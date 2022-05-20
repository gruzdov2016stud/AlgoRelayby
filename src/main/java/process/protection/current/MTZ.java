//package process.protection.current;
//
//import nodes.controls.CSWI;
//import nodes.gui.NHMI;
//import nodes.gui.other.NHMISignal;
//import nodes.measurements.MMXU;
//import nodes.protection.PTOC;
//import nodes.registration.SVCF;
//import nodes.switchgear.XCBR;
//import objects.data.enums.StVal;
//
//public class MTZ {
//    public static void main(String[] args) {
//
//        SVCF lsvc = new SVCF();
//        String path = "D:\\YandexDisk\\Project\\AlgoRelayProtection\\AlgoRelayby\\BeginLines\\PhB20";
//        lsvc.readCSV(path);
//
//        NHMI nhmi = new NHMI();
//        /**Измерения*/
//        MMXU mmxu = new MMXU();
//        /**Первая ступень защиты*/
//        PTOC ptoc1 = new PTOC();
//        /**Вторая ступень защиты*/
//        PTOC ptoc2 = new PTOC();
//        /**Третья ступень защиты*/
//        PTOC ptoc3 = new PTOC();
//        /**логический узел используется для управления всеми состояниями переключений */
//        CSWI cswi = new CSWI();
//        /**Имя: XCBR Данный логический узел (LN) используется для моделирования коммутационных устройств*/
//        XCBR xcbr = new XCBR();
//
//
//
//
//
//        mmxu.setInstIa(lsvc.getSignals().get(0));
//        mmxu.setInstIb(lsvc.getSignals().get(1));
//        mmxu.setInstIc(lsvc.getSignals().get(2));
//        ptoc1.setA(mmxu.getA());
//        ptoc2.setA(mmxu.getA());
//        ptoc3.setA(mmxu.getA());
//
//        ptoc1.getStrVal().getSetMag().getF().setValue(500.0f);
//        ptoc2.getStrVal().getSetMag().getF().setValue(500.0f);
//        ptoc3.getStrVal().getSetMag().getF().setValue(500.0f);
//
//        cswi.setOpOpn(ptoc1.getOp());
//        cswi.getPos().getCtIVal().setValue(true);
//        cswi.getPos().getStVal().setValue(StVal.ON);
//
//        xcbr.setPos(cswi.getPos());
//
//        nhmi.addSignals(
//                new NHMISignal("PhsA", lsvc.getSignals().get(0).getInstMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("PhsB", lsvc.getSignals().get(1).getInstMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("PhsC", lsvc.getSignals().get(2).getInstMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("rms Ia", mmxu.getA().getPhsA().getCVal().getMag().getF()),
//                new NHMISignal("StrVal", ptoc1.getStrVal().getSetMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("rms Ib", mmxu.getA().getPhsB().getCVal().getMag().getF()),
//                new NHMISignal("StrVal", ptoc1.getStrVal().getSetMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("rms Ic", mmxu.getA().getPhsC().getCVal().getMag().getF()),
//                new NHMISignal("StrVal", ptoc1.getStrVal().getSetMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Срабатывание", ptoc1.getOp().getGeneral())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Пуск", ptoc1.getStr().getGeneral())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Срабатывание", ptoc2.getOp().getGeneral())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Пуск", ptoc2.getStr().getGeneral())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Срабатывание", ptoc3.getOp().getGeneral())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Пуск", ptoc3.getStr().getGeneral())
//        );
//        nhmi.addSignals(
//                new NHMISignal("breaker", xcbr.getPos().getCtIVal())
//        );
//
//
//        while (lsvc.hasNext()){
//            lsvc.process();
//            nhmi.process();
//            mmxu.process();
//            ptoc1.process();
//            ptoc2.process();
//            ptoc3.process();
//            cswi.process();
//            xcbr.process();
//
//            System.out.println(lsvc.getSignals().get(0).getInstMag().getF().getValue());
//            System.out.println(lsvc.getSignals().get(1).getInstMag().getF().getValue());
//            System.out.println(lsvc.getSignals().get(2).getInstMag().getF().getValue());
//        }
//
//
//    }
//}