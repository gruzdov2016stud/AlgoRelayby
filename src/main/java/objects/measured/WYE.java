package objects.measured;


import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.AngRefWYE;

/** 7.4.5 Класс WYE (Y — «звезда»)
 *
 *  В классе приведено определение класса общих данных WYE («звезда»).
 *  Этот класс представляет собой набор одновременно измереных значений в трехфазной сети,
 *  представляющих значения напряжений фаз относительно земли.
 * */

public class WYE extends Data {
    /*
        todo Состояние
    */
    /**Значение фазы А.В классе WYE значения для атрибутов phsA, phsB, phsC, neut, net и
     res должны быть получены или определены одновременно.*/
    @Getter @Setter
    private CMV phsA = new CMV();
    /**Значение фазы B.В классе WYE значения для атрибутов phsA, phsB, phsC, neut, net и
     res должны быть получены или определены одновременно.*/
    @Getter @Setter
    private CMV phsB = new CMV();
    /**Значение фазы C.В классе WYE значения для атрибутов phsA, phsB, phsC, neut, net и
     res должны быть получены или определены одновременно.*/
    @Getter @Setter
    private CMV phsC = new CMV();
    /**Значение фазы Нейтрали.В классе WYE значения для атрибутов phsA, phsB, phsC, neut, net и
     res должны быть получены или определены одновременно.*/
    @Getter @Setter
    private CMV phsN = new CMV();
    /**Значение фаза-нейтраль, подробнее см. атрибут phsA.В классе WYE значения для атрибутов phsA, phsB, phsC, neut, net и
     res должны быть получены или определены одновременно.*/
    @Getter @Setter
    private CMV neut = new CMV();

    /**
     *  net — представляет собой алгебраическую сумму мгновенных значений токов,
     *  протекающих через все провода под напряжением (сумма фазных токов) и нейтраль в точке электрической установки*/
    private CMV net = new CMV();
    /**
     *Остаточный ток. Остаточный ток представляет собой алгебраическую сумму мгновенных
     * значений токов, протекающих через все провода под напряжением (сумма фазных
     * токов) в точке электрической установки
     */
    private CMV res = new CMV();
/*
    todo Конфигурация, описание и расширение
*/
    private DataAttribute<AngRefWYE> angRefWYE = new DataAttribute<>(AngRefWYE.Aa);

    /**
     * Текстовое описание данных. В случае класса LPL в классе общих данных описание
     * относится к логическому узлу
     */
    private String d;
    private String dU;
    private String cdcNs;
    private String cdcName;
    private String dataNs;
}