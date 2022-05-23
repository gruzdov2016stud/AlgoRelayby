package nodes.registration;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.DataAttribute;
import objects.info.ACD;
import objects.measured.SEQ;
import objects.measured.WYE;
import objects.task.ASG;

@Getter
@Setter
/**Логический узел RDIR «Направленный элемент защиты»
 * Применяется для реализации ОНМ. Применяется один экземпляр ЛУ на одну ступень функции.
 * Экземпляры ЛУ размещаются в логических устройствах РЗА.*/
public class RDIR_PDIS extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /** Сопротивление из MMXU */
    private WYE Z = new WYE();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /** Направление */
    private ACD Dir = new ACD();
    /** Минимальный фазный угол для прямого направления */
    private ASG MinFwdAng = new ASG();
    /** Максимальный фазный угол для прямого направления */
    private ASG MaxFwdAng = new ASG();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    // Если мощности нулевой последовательности PhsA,PhsB,PhsC больше нул, то Direction получается значение FORWARD, которое говорит
    // о том, что ступень направленная
    //
    ///////////////////////////////////////////////////////////////////////////
    /** Углы векторов сопротивлений */
    private float Aang;
    private float Bang;
    private float Cang;

    @Override
    public void process() {
        Aang = Z.getPhsA().getCVal().getAng().getValue();
        Bang = Z.getPhsB().getCVal().getAng().getValue();
        Cang = Z.getPhsC().getCVal().getAng().getValue();

        if (Aang < MaxFwdAng.getSetMag().getF().getValue() && Aang > MinFwdAng.getSetMag().getF().getValue()){
            Dir.getPhsA().setValue(true); // Сопротивелние по фазе A входит в защищаемую зону
        } else {
            Dir.getPhsA().setValue(false);
        }
        if (Bang < MaxFwdAng.getSetMag().getF().getValue() && Bang > MinFwdAng.getSetMag().getF().getValue()){
            Dir.getPhsB().setValue(true); // Сопротивелние по фазе B входит в защищаемую зону
        } else {
            Dir.getPhsB().setValue(false);
        }
        if (Cang < MaxFwdAng.getSetMag().getF().getValue() && Cang > MinFwdAng.getSetMag().getF().getValue()){
            Dir.getPhsC().setValue(true); // Сопротивелние по фазе C входит в защищаемую зону
        } else {
            Dir.getPhsC().setValue(false);
        }
    }

    public void setArea(float leftAng, float rightAng) {
        MinFwdAng.getSetMag().setF(new DataAttribute<>(rightAng));
        MaxFwdAng.getSetMag().setF(new DataAttribute<>(leftAng));
    }
    // ================================================ Не используемые ================================================
    /**
     * Угол характеристики
     */
    private ASG ChrAng = new ASG();
    /**
     * Минимальный фазный угол для обратного направления
     */
    private ASG MinRvAng = new ASG();
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

