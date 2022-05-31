package process.protection.PDIF;

import nodes.common.LN;
import nodes.controls.CSWI;
import nodes.gui.NHMI;
import nodes.gui.other.NHMISignal;
import nodes.measurements.MHAI;
import nodes.measurements.MMXU;
import nodes.measurements.RMXU;
import nodes.measurements.utils.LSVC;
import nodes.protection.PDIF;
import nodes.protection.PHAR;
import nodes.protection.PTOC;
import nodes.protection.PTRC;
import nodes.switchgear.XCBR;
import objects.measured.SAV;

import java.util.ArrayList;
import java.util.List;


public class main {
    private static List<LN> logicalNodes = new ArrayList<>();
    public static void main(String[] args) {
//----------------------------------------------------todo Узел LSVC lsvc---------------------------------------------//
        LSVC lsvc = new LSVC();
        /*Включение*/
//        String path = "src/main/resources/DPT/Trans2Obm/Trans2ObmVkl"; // Блокруется при уставки 0,1
        /*Внешнее ABC*/
//        String path = "src/main/resources/DPT/Trans2Obm/Trans2ObmVneshABC";
        /*Внутренне A,B,BC*/
//        String path = "src/main/resources/DPT/Trans2Obm/Trans2ObmVnutA";
//        String path = "src/main/resources/DPT/Trans2Obm/Trans2ObmVnutB";
        String path = "src/main/resources/DPT/Trans2Obm/Trans2ObmVnutBC"; // Блокруется при уставки 0,1 ДЗТ, ДТО отработает
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
        MHAI mhai1 = new MHAI(1,5);
        mhai1.setInstIa(lsvc.getSignals().get(0));
        mhai1.setInstIb(lsvc.getSignals().get(1));
        mhai1.setInstIc(lsvc.getSignals().get(2));
        logicalNodes.add(mhai1);
        MHAI mhai2 = new MHAI(1,5);
        mhai1.setInstIa(lsvc.getSignals().get(3));
        mhai1.setInstIb(lsvc.getSignals().get(4));
        mhai1.setInstIc(lsvc.getSignals().get(5));
        logicalNodes.add(mhai2);
//----------------------------------------------------todo Узел RMXU rmxu---------------------------------------------//
        RMXU rmxu = new RMXU();
        rmxu.getInputs().add(mmxu1.getA());
        rmxu.getInputs().add(mmxu2.getA());
        logicalNodes.add(rmxu);
//----------------------------------------------------todo Узел RMXU rmxu---------------------------------------------//
        PHAR phar = new PHAR(0.1);// при 100 не сработает
        phar.getHInputs().add(mhai1.getHA());
        phar.getHInputs().add(mhai2.getHA());
        logicalNodes.add(phar);
//----------------------------------------------------todo Узел PTOC dto--------------------------------------------//
        /**Дифференциальная токовая отсечка*/
        PTOC dto = new PTOC(20);
        dto.setDifACIc(rmxu.getDifACIc());
        logicalNodes.add(dto);
//----------------------------------------------------todo Узел PDIF pdif--------------------------------------------//
        /**Дифференциальной защиты трансформатора*/
        PDIF pdif = new PDIF(100,
                        0, 0.5,
                                  1, 0.5,
                                5.0, 2.5,
                                  10,5 );

        pdif.setDifACIc(rmxu.getDifACIc());
        pdif.setBlkOp(phar.getBlkOp());
        pdif.calc();
        logicalNodes.add(pdif);
// ----------------------------------------------------todo Узел PTRC ptrc--------------------------------------------//
        PTRC ptrc = new PTRC(2);
        ptrc.getOpS().add(dto.getOp());
        ptrc.getOpS().add(pdif.getOp());
        logicalNodes.add(ptrc);
//----------------------------------------------------todo Узел CSWI cswi---------------------------------------------//
        CSWI cswi1 = new CSWI(true, 2);
        cswi1.setOpOpn(ptrc.getTr());
        logicalNodes.add(cswi1);
        CSWI cswi2 = new CSWI(true, 2);
        cswi2.setOpOpn(ptrc.getTr());
        logicalNodes.add(cswi2);
//----------------------------------------------------todo Узел XCBR xcbr---------------------------------------------//
        XCBR xcbr1 = new XCBR();
        xcbr1.setPos(cswi1.getPos());
        logicalNodes.add(xcbr1);

        XCBR xcbr2 = new XCBR();
        xcbr2.setPos(cswi2.getPos());
        logicalNodes.add(xcbr2);
//----------------------------------------------------todo Узел NHMI nhmi---------------------------------------------//
        NHMI nhmi1 = new NHMI();
        logicalNodes.add(nhmi1);
        nhmi1.addSignals("ВН",
                new NHMISignal("IAвн", lsvc.getSignals().get(0).getInstMag().getF()),
                new NHMISignal("IBвн", lsvc.getSignals().get(1).getInstMag().getF()),
                new NHMISignal("ICвн", lsvc.getSignals().get(2).getInstMag().getF()));
        nhmi1.addSignals("НН",
                new NHMISignal("IAнн", lsvc.getSignals().get(3).getInstMag().getF()),
                new NHMISignal("IBнн", lsvc.getSignals().get(4).getInstMag().getF()),
                new NHMISignal("ICнн", lsvc.getSignals().get(5).getInstMag().getF()));
        nhmi1.addSignals(new NHMISignal("Действующее значение ВН", mmxu1.getA().getPhsA().getCVal().getMag()));
        nhmi1.addSignals(new NHMISignal("Действующее значение НН", mmxu2.getA().getPhsA().getCVal().getMag()));
        nhmi1.addSignals(new NHMISignal("IaVn1", mhai1.getHA().getPhsAHar().get(1).getMag())); //первая гармоника по вн
        nhmi1.addSignals(
                new NHMISignal("IaVn5phA", mhai1.getHA().getPhsAHar().get(5).getMag()),
                new NHMISignal("IaVn5phB", mhai1.getHA().getPhsBHar().get(5).getMag()),
                new NHMISignal("IaVn5phC", mhai1.getHA().getPhsCHar().get(5).getMag()),
                new NHMISignal("SrtValBlock", phar.getStrBlock().getSetMag()));
        nhmi1.addSignals(new NHMISignal("IaNn1", mhai2.getHA().getPhsAHar().get(1).getMag())); //первая гармоника по нн
        nhmi1.addSignals(new NHMISignal("IaNn5", mhai2.getHA().getPhsAHar().get(5).getMag()),
                new NHMISignal("SrtValBlock", phar.getStrBlock().getSetMag()));

        NHMI nhmi2 = new NHMI();
        logicalNodes.add(nhmi2);
        nhmi2.addSignals(new NHMISignal("IbVn", lsvc.getSignals().get(0).getInstMag().getF()));
        nhmi2.addSignals(new NHMISignal("IbNn", lsvc.getSignals().get(3).getInstMag().getF()));
        nhmi2.addSignals(
                new NHMISignal("DifA", rmxu.getDifACIc().getPhsA().getCVal().getMag()),
                new NHMISignal("SrtValDTO", rmxu.getDifACIc().getPhsA().getCVal().getMag()),
                new NHMISignal("StrValDZT", pdif.getTripPoint().getPhsA().getCVal().getMag()));
        nhmi2.addSignals(new NHMISignal("<Блок>", phar.getBlkOp().getStValPhGeneral()));
        nhmi2.addSignals(new NHMISignal("ТО Op", dto.getOp().getGeneral()));
        nhmi2.addSignals(new NHMISignal("ДЗТ Str", pdif.getStr().getGeneral()));
        nhmi2.addSignals(new NHMISignal("ДЗТ Op", pdif.getOp().getGeneral()));

        nhmi2.addSignals(new NHMISignal("SwitchMode ВН", xcbr1.getPos().getCtIVal()));
        nhmi2.addSignals(new NHMISignal("SwitchMode НН", xcbr2.getPos().getCtIVal()));

        while (lsvc.hasNext()){
            logicalNodes.forEach(LN::process);
        }

    }
}
