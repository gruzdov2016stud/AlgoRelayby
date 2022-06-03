package process.protection.DZL;

import nodes.common.LN;
import nodes.controls.CSWI;
import nodes.gui.NHMI;
import nodes.gui.NHMIP;
import nodes.gui.other.NHMIPoint;
import nodes.gui.other.NHMISignal;
import nodes.measurements.MHAI;
import nodes.measurements.MMXU;
import nodes.measurements.RMXU;
import nodes.measurements.utils.LSVC;
import nodes.registration.protection.PDIF;
import nodes.registration.protection.PHAR;
import nodes.registration.protection.PIOC;
import nodes.registration.protection.PTRC;
import nodes.registration.protection.signals_mode.SCTR;
import nodes.switchgear.XCBR;

import java.util.ArrayList;
import java.util.List;


public class main {
    private static List<LN> logicalNodes = new ArrayList<>();
    public static void main(String[] args) {
//----------------------------------------------------todo Узел LSVC lsvc---------------------------------------------//
        LSVC lsvc = new LSVC();
        /*Внутренне 1-3 Внешнее 4-7*/
        String path = "src/main/resources/KP/KZ7";
        lsvc.readComtrade(path);
        logicalNodes.add(lsvc);
//----------------------------------------------------todo Узел MMXU mmxu---------------------------------------------//
        /**Измерения*/
        MMXU mmxu1 = new MMXU();
        logicalNodes.add(mmxu1);
        mmxu1.setInstMagIa(lsvc.getSignals().get(0));
        mmxu1.setInstMagIb(lsvc.getSignals().get(1));
        mmxu1.setInstMagIc(lsvc.getSignals().get(2));
        MMXU mmxu2 = new MMXU();
        logicalNodes.add(mmxu2);
        mmxu2.setInstMagIa(lsvc.getSignals().get(3));
        mmxu2.setInstMagIb(lsvc.getSignals().get(4));
        mmxu2.setInstMagIc(lsvc.getSignals().get(5));
//----------------------------------------------------todo Узел MHAI mhai---------------------------------------------//
        MHAI mhai1 = new MHAI(1,2);
        mhai1.setInstIa(lsvc.getSignals().get(0));
        mhai1.setInstIb(lsvc.getSignals().get(1));
        mhai1.setInstIc(lsvc.getSignals().get(2));
        logicalNodes.add(mhai1);
        MHAI mhai2 = new MHAI(1,2);
        mhai1.setInstIa(lsvc.getSignals().get(3));
        mhai1.setInstIb(lsvc.getSignals().get(4));
        mhai1.setInstIc(lsvc.getSignals().get(5));
        logicalNodes.add(mhai2);
//----------------------------------------------------todo Узел RMXU rmxu---------------------------------------------//
        RMXU rmxu = new RMXU(0.75);
        rmxu.getInputsA().add(mmxu1.getA());
        rmxu.getInputsA().add(mmxu2.getA());
        logicalNodes.add(rmxu);
//----------------------------------------------------todo Узел RMXU rmxu---------------------------------------------//
        PHAR phar = new PHAR(0.1);
        phar.getHInputs().add(mhai1.getHA());
        phar.getHInputs().add(mhai2.getHA());
        logicalNodes.add(phar);
//----------------------------------------------------todo Узел RMXU rmxu---------------------------------------------//
        SCTR sctr = new SCTR(0.4, 100);
        sctr.setDifACIc(rmxu.getDifACIc());
        logicalNodes.add(phar);
//----------------------------------------------------todo Узел PTOC dto--------------------------------------------//
        /**Дифференциальная токовая отсечка*/
        PIOC dto = new PIOC(2.95);
        dto.setDifACIc(rmxu.getDifACIc());
        logicalNodes.add(dto);
//----------------------------------------------------todo Узел PDIF pdif--------------------------------------------//
        /**Дифференциальной защиты трансформатора*/
        PDIF pdif = new PDIF(100, 0.5,
                          0, 0.344,
                                    0, 0.344,
                                 0.688,0.344,
                                 4.53, 2.95);
        pdif.setDifACIc(rmxu.getDifACIc());
        pdif.setRstCurrent(rmxu.getRstCurrent());
        pdif.setBlkOpPHAR(phar.getBlkOp());
        pdif.setBlkOpSCTR(sctr.getBlkOp());
        pdif.init();
        logicalNodes.add(pdif);
// ----------------------------------------------------todo Узел PTRC ptrc--------------------------------------------//
        PTRC ptrc = new PTRC(dto.getOp(),pdif.getOp());
        logicalNodes.add(ptrc);
//----------------------------------------------------todo Узел CSWI cswi---------------------------------------------//
        CSWI cswi1 = new CSWI(ptrc.getTr(),true, 2);
        logicalNodes.add(cswi1);

        CSWI cswi2 = new CSWI(ptrc.getTr(),true, 2);
        logicalNodes.add(cswi2);
//----------------------------------------------------todo Узел XCBR xcbr---------------------------------------------//
        XCBR xcbr1 = new XCBR(cswi1.getPos());
        logicalNodes.add(xcbr1);

        XCBR xcbr2 = new XCBR(cswi2.getPos());
        logicalNodes.add(xcbr2);
//----------------------------------------------------todo Узел NHMI nhmi---------------------------------------------//
        NHMI nhmi1 = new NHMI();
        logicalNodes.add(nhmi1);
        nhmi1.addSignals("LA",
                new NHMISignal("IAL1", lsvc.getSignals().get(0).getInstMag().getF()),
                new NHMISignal("IBL1", lsvc.getSignals().get(1).getInstMag().getF()),
                new NHMISignal("ICL1", lsvc.getSignals().get(2).getInstMag().getF()));
        nhmi1.addSignals("LB",
                new NHMISignal("IAL2", lsvc.getSignals().get(3).getInstMag().getF()),
                new NHMISignal("IBL2", lsvc.getSignals().get(4).getInstMag().getF()),
                new NHMISignal("ICL2", lsvc.getSignals().get(5).getInstMag().getF()));
        nhmi1.addSignals(new NHMISignal("Действующее phA L1", mmxu1.getA().getPhsA().getCVal().getMag()));
        nhmi1.addSignals(new NHMISignal("Действующее phA L2", mmxu2.getA().getPhsA().getCVal().getMag()));

        NHMI nhmi2 = new NHMI();
        logicalNodes.add(nhmi2);
        nhmi2.addSignals(new NHMISignal("IbVn", lsvc.getSignals().get(0).getInstMag().getF()));
        nhmi2.addSignals(new NHMISignal("IbNn", lsvc.getSignals().get(3).getInstMag().getF()));
        nhmi2.addSignals(
                new NHMISignal("SrtValDTO", rmxu.getDifACIc().getPhsA().getCVal().getMag()),
                new NHMISignal("StrValDZL", pdif.getStrRst().getPhsA().getCVal().getMag()),
                new NHMISignal("", rmxu.getDifACIc().getPhsB().getCVal().getMag()),
                new NHMISignal("", pdif.getStrRst().getPhsB().getCVal().getMag()),
                new NHMISignal("", rmxu.getDifACIc().getPhsC().getCVal().getMag()),
                new NHMISignal("", pdif.getStrRst().getPhsC().getCVal().getMag()));
        nhmi2.addSignals(new NHMISignal("<phar>", phar.getBlkOp().getStValPhA()));
        nhmi2.addSignals(new NHMISignal("<sctr>", sctr.getBlkOp().getStValPhA()));
        nhmi2.addSignals(new NHMISignal("ТО Op", dto.getOp().getGeneral()));
        nhmi2.addSignals(new NHMISignal("ДЗТ Str", pdif.getStr().getGeneral()));
        nhmi2.addSignals(new NHMISignal("ДЗТ Op", pdif.getOp().getGeneral()));

        nhmi2.addSignals(new NHMISignal("SwitchMode ВН", xcbr1.getPos().getCtIVal()));
        nhmi2.addSignals(new NHMISignal("SwitchMode НН", xcbr2.getPos().getCtIVal()));

        NHMIP nhmip = new NHMIP();
        logicalNodes.add(nhmip);
        nhmip.drawCharacteristic("ДТО",TripZone(pdif, false));
        nhmip.drawCharacteristic("Тормозная Характеристика",TripZone(pdif,true));
        while (lsvc.hasNext()){
            logicalNodes.forEach(LN::process);
        }

    }
    private static List<NHMIPoint<Double,Double>> TripZone(PDIF pdif, boolean trip){
        List<NHMIPoint<Double, Double>> points = new ArrayList<>();
        double rst0 = pdif.getRst0();
        double rst1 = pdif.getRst1();
        double difI0 =pdif.getDif0();
        double Ktorm = pdif.getKT1();
        double y = 0.344;
        if(trip){
            for (double x = 0; x < 5.9; x += 0.001) {
                if (x<rst0)            y = x;
                if (x>=rst0 && x<rst1) y = difI0;
                if (x>=rst1)           y = x*Ktorm;
                points.add(new NHMIPoint<>(x, y));
            }
        }else {
            for (double x1 = 0; x1 < 7 ; x1 += 0.001) {
                points.add(new NHMIPoint<>(x1, 2.95));
            }
        }
        return points;
    }
    private static List<NHMIPoint<Double,Double>> TripPoint(RMXU rmxu){
        List<NHMIPoint<Double, Double>> points = new ArrayList<>();
        points.add(new NHMIPoint<>(rmxu.getRstCurrent().getOrtX().getValue().doubleValue(),  rmxu.getRstCurrent().getOrtY().getValue().doubleValue()));
        return points;
    }
}
