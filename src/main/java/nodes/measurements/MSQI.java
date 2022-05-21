package nodes.measurements;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.typeData.Vector;
import objects.measured.DEL;
import objects.measured.MV;
import objects.measured.SEQ;
import objects.measured.WYE;

/**
 * Логический узел MSQI «Последовательность и небаланс»
 * Для вычисления симметричных составляющих токов и напряжений прямой и обратной последовательности
 */
@Getter
@Setter
public class MSQI extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Входные значения токов
     */
    private WYE A = new WYE();
    private WYE PhV = new WYE();
    private Vector vectorB = new Vector();
    private Vector vectorC = new Vector();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**
     * ПП, ОП, НП тока
     */
    private SEQ SeqA = new SEQ();
    /**
     * ПП, ОП, НП напряжения
     */
    private SEQ SeqV = new SEQ();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public void process() {
        initSEQ(A, SeqA);
        initSEQ(PhV, SeqV);
    }

    public void initSEQ(WYE value, SEQ Seq) {
        /** Поворот вектора В, C на 120 в прямом направлении */
        vectorB.setValue(value.getPhsB().getCVal().getMag().getValue(), value.getPhsB().getCVal().getAng().getValue() + 120);
        vectorC.setValue(value.getPhsC().getCVal().getMag().getValue(), value.getPhsC().getCVal().getAng().getValue() - 120);

        /** Расчет ПП */
        Seq.getС1().getCVal().setValueO(
                (value.getPhsA().getCVal().getOrtX().getValue() + vectorB.getOrtX().getValue() + vectorC.getOrtX().getValue()) / 3,
                (value.getPhsA().getCVal().getOrtY().getValue() + vectorB.getOrtY().getValue() + vectorC.getOrtY().getValue()) / 3);
        /** Поворот вектора В, C на 120 в обратном направлении */
        vectorB.setValue(value.getPhsB().getCVal().getMag().getValue(), value.getPhsB().getCVal().getAng().getValue() - 120);
        vectorC.setValue(value.getPhsC().getCVal().getMag().getValue(), value.getPhsC().getCVal().getAng().getValue() + 120);

        /** Расчет 0П */
        Seq.getС2().getCVal().setValueO(
                (value.getPhsA().getCVal().getOrtX().getValue() + vectorB.getOrtX().getValue() + vectorC.getOrtX().getValue()) / 3,
                (value.getPhsA().getCVal().getOrtY().getValue() + vectorB.getOrtY().getValue() + vectorC.getOrtY().getValue()) / 3);

        /** Расчет НП */
        Seq.getС3().getCVal().setValueO(
                (value.getPhsA().getCVal().getOrtX().getValue() + value.getPhsB().getCVal().getOrtX().getValue() + value.getPhsC().getCVal().getOrtX().getValue()) / 3,
                (value.getPhsA().getCVal().getOrtY().getValue() + value.getPhsB().getCVal().getOrtY().getValue() + value.getPhsC().getCVal().getOrtY().getValue()) / 3);


    }
//        /*Поворачиваем вектора согласно формуле нахождения todo ПП*/
//        vectorB.setValue(value.getPhsB().getInstCVal().getMag().getValue(), value.getPhsB().getInstCVal().getAng().getValue() + angelW);
//        vectorC.setValue(value.getPhsC().getInstCVal().getMag().getValue(), value.getPhsC().getInstCVal().getAng().getValue() - angelW);
//        /*Запишем значение прямой последовательности*/
//        /*Проекция на ось x*/
//        Seq.getС1().getInstCVal().getOrtX().setValue(
//                (value.getPhsA().getInstCVal().getOrtX().getValue()+vectorB.getOrtX().getValue()+vectorC.getOrtX().getValue())/3);
//        /*Проекция на ось y*/
//        Seq.getС1().getInstCVal().getOrtY().setValue(
//                (value.getPhsA().getInstCVal().getOrtY().getValue()+vectorB.getOrtY().getValue()+vectorC.getOrtY().getValue())/3);
//        /*sqrt(x*x+y*y)*/
//        Seq.getС1().getInstCVal().getMag().setValue((float)Math.sqrt(
//                Seq.getС1().getInstCVal().getOrtX().getValue() * Seq.getС1().getInstCVal().getOrtX().getValue()+
//                        Seq.getС1().getInstCVal().getOrtY().getValue() * Seq.getС1().getInstCVal().getOrtY().getValue()));
//
//        /*Поворачиваем вектора согласно формуле нахождения todo ОП*/
//        vectorB.setValue(value.getPhsB().getInstCVal().getMag().getValue(), value.getPhsB().getInstCVal().getAng().getValue() - angelW);
//        vectorC.setValue(value.getPhsC().getInstCVal().getMag().getValue(), value.getPhsC().getInstCVal().getAng().getValue() + angelW);
//        /*Запишем значение ОП*/
//        Seq.getС2().getInstCVal().getOrtX().setValue(
//                (value.getPhsA().getInstCVal().getOrtX().getValue()+vectorB.getOrtX().getValue()+vectorC.getOrtX().getValue())/3);
//        Seq.getС2().getInstCVal().getOrtY().setValue(
//                (value.getPhsA().getInstCVal().getOrtY().getValue()+vectorB.getOrtY().getValue()+vectorC.getOrtY().getValue())/3);
//        Seq.getС2().getInstCVal().getMag().setValue((float)Math.sqrt(
//                Seq.getС2().getInstCVal().getOrtX().getValue()* Seq.getС2().getInstCVal().getOrtX().getValue()+
//                        Seq.getС2().getInstCVal().getOrtY().getValue()* Seq.getС2().getInstCVal().getOrtY().getValue()));
//
//        /*Поворачиваем вектора согласно формуле нахождения todo НП*/
//        vectorB.setValue(value.getPhsB().getInstCVal().getMag().getValue(), value.getPhsB().getInstCVal().getAng().getValue());
//        vectorC.setValue(value.getPhsC().getInstCVal().getMag().getValue(), value.getPhsC().getInstCVal().getAng().getValue());
//        /*Запишем значение НП*/
//        Seq.getС3().getInstCVal().getOrtX().setValue(
//                (value.getPhsA().getInstCVal().getOrtX().getValue()+vectorB.getOrtX().getValue()+vectorC.getOrtX().getValue())/3);
//        Seq.getС3().getInstCVal().getOrtY().setValue(
//                (value.getPhsA().getInstCVal().getOrtY().getValue()+vectorB.getOrtY().getValue()+vectorC.getOrtY().getValue())/3);
//        Seq.getС3().getInstCVal().getMag().setValue((float)Math.sqrt(
//                Seq.getС3().getInstCVal().getOrtX().getValue()* Seq.getС3().getInstCVal().getOrtX().getValue()+
//                        Seq.getС3().getInstCVal().getOrtY().getValue()* Seq.getС3().getInstCVal().getOrtY().getValue()));


    // ================================================ Не используемые ================================================
    /**
     * DQ0 последовательность
     */
    private SEQ DQ0Seq = new SEQ();
    /**
     * Ток небаланса
     */
    private WYE ImbA = new WYE();
    /**
     * Ток небаланса ОП
     */
    private MV ImbNgA = new MV();
    /**
     * Напряжения небаланса ОП
     */
    private MV ImbNgV = new MV();
    /**
     * Междуфазное напряжение небаланса
     */
    private DEL ImbPPV = new DEL();
    /**
     * Напряжение небаланса
     */
    private WYE ImbV = new WYE();
    /**
     * Ток небаланса НП
     */
    private MV ImbZroA = new MV();
    /**
     * Напряжение небаланса НП
     */
    private MV ImbZroV = new MV();
    /**
     * Максимальный ток небаланса
     */
    private MV MaxImbA = new MV();
    /**
     * Максимальное междуфазное напряжение небаланса
     */
    private MV MaxImbPPV = new MV();
    /**
     * Максимальное напряжение небаланса
     */
    private MV MaxImbV = new MV();


}
