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

import java.util.ArrayList;
import java.util.List;


public class main {
    private static List<LN> logicalNodes = new ArrayList<>();
    public static void main(String[] args) {
//----------------------------------------------------todo Узел LSVC lsvc---------------------------------------------//
        LSVC lsvc = new LSVC();
        /*Включение*/
        String path = "src/main/resources/DPT/Trans2Obm/Trans2ObmVkl";
        /*Внешнее ABC*/
//        String path = "src/main/resources/DPT/Trans2Obm/Trans2ObmVneshABC";
        /*Внутренне A,B,BC*/
//        String path = "src/main/resources/DPT/Trans2Obm/Trans2ObmVnutA";
//        String path = "src/main/resources/DPT/Trans2Obm/Trans2ObmVnutB";
//        String path = "src/main/resources/DPT/Trans2Obm/Trans2ObmVnutBC";
        lsvc.readComtrade(path);
        logicalNodes.add(lsvc);
//----------------------------------------------------todo Узел NHMI nhmi---------------------------------------------//
        NHMI nhmi1 = new NHMI();
        NHMI nhmi2 = new NHMI();
        logicalNodes.add(nhmi1);
        logicalNodes.add(nhmi2);
//----------------------------------------------------todo Узел MMXU mmxu---------------------------------------------//
        /**Измерения*/
        MMXU mmxu1 = new MMXU(
                lsvc.getSignals().get(0),
                lsvc.getSignals().get(1),
                lsvc.getSignals().get(2)
                );
        logicalNodes.add(mmxu1);
        MMXU mmxu2 = new MMXU(
                lsvc.getSignals().get(3),
                lsvc.getSignals().get(4),
                lsvc.getSignals().get(5)
        );
        logicalNodes.add(mmxu2);
//----------------------------------------------------todo Узел MHAI mhai---------------------------------------------//
        MHAI mhai1 = new MHAI(
                lsvc.getSignals().get(0),
                lsvc.getSignals().get(1),
                lsvc.getSignals().get(2)
        );
        logicalNodes.add(mhai1);
        MHAI mhai2 = new MHAI(
                lsvc.getSignals().get(3),
                lsvc.getSignals().get(4),
                lsvc.getSignals().get(5)
        );
        logicalNodes.add(mhai2);
//----------------------------------------------------todo Узел RMXU rmxu---------------------------------------------//
        RMXU rmxu = new RMXU(mmxu1.getA(), mmxu2.getA());
        logicalNodes.add(rmxu);
//----------------------------------------------------todo Узел RMXU rmxu---------------------------------------------//
        PHAR phar = new PHAR(0.2, mhai1.getHA(), mhai2.getHA());
        logicalNodes.add(phar);
//----------------------------------------------------todo Узел PTOC dto--------------------------------------------//
        /**Дифференциальная токовая отсечка*/
        PTOC dto = new PTOC(rmxu.getDifACIc(),1107);
        logicalNodes.add(dto);
//----------------------------------------------------todo Узел PDIF pdif--------------------------------------------//
        /**Дифференциальной защиты трансформатора*/
        PDIF pdif = new PDIF(rmxu.getDifACIc(),phar.getBlkOp(),
                200, 0, 1000, 2000, 1000, 10000, 1000, 10000, 1000);
        logicalNodes.add(pdif);
// ----------------------------------------------------todo Узел PTRC ptrc--------------------------------------------//
        PTRC ptrc = new PTRC(dto.getOp(), pdif.getOp());
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
//----------------------------------------------------todo Отображение--------------------------------------------//
        nhmi1.addSignals("ВН",
                new NHMISignal("IAвн", lsvc.getSignals().get(0).getInstMag().getF()),
                new NHMISignal("IBвн", lsvc.getSignals().get(1).getInstMag().getF()),
                new NHMISignal("ICвн", lsvc.getSignals().get(2).getInstMag().getF()));
        nhmi1.addSignals("НН",
                new NHMISignal("IAнн", lsvc.getSignals().get(3).getInstMag().getF()),
                new NHMISignal("IBнн", lsvc.getSignals().get(4).getInstMag().getF()),
                new NHMISignal("ICнн", lsvc.getSignals().get(5).getInstMag().getF()));

        nhmi1.addSignals(new NHMISignal("<Блок>", phar.getBlkOp().getStValPhGeneral()));
        nhmi1.addSignals(new NHMISignal("ДТО Str", dto.getStr().getGeneral()));
        nhmi1.addSignals(new NHMISignal("ДТО Op", dto.getOp().getGeneral()));
        nhmi1.addSignals(new NHMISignal("ДЗТ Str", pdif.getStr().getGeneral()));
        nhmi1.addSignals(new NHMISignal("ДЗТ Op", pdif.getOp().getGeneral()));

        nhmi1.addSignals(new NHMISignal("SwitchMode ВН", xcbr1.getPos().getCtIVal()));
        nhmi1.addSignals(new NHMISignal("SwitchMode НН", xcbr2.getPos().getCtIVal()));

        while (lsvc.hasNext()){
            logicalNodes.forEach(LN::process);
        }

    }
}
