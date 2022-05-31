package nodes.protection;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.DataAttribute;
import objects.data.typeData.Point;
import objects.info.ACD;
import objects.info.SPS;
import objects.managment.INC;
import objects.measured.HWYE;
import objects.setStatus.ING;
import objects.task.ASG;

import java.util.ArrayList;

/**LN: Блокировка по гармоникам.*/
@Getter @Setter
public class PHAR extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Последовательность тока гармоник или интергармоник*/
    private HWYE HA = new HWYE();
    /** Уставка блокировки по 2 или 5 гармоники */
    private ASG StrBlock = new ASG();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Блокировка зависимых функций защиты */
    private SPS BlkOp = new SPS();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    /** Гармонический состав сигнала(для блокировки по какой-либо гармонике) */
    private ArrayList<HWYE> hInputs = new ArrayList<>();
    /**
     * @param hBlock - Уровень блокировки по 2 или 5 гармоники (%)
     */
    public PHAR(double hBlock) {
        StrBlock.getSetMag().setValue((float) hBlock);
        this.BlkOp.getStValPhA().setValue(false);
        this.BlkOp.getStValPhC().setValue(false);
        this.BlkOp.getStValPhB().setValue(false);
        this.BlkOp.getStValPhGeneral().setValue(false);
    }

    @Override
    public void process() {
        /* Блокировка по 2 гармонике */
        for (HWYE w : hInputs) {
            if (!BlkOp.getStValPhA().getValue()) {
                BlkOp.getStValPhA().setValue(
                        w.getPhsAHar().get(5).getMag().getValue() / w.getPhsAHar().get(1).getMag().getValue()
                                > StrBlock.getSetMag().getValue());
            } else BlkOp.getStValPhA().setValue(false);

            if (!BlkOp.getStValPhB().getValue()) {
                BlkOp.getStValPhB().setValue(
                        w.getPhsBHar().get(5).getMag().getValue() / w.getPhsBHar().get(1).getMag().getValue()
                                > StrBlock.getSetMag().getValue());
            } else BlkOp.getStValPhB().setValue(false);

            if (!BlkOp.getStValPhC().getValue()) {
                BlkOp.getStValPhC().setValue(
                        w.getPhsCHar().get(5).getMag().getValue() / w.getPhsCHar().get(1).getMag().getValue()
                                > StrBlock.getSetMag().getValue());
            } else BlkOp.getStValPhC().setValue(false);

            if(BlkOp.getStValPhA().getValue() || BlkOp.getStValPhB().getValue() || BlkOp.getStValPhC().getValue())
                BlkOp.getStValPhGeneral().setValue(true);
        }
    }
    // ================================================ Не используемые ================================================

    // todo Информация об общих логических узлах
    /**
     *Счетчик числа переключений со сбросом
     */
    private INC OpCntRs = new INC();
    // todo Информация о статусе
    /** Пуск на Срабатывание (срабатывает при необходимости подавления) (атрибут ACD класса общих данных) означает, что обнаружено нарушение или недопустимое состояние. Элемент Str может включать в себя информацию о фазе и направлении*/
    private ACD Str = new ACD();
    // todo Параметры настройки
    /**Количество подавлений по гармоникам*/
    private ING HaRst = new ING();
    /**Начальное значение*/
    private ASG PhStr = new ASG();
    /**Время задержки срабатывания */
    private ING OpDITmms = new ING();
    /**Время задержки сброса - Время выдержки в миллисекундах (мс) до сброса, как только будут выполнены условия для сброса */
    private ING RsDITmms = new ING();





}
