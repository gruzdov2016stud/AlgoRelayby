package objects.measured;


import lombok.Getter;
import lombok.Setter;
import objects.data.AnalogValue;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.Range;
import objects.data.typeData.*;

/**7.4.2 Класс MV (измеряемые значения)
 */
@Getter
@Setter
public class MV extends Data {
    /*
        todo Измеряемые атрибуты
    */
    /**Качество атрибута(ов), представляющего значение данных.*/
    private Quality q = new Quality();
    /**Timestamp последнего изменения является одним из атрибутов, представляющим
     значение данных или в атрибуте q.*/
    private TimeStamp t = new TimeStamp();
    /**Значение, определенное на основе зоны нечувствительности. Как показано ниже, основано
     на вычислении зоны нечувствительности из атрибута instMag. Значение mag
     обновляется до текущего значения instMag, если значение изменилось согласно параметру
     конфигурации db. */
    private AnalogValue mag = new AnalogValue();
    /**Величина мгновенного значения измеренного значения*/
    private AnalogValue instMag = new AnalogValue();
    /**Диапазон текущего значения атрибутов instMag или instCVal.mag. Он может быть
     использован для выдачи сообщения о событии в случае изменения текущего значения
     и перехода в другой диапазон.*/
    private DataAttribute<Range> rangeMV = new DataAttribute<>(Range.NORMAL);

    /*
        todo Замещение
    */
    /**Используется для разрешения замещения.*/
    private DataAttribute<Boolean> subEna = new DataAttribute<>(false);
    private AnalogValue subMag = new AnalogValue();
    /**Значение используется для замещения атрибута данных q */
    private Quality subQ = new Quality();
    /** Показывает адрес устройства, выполнившего замещение. Значение NULL (нуль) используется,
     если атрибут subEna имеет значение FALSE или если устройство неизвестно*/
    private String subID;
    /*
        todo Конфигурация, описание и расширение
    */
    private Unit nits = new Unit();
    private DataAttribute<Integer> db = new DataAttribute<>(0);
    private DataAttribute<Integer> zeroDb = new DataAttribute<>(0); // 0...100 000
    private ScaledValueConfig sVC = new ScaledValueConfig();
    private RangeConfig rangeC = new RangeConfig();
    private DataAttribute<Integer> smpRate = new DataAttribute<>(0);
        /**
     * Текстовое описание данных. В случае класса LPL в классе общих данных описание
     * относится к логическому узлу
     */
    private String d;
    private String dU;
    private String cdcNs;
    private String cdcName;
    private String dataNs;
/*
    todo Методы
*/
}
