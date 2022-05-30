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
    private ASG HBlock = new ASG();
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
    private HWYE hI = new HWYE();

    /**
     * @param hBlock - Уставка блокировки по 2 или 5 гармоники (%)
     * @param hAs HA_1...HA_N- Последовательность тока гармоник от каждого измерителя
     */
    public PHAR(double hBlock, HWYE... hAs) {
        HBlock.getSetMag().setValue((float) hBlock);
        for(int i = 0; i < hAs.length; i ++) {
            this.HA = hAs[i];
            hInputs.add(HA);
        }
        this.BlkOp.getStValPhA().setValue(false);
        this.BlkOp.getStValPhC().setValue(false);
        this.BlkOp.getStValPhB().setValue(false);
        this.BlkOp.getStValPhGeneral().setValue(false);
    }

    @Override
    public void process() {
        /* Блокировка по 5 гармонике */
        for (HWYE w : hInputs) {
            if (!BlkOp.getStValPhA().getValue()) {
                BlkOp.getStValPhA().setValue(
                        w.getPhsAHar().get(2).getMag().getValue() / w.getPhsAHar().get(1).getMag().getValue() > HBlock.getSetMag().getValue());
            }
            if (!BlkOp.getStValPhB().getValue()) {
                BlkOp.getStValPhB().setValue(
                        w.getPhsBHar().get(2).getMag().getValue() / w.getPhsBHar().get(1).getMag().getValue() > HBlock.getSetMag().getValue());
            }
            if (!BlkOp.getStValPhC().getValue()) {
                BlkOp.getStValPhC().setValue(
                        w.getPhsCHar().get(2).getMag().getValue() / w.getPhsCHar().get(1).getMag().getValue() > HBlock.getSetMag().getValue());
            }
            BlkOp.getStValPhGeneral().setValue(
                    BlkOp.getStValPhA().getValue()||BlkOp.getStValPhB().getValue()||BlkOp.getStValPhC().getValue()
            );
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
