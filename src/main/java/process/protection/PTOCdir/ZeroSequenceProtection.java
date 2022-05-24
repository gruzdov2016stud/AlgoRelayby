package process.protection.PTOCdir;

import nodes.common.LN;
import nodes.controls.CSWI;
import nodes.gui.NHMI;
import nodes.gui.other.NHMISignal;
import nodes.measurements.MMXU;
import nodes.measurements.MSQI;
import nodes.protection.PTOC;
import nodes.measurements.utils.LSVC;
import nodes.measurements.utils.ParseQuality;
import nodes.registration.RDIR;
import nodes.switchgear.XCBR;
import objects.data.enums.StVal;

import java.util.ArrayList;


public class ZeroSequenceProtection {
    private static ArrayList<LN> logicalNodes = new ArrayList<>();
    public static void main(String[] args) {
//----------------------------------------------------todo Узел LSVC lsvc---------------------------------------------//
        LSVC lsvc = new LSVC();
        logicalNodes.add(lsvc);
        /**
         *  KZ4 KZ3 - 1 ступень KZ2
         *
         *  KZ7 KZ6 KZ5 KZ1
         */
        String path = "src/main/resources/Опыты/KZ4";
//        String path = "src/main/resources/Опыты (с качеством)/KZ5";
        lsvc.readComtrade(path);
//----------------------------------------------------todo Узел ParseQuality pq---------------------------------------//
        ParseQuality pq = new ParseQuality();//создание объекта класса ParseQuality
        if (lsvc.getSignalNumber()>12) {
            logicalNodes.add(pq);//добавление его в логический узел если присутсвует качество
        }
        pq.setSQualityIa(lsvc.getSignals().get(9));// присвоение значений качества тока фазы А в соответствии с comtrade файлом
        pq.setSQualityIb(lsvc.getSignals().get(10));// присвоение значений качества тока фазы В в соответствии с comtrade файлом
        pq.setSQualityIc(lsvc.getSignals().get(11));// присвоение значений качества тока фазы С в соответствии с comtrade файлом
        pq.setSQualityUa(lsvc.getSignals().get(6));// присвоение значений качества напряжения фазы А в соответствии с comtrade файлом
        pq.setSQualityUb(lsvc.getSignals().get(7));// присвоение значений качества напряжения фазы В в соответствии с comtrade файлом
        pq.setSQualityUc(lsvc.getSignals().get(8));// присвоение значений качества напряжения фазы С в соответствии с comtrade файлом

//----------------------------------------------------todo Узел NHMI nhmi---------------------------------------------//
        NHMI nhmi = new NHMI();
        logicalNodes.add(nhmi);
        NHMI nhmi1 = new NHMI();
        logicalNodes.add(nhmi1);

//----------------------------------------------------todo Узел MMXU mmxu---------------------------------------------//
        /**Измерения*/
        MMXU mmxu = new MMXU();
        logicalNodes.add(mmxu);
        /**Извлекаем и запоминаем Токи мгновенных значений для дальнейшей обработки фильтром Фурье*/
        mmxu.setInstMagUa(lsvc.getSignals().get(2));
        mmxu.setInstMagUb(lsvc.getSignals().get(1));
        mmxu.setInstMagUc(lsvc.getSignals().get(0));
        mmxu.setInstMagIa(lsvc.getSignals().get(3));
        mmxu.setInstMagIb(lsvc.getSignals().get(4));
        mmxu.setInstMagIc(lsvc.getSignals().get(5));
        System.out.println();

//----------------------------------------------------todo Узел MSQI msqi---------------------------------------------//
        MSQI msqi = new MSQI();
        logicalNodes.add(msqi);
        /**Передача векторов тока*/
        msqi.setA(mmxu.getA());
        /**Передача векторов напряжения*/
        msqi.setPhV(mmxu.getPhV());
//----------------------------------------------------todo Узел RDIR rdir---------------------------------------------//
        RDIR rdir = new RDIR();
        logicalNodes.add(rdir);
        /**Передача векторов мощности*/
        rdir.setSeqA(msqi.getSeqA());
        rdir.setSeqV(msqi.getSeqV());
        rdir.setW(mmxu.getW()); //передаем активную мощность


//----------------------------------------------------todo Узел PTOC ptoc1--------------------------------------------//
        /**Первая ступень направленная*/
        PTOC ptoc1 =new PTOC();
        logicalNodes.add(ptoc1);
        ptoc1.getA().setPhsA(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы A
        ptoc1.getA().setPhsB(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы B
        ptoc1.getA().setPhsC(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы C
        ptoc1.getStrVal().getSetMag().getF().setValue(118f);;// выбор уставки 1 кА 453f За спиной 250 кз6 = 180f
        ptoc1.getOpDLTmms().getSetVal().setValue(100); // выбор выдержки по времени
        ptoc1.setDir(rdir.getDir()); // передача направления мощности
        ptoc1.getDirMod().getSetVal().setValue(1); // выбор направленной защиты
        ptoc1.getAutomaticAccelearation().getCtIVal().setValue(false); //режим автоматического ускорения(вводится для всех направленных ступеней)

//----------------------------------------------------todo Узел PTOC ptoc2--------------------------------------------//
        /**Вторая ступень направленная*/
        PTOC ptoc2 =new PTOC();
        logicalNodes.add(ptoc2);
        ptoc2.getA().setPhsA(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы A
        ptoc2.getA().setPhsB(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы B
        ptoc2.getA().setPhsC(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы C
        ptoc2.getStrVal().getSetMag().getF().setValue(116f);;// выбор уставки 1 кА
        ptoc2.getOpDLTmms().getSetVal().setValue(300); // выбор выдержки по времени
        ptoc2.setDir(rdir.getDir()); // передача направления мощности
        ptoc2.getDirMod().getSetVal().setValue(1); // выбор направленной защиты
        ptoc2.getAutomaticAccelearation().getCtIVal().setValue(false); //режим автоматического ускорения(вводится для всех направленных ступеней)

//----------------------------------------------------todo Узел PTOC ptoc3--------------------------------------------//
        /**Третья ступень направленная*/
        PTOC ptoc3 =new PTOC();
        logicalNodes.add(ptoc3);
        ptoc3.getA().setPhsA(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы A
        ptoc3.getA().setPhsB(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы B
        ptoc3.getA().setPhsC(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы C
        ptoc3.getStrVal().getSetMag().getF().setValue(118f);;// выбор уставки 1 кА
        ptoc3.getOpDLTmms().getSetVal().setValue(400); // выбор выдержки по времени
        ptoc3.getDirMod().getSetVal().setValue(1); // выбор направленной защиты
        ptoc3.setDir(rdir.getDir()); // передача направления мощности
        ptoc3.getAutomaticAccelearation().getCtIVal().setValue(false); //режим автоматического ускорения(вводится для всех направленных ступеней)

//----------------------------------------------------todo Узел PTOC ptoc1NE------------------------------------------//
        /**Первая ступень ненаправленная*/
        PTOC ptoc1NE =new PTOC();
        logicalNodes.add(ptoc1NE);
        ptoc1NE.getA().setPhsA(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы A
        ptoc1NE.getA().setPhsB(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы B
        ptoc1NE.getA().setPhsC(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы C
        ptoc1NE.getStrVal().getSetMag().getF().setValue(100f);;// выбор уставки 1 кА
        ptoc1NE.getOpDLTmms().getSetVal().setValue(100); // выбор выдержки по времени
        ptoc1NE.setDir(rdir.getDir()); // передача направления мощности
        ptoc1NE.getDirMod().getSetVal().setValue(0); // выбор ненаправленная защиты

//------------------------------------------------todo Узел PTOC ptoc2NE----------------------------------------------//
        /**Вторая ступень ненаправленная*/
        PTOC ptoc2NE =new PTOC();
        logicalNodes.add(ptoc2NE);
        ptoc2NE.getA().setPhsA(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы A
        ptoc2NE.getA().setPhsB(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы B
        ptoc2NE.getA().setPhsC(msqi.getSeqA().getС3());// присвоение тока нулевой последовательности фазы C
        ptoc2NE.getStrVal().getSetMag().getF().setValue(100f);;// выбор уставки 1 кА
        ptoc2NE.getOpDLTmms().getSetVal().setValue(500); // выбор выдержки по времени
        ptoc2NE.setDir(rdir.getDir()); // передача направления мощности
        ptoc2NE.getDirMod().getSetVal().setValue(0); // выбор направленной защиты

//----------------------------------------------------todo Узел CSWI cswi---------------------------------------------//
        CSWI cswi = new CSWI();
        logicalNodes.add(cswi);
        cswi.setOpOpn1(ptoc1.getOp());
        cswi.setOpOpn2(ptoc2.getOp());
        cswi.setOpOpn3(ptoc3.getOp());
        cswi.setOpOpn4(ptoc1NE.getOp());
        cswi.setOpOpn5(ptoc2NE.getOp());
        cswi.getPos().getCtIVal().setValue(true); // присвоение начальной команды на отключение
        cswi.getPos().getStVal().setValue(StVal.ON);// присвоение начального положения выключателя

//----------------------------------------------------todo Узел XCBR xcbr---------------------------------------------//
        XCBR xcbr = new XCBR();
        logicalNodes.add(xcbr);
        xcbr.setPos(cswi.getPos());
//----------------------------------------------------todo Отображение--------------------------------------------//
        nhmi.addSignals(
                new NHMISignal("PhsIa", lsvc.getSignals().get(3).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("PhsIb", lsvc.getSignals().get(4).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("PhsIc", lsvc.getSignals().get(5).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("RMS Ia", mmxu.getA().getPhsA().getCVal().getMag())
        );
        nhmi.addSignals(
                new NHMISignal("RMS Ib", mmxu.getA().getPhsB().getCVal().getMag())
        );
        nhmi.addSignals(
                new NHMISignal("RMS Ic", mmxu.getA().getPhsC().getCVal().getMag())
        );

        //симметрич составляющие
        nhmi1.addSignals(new NHMISignal("I0", msqi.getSeqA().getС3().getCVal().getMag()),
                new NHMISignal("Уставка ptoc1", ptoc1.getStrVal().getSetMag().getF()));
        nhmi1.addSignals(new NHMISignal("U0", msqi.getSeqV().getС3().getCVal().getMag()));
//         Направление мощности
        nhmi1.addSignals(new NHMISignal("Направ",rdir.getDir().getGeneral()));

        //пуск защиты пофазно
        nhmi1.addSignals(new NHMISignal("Пуск 1ст", ptoc1.getStr().getPhsA()));
        nhmi1.addSignals(new NHMISignal("Пуск 2ст", ptoc2.getStr().getPhsB()));
        nhmi1.addSignals(new NHMISignal("Пуск 3ст", ptoc3.getStr().getPhsC()));
        nhmi1.addSignals(new NHMISignal("Пуск 1ст не", ptoc1NE.getStr().getPhsC()));
        nhmi1.addSignals(new NHMISignal("Пуск 2ст не", ptoc2NE.getStr().getPhsC()));

        //срабатывание защиты пофазно
        nhmi1.addSignals(new NHMISignal("Сраб. 1ст", ptoc1.getOp().getGeneral()));
        nhmi1.addSignals(new NHMISignal("Сраб. 2ст", ptoc2.getOp().getGeneral()));
        nhmi1.addSignals(new NHMISignal("Сраб. 3ст", ptoc3.getOp().getGeneral()));
        nhmi1.addSignals(new NHMISignal("Сраб. 1ст не", ptoc1NE.getOp().getGeneral()));
        nhmi1.addSignals(new NHMISignal("Сраб. 2ст не", ptoc2NE.getOp().getGeneral()));

        //выключатель
        nhmi1.addSignals(new NHMISignal("Выключатель", xcbr.getPos().getCtIVal())); // сигнал состояния выключателя


        while (lsvc.hasNext()){
            logicalNodes.forEach(LN::process);
//            System.out.println("Сигнал тока фазы А: "+lsvc.getSignals().get(3).getInstMag().getF().getValue());
//            System.out.println("Сигнал тока фазы В: "+lsvc.getSignals().get(4).getInstMag().getF().getValue());
//            System.out.println("Сигнал тока фазы С: "+lsvc.getSignals().get(5).getInstMag().getF().getValue());
//            if(lsvc.getSignalNumber()>12){
//                System.out.println("Качество полученного сигнала Ia - "+pq.getQualityIa().getValidity().getValue());
//                System.out.println("Качество полученного сигнала Ib - "+pq.getQualityIb().getValidity().getValue());
//                System.out.println("Качество полученного сигнала Ic - "+pq.getQualityIc().getValidity().getValue());
//                System.out.println("Качество полученного сигнала Ua - "+pq.getQualityUa().getValidity().getValue());
//                System.out.println("Качество полученного сигнала Ub - "+pq.getQualityUb().getValidity().getValue());
//                System.out.println("Качество полученного сигнала Uc - "+pq.getQualityUc().getValidity().getValue());
//            }

        }


    }
}
