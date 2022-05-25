package process.protection.PDIS;

import nodes.common.LN;
import nodes.controls.CSWI;
import nodes.gui.NHMI;
import nodes.gui.NHMIP;
import nodes.gui.other.NHMIPoint;
import nodes.gui.other.NHMISignal;
import nodes.measurements.MMXU;
import nodes.measurements.MSQI;
import nodes.measurements.utils.LSVC;
import nodes.protection.PDIS;
import nodes.protection.PTOC;
import nodes.protection.PTRC;
import nodes.registration.RDIR_PDIS;
import nodes.registration.RPSB;
import nodes.switchgear.XCBR;
import objects.data.DataAttribute;
import objects.data.enums.StVal;

import java.util.ArrayList;
import java.util.List;


public class main_PDIS {
    private static ArrayList<LN> logicalNodes = new ArrayList<>();
    public static void main(String[] args) {
        float setpoint1 = 200f; // Радиус характеристики ДЗ первой ступени
        float setpoint2 = 250f; // Радиус характеристики ДЗ второй ступени
        float setpoint3 = 300f; // Радиус характеристики ДЗ третей ступени
        float setpoint4 = 350f; // Радиус характеристики ДЗ четвёртой ступени
        float setpoint5 = 400f; // Радиус характеристики ДЗ пятой ступени

        int timeSet1 = 100; // Задержка на срабатывание 1ой ступени
        int timeSet2 = 400; // Задержка на срабатывание 2ой ступени
        int timeSet3 = 600; // Задержка на срабатывание 3ей ступени
        int timeSet4 = 400; // Задержка на срабатывание 4ой ступени
        int timeSet5 = 600; // Задержка на срабатывание 5ой ступени

        // Зона чувствительности направленной ДЗ KZ4 еще и ненаправленные
        /*Зона чувствительности ненаправленной ДЗ KZ7 отрабатывает верно*/
        float LeftAng = 85f; //  1.83f
        float RightAng = -16f; // -1.2f
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
//----------------------------------------------------todo Узел NHMI nhmi---------------------------------------------//
        NHMI nhmi1 = new NHMI();
        NHMI nhmi2 = new NHMI();
        NHMI nhmi3 = new NHMI();
        logicalNodes.add(nhmi1);
//        logicalNodes.add(nhmi2);
//        logicalNodes.add(nhmi3);
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
//----------------------------------------------------todo Узел MSQI msqi---------------------------------------------//
        MSQI msqi = new MSQI();
        logicalNodes.add(msqi);
        /**Передача векторов тока*/
        msqi.setA(mmxu.getA());
        /**Передача векторов напряжения*/
        msqi.setPhV(mmxu.getPhV());
//----------------------------------------------------todo Узел RDIR_PDIS rdir---------------------------------------------//
        RDIR_PDIS rdir = new RDIR_PDIS();
        logicalNodes.add(rdir);
        /**Передача сопротивления*/
        rdir.setZ(mmxu.getZ());
        rdir.setArea(LeftAng, RightAng);
//----------------------------------------------------todo Узел RPSB rpsb---------------------------------------------//
        RPSB rpsb = new RPSB();
        logicalNodes.add(rpsb);
        rpsb.setSeqA(msqi.getSeqA());
//----------------------------------------------------todo Узел PDIS pdis1--------------------------------------------//
        /**Первая ступень направленная*/
        PDIS pdis1 =new PDIS();
        logicalNodes.add(pdis1);
        pdis1.setZ(mmxu.getZ());// присвоение сопротивления
        pdis1.getPoRch().getSetMag().getF().setValue(setpoint1);
        pdis1.setDir(rdir.getDir()); // передача направления мощности
        pdis1.getOpDLTmms().getSetVal().setValue(timeSet1); // выбор выдержки по времени
        pdis1.getDirMod().getSetVal().setValue(1); // выбор направленной защиты

//----------------------------------------------------todo Узел PDIS pdis2--------------------------------------------//
        /**Вторая ступень направленная*/
        PDIS pdis2 =new PDIS();
        logicalNodes.add(pdis2);
        pdis2.setZ(mmxu.getZ());// присвоение сопротивления
        pdis2.getPoRch().getSetMag().getF().setValue(setpoint2);
        pdis2.setDir(rdir.getDir()); // передача направления мощности
        pdis2.getOpDLTmms().getSetVal().setValue(timeSet2); // выбор выдержки по времени
        pdis2.getDirMod().getSetVal().setValue(1); // выбор направленной защиты
//----------------------------------------------------todo Узел PDIS pdis3--------------------------------------------//
        /**Третья ступень направленная*/
        PDIS pdis3 =new PDIS();
        logicalNodes.add(pdis3);
        pdis3.setZ(mmxu.getZ());// присвоение сопротивления
        pdis3.getPoRch().getSetMag().getF().setValue(setpoint3);
        pdis3.setDir(rdir.getDir()); // передача направления мощности
        pdis3.getOpDLTmms().getSetVal().setValue(timeSet3); // выбор выдержки по времени
        pdis3.getDirMod().getSetVal().setValue(1); // выбор направленной защиты
//----------------------------------------------------todo Узел PDIS pdis4ne------------------------------------------//
        /**Первая ступень ненаправленная*/
        PDIS pdis4ne =new PDIS();
        logicalNodes.add(pdis4ne);
        pdis4ne.setZ(mmxu.getZ());// присвоение сопротивления
        pdis4ne.getPoRch().getSetMag().getF().setValue(setpoint4);
        pdis4ne.setDir(rdir.getDir()); // передача направления мощности
        pdis4ne.getOpDLTmms().getSetVal().setValue(timeSet4); // выбор выдержки по времени
        pdis4ne.getDirMod().getSetVal().setValue(0); // выбор направленной защиты
//------------------------------------------------todo Узел PDIS pdis5ne----------------------------------------------//
        /**Вторая ступень ненаправленная*/
        PDIS pdis5ne =new PDIS();
        logicalNodes.add(pdis5ne);
        pdis5ne.setZ(mmxu.getZ());// присвоение сопротивления
        pdis5ne.getPoRch().getSetMag().getF().setValue(setpoint5);
        pdis5ne.setDir(rdir.getDir()); // передача направления мощности
        pdis5ne.getOpDLTmms().getSetVal().setValue(timeSet5); // выбор выдержки по времени
        pdis5ne.getDirMod().getSetVal().setValue(0); // выбор направленной защиты
// ----------------------------------------------------todo Узел PTRC ptrc---------------------------------------------//
        PTRC ptrc = new PTRC();
        ptrc.getOp().add(pdis1.getOp());
        ptrc.getOp().add(pdis2.getOp());
        ptrc.getOp().add(pdis3.getOp());
        ptrc.getOp().add(pdis4ne.getOp());
        ptrc.getOp().add(pdis5ne.getOp());
        ptrc.getOp().add(rpsb.getOp());
        logicalNodes.add(ptrc);

//----------------------------------------------------todo Узел CSWI cswi---------------------------------------------//
        CSWI cswi = new CSWI();
        logicalNodes.add(cswi);
        cswi.setOpOpn(ptrc.getStr());
        cswi.getPos().getCtIVal().setValue(true); // присвоение начальной команды на отключение
        cswi.getPos().getStVal().setValue(StVal.ON);// присвоение начального положения выключателя

//----------------------------------------------------todo Узел XCBR xcbr---------------------------------------------//
        XCBR xcbr = new XCBR();
        logicalNodes.add(xcbr);
        xcbr.setPos(cswi.getPos());
//----------------------------------------------------todo Отображение--------------------------------------------//
        nhmi1.addSignals("U", new NHMISignal("Ua", lsvc.getSignals().get(2).getInstMag().getF()),
                new NHMISignal("Ub", lsvc.getSignals().get(1).getInstMag().getF()),
                new NHMISignal("Uc", lsvc.getSignals().get(0).getInstMag().getF()));
        nhmi1.addSignals("I", new NHMISignal("Ia", lsvc.getSignals().get(3).getInstMag().getF()),
                new NHMISignal("Ib", lsvc.getSignals().get(4).getInstMag().getF()),
                new NHMISignal("Ic", lsvc.getSignals().get(11).getInstMag().getF()));
        nhmi1.addSignals(new NHMISignal("I2", msqi.getSeqA().getС2().getCVal().getMag()));
        nhmi1.addSignals("Zmag",
                new NHMISignal("ZmagAB", mmxu.getZ().getPhsA().getCVal().getMag()),
                new NHMISignal("ZmagBC", mmxu.getZ().getPhsB().getCVal().getMag()),
                new NHMISignal("ZmagCA", mmxu.getZ().getPhsC().getCVal().getMag()),
                new NHMISignal("setpoint1", new DataAttribute<>(setpoint1)),
                new NHMISignal("setpoint2", new DataAttribute<>(setpoint2)),
                new NHMISignal("setpoint3", new DataAttribute<>(setpoint3)),
                new NHMISignal("setpoint4", new DataAttribute<>(setpoint4)),
                new NHMISignal("setpoint5", new DataAttribute<>(setpoint5)));
        nhmi1.addSignals("Zang",
                new NHMISignal("ZangAB", mmxu.getZ().getPhsA().getCVal().getMag()),
                new NHMISignal("ZangBC", mmxu.getZ().getPhsB().getCVal().getMag()),
                new NHMISignal("ZangCA", mmxu.getZ().getPhsC().getCVal().getMag()),
                new NHMISignal("LeftAng", new DataAttribute<>(LeftAng)),
                new NHMISignal("RightAng", new DataAttribute<>(RightAng)));
        nhmi1.addSignals("<Блок>", new NHMISignal("", rpsb.getOp().getGeneral()));
        nhmi1.addSignals("Op1", new NHMISignal("Op1", pdis1.getOp().getGeneral()));
        nhmi1.addSignals("Op2", new NHMISignal("Op2", pdis2.getOp().getGeneral()));
        nhmi1.addSignals("Op3", new NHMISignal("Op3", pdis3.getOp().getGeneral()));
        nhmi1.addSignals("Op4", new NHMISignal("Op4", pdis4ne.getOp().getGeneral()));
        nhmi1.addSignals("Op5", new NHMISignal("Op5", pdis5ne.getOp().getGeneral()));
        nhmi1.addSignals(new NHMISignal("SwitchMode", xcbr.getPos().getCtIVal())); // сигнал состояния выключателя
//        nhmi2.addSignals("Str1", new NHMISignal("Str1", pdis1.getStr().getGeneral()));
//        nhmi2.addSignals("Str2", new NHMISignal("Str2", pdis2.getStr().getGeneral()));
//        nhmi2.addSignals("Str3", new NHMISignal("Str3", pdis3.getStr().getGeneral()));
//        nhmi2.addSignals("Str4", new NHMISignal("Str4", pdis4ne.getStr().getGeneral()));
//        nhmi2.addSignals("Str5", new NHMISignal("Str5", pdis5ne.getStr().getGeneral()));
//        //симметрич составляющие
//        nhmi3.addSignals(new NHMISignal("I1", msqi.getSeqA().getС1().getCVal().getMag()));
//        nhmi3.addSignals(new NHMISignal("I2", msqi.getSeqA().getС2().getCVal().getMag()));
//        nhmi3.addSignals(new NHMISignal("I0", msqi.getSeqA().getС3().getCVal().getMag()));
//        // Направление мощности
//        nhmi3.addSignals(new NHMISignal("Напр PА", rdir.getDir().getPhsA()));
//        nhmi3.addSignals(new NHMISignal("Напр PB", rdir.getDir().getPhsB()));
//        nhmi3.addSignals(new NHMISignal("Напр PC", rdir.getDir().getPhsC()));


        while (lsvc.hasNext()){
            logicalNodes.forEach(LN::process);
//            System.out.println("Сигнал тока фазы А: "+lsvc.getSignals().get(3).getInstMag().getF().getValue());
//            System.out.println("Сигнал тока фазы В: "+lsvc.getSignals().get(4).getInstMag().getF().getValue());
//            System.out.println("Сигнал тока фазы С: "+lsvc.getSignals().get(5).getInstMag().getF().getValue());
        }

        NHMIP nhmip = new NHMIP();

        List<NHMIPoint<Double,Double>> circle1 = new ArrayList<>();
        List<NHMIPoint<Double,Double>> circle2 = new ArrayList<>();
        List<NHMIPoint<Double,Double>> circle3 = new ArrayList<>();
        List<NHMIPoint<Double,Double>> circle4 = new ArrayList<>();
        List<NHMIPoint<Double,Double>> circle5 = new ArrayList<>();
        List<NHMIPoint<Double,Double>> LeftAngDir = new ArrayList<>();
        List<NHMIPoint<Double,Double>> RightAngDir = new ArrayList<>();
        double sin;
        double cos;
        double sin1 = Math.sin(LeftAng);
        double cos1 = Math.cos(LeftAng);
        double sin2 = Math.sin(RightAng);
        double cos2 = Math.cos(RightAng);
        double value1Y;
        double value1X;
        double value2Y;
        double value2X;
        int iters = 1000;
        for (int i = 0; i < iters; i++) {
            sin = Math.sin(2*Math.PI*((float) i) / ((float) iters));
            cos = Math.cos(2*Math.PI*((float) i) / ((float) iters));
            circle1.add(new NHMIPoint(setpoint1*sin, setpoint1*cos));
            circle2.add(new NHMIPoint(setpoint2*sin, setpoint2*cos));
            circle3.add(new NHMIPoint(setpoint3*sin, setpoint3*cos));
            circle4.add(new NHMIPoint(setpoint4*sin, setpoint4*cos));
            circle5.add(new NHMIPoint(setpoint5*sin, setpoint5*cos));
            value1Y = setpoint5 * i / iters * sin1;
            value1X = setpoint5 * i / iters * cos1;
            value2Y = setpoint5 * i / iters * sin2;
            value2X = setpoint5 * i / iters * cos2;
            LeftAngDir.add(new NHMIPoint(value1X, value1Y));
            RightAngDir.add(new NHMIPoint(value2X, value2Y));
        }

        nhmip.drawCharacteristic("Stage1", circle1);
        nhmip.addSignals(
                new NHMISignal("ZmagAB", mmxu.getZ().getPhsA().getCVal().getOrtX(),mmxu.getZ().getPhsA().getCVal().getOrtY()),
                new NHMISignal("ZmagBC", mmxu.getZ().getPhsB().getCVal().getOrtX(),mmxu.getZ().getPhsB().getCVal().getOrtY()),
                new NHMISignal("ZmagCA", mmxu.getZ().getPhsC().getCVal().getOrtX(),mmxu.getZ().getPhsC().getCVal().getOrtY()));
        nhmip.drawCharacteristic("Stage2", circle2);
        nhmip.drawCharacteristic("Stage3", circle3);
        nhmip.drawCharacteristic("Stage4", circle4);
        nhmip.drawCharacteristic("Stage5", circle5);
        nhmip.drawCharacteristic("Left angle", LeftAngDir);
        nhmip.drawCharacteristic("Right angle", RightAngDir);


    }
}
