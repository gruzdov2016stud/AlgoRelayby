package objects.info;


import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.typeData.TimeStamp;
import objects.data.typeData.Unit;

/**
 * 7.3.8 Класс BCR (считывание показаний двоичного счетчика)
 */
@Getter
@Setter
public class BCR extends Data {

/*
    todo Состояние
*/
    /**Статус двоичного счетчика представлен целочисленным значением*/
    private DataAttribute<Integer> actVal = new DataAttribute<>(0);
    /**Замороженное состояние двоичного счетчика представлено целочисленным значением*/
    private DataAttribute<Integer> frVal = new DataAttribute<>(0);
    /**Время последнего замораживания счетчика*/
    private TimeStamp frTm = new TimeStamp();

    /*
        todo Конфигурация, описание и расширение
    */
    /** Единицы измерения атрибута(ов), представляющие значение данных. В различных
     классах общих данных CDC атрибут units применяется к следующим атрибутам данных:
     BCR actVal, frVal
     MV instMag, mag
     CMV instCVal.Mag, cVal.Mag
     SAV instMag
     HMV har.Mag
     HWYE phsAHar.Mag, phsBHar.Mag, phsCHar.Mag, neutHar.Mag,
     netHar.Mag, resHar.Mmag
     HDEL phsAB.Mag, phsBC.Mag, phsCA.Mag
     АРС setMag
     ASG setMag
     */
    private Unit units = new Unit();
    /**Величина подсчитанного значения на единицу счета. Для вычисления значения используются
     атрибуты actVal/frVal и puIsQty */
    private DataAttribute<Float> puIsQty = new DataAttribute<>(0f);
    /**Значение BOOLEAN (булево), которое управляет процессом «замораживания». Если
     значение установлено на TRUE (истинное), замораживание происходит как указано в
     атрибутах strTm, frPd и frRs. Если значение установлено на FALSE (ложное), замораживания
     не происходит */
    private DataAttribute<Boolean> frEna = new DataAttribute<>(false);
    /**Стартовое время для процесса замораживания. Если значение текущего времени
     позднее стартового времени, первое замораживание должно произойти по истечении
     интервала очередного замораживания (frPd), рассчитанного из настроек стартового
     времени */
    private TimeStamp strTm = new TimeStamp();
    /**Временной интервал в мс между операциями замораживания. Если значение атрибута
     frPd равно 0, в период времени, указанный атрибутом strTm, выполняется только
     единичное замораживание */
    private DataAttribute<Integer> frPd = new DataAttribute<>(0);
    /**Показывает, что после каждого процесса замораживания счетчик автоматически сбрасывается
     на нуль */
    private DataAttribute<Boolean> frRs = new DataAttribute<>(false);

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
