package nodes.registration;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.enums.Direction;
import objects.info.ACD;
import objects.measured.SEQ;
import objects.measured.WYE;
import objects.task.ASG;

@Getter
@Setter
/**Логический узел RDIR «Направленный элемент защиты»
 * Применяется для реализации ОНМ. Применяется один экземпляр ЛУ на одну ступень функции.
 * Экземпляры ЛУ размещаются в логических устройствах РЗА.*/
public class RDIR extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**
     * ПП, ОП, НП тока
     */
    private SEQ SeqA = new SEQ();
    /**
     * ПП, ОП, НП напряжения
     */
    private SEQ SeqV = new SEQ();
    /**
     * Активная мощность
     */
    private WYE W = new WYE();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Направление
     */
    private ACD Dir = new ACD();

    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    // Если мощности нулевой последовательности PhsA,PhsB,PhsC больше нул, то Direction получается значение FORWARD, которое говорит
    // о том, что ступень направленная
    //
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void process() {
        /*Расчет угла*/
        float ang = (float) Math.toRadians(
                SeqV.getС3().getCVal().getMag().getValue() - SeqA.getС3().getCVal().getMag().getValue());
        /*Расчет sin*/
        float sinS = (float) Math.sin(ang);
        /*Расчет мощности нулевой последовательности*/
        float TotVA0 = 6 * SeqA.getС3().getCVal().getMag().getValue() *
                SeqV.getС3().getCVal().getMag().getValue() * sinS;
        /**Проверка направления мощности нулевой последовательности */
        System.out.println(TotVA0);
        Dir.getDirGeneral().setValue(TotVA0 > 0 ? Direction.FORWARD : Direction.BACKWARD);
    }


    // ================================================ Не используемые ================================================
    /**
     * Угол характеристики
     */
    private ASG ChrAng = new ASG();
    /**
     * Минимальный фазный угол для прямого направления
     */
    private ASG MinFwdAng = new ASG();
    /**
     * Минимальный фазный угол для обратного направления
     */
    private ASG MinRvAng = new ASG();
    /**
     * Максимальный фазный угол для прямого направления
     */
    private ASG MaxFwdAng = new ASG();
    /**
     * Максимальный фазный угол для обратного направления
     */
    private ASG MaxRvAng = new ASG();
    /**
     * Минимальный рабочий ток
     */
    private ASG BlkValA = new ASG();
    /**
     * Минимальное рабочее напряжение
     */
    private ASG BlkValV = new ASG();
    /**
     * Минимальное междуфазное напряжение
     */
    private ASG MinPPV = new ASG();
}

