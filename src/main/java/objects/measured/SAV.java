package objects.measured;


import lombok.Getter;
import lombok.Setter;
import objects.data.AnalogValue;
import objects.data.Data;
import objects.data.typeData.Quality;
import objects.data.typeData.ScaledValueConfig;
import objects.data.typeData.TimeStamp;
import objects.data.typeData.Unit;

/**
 * 7.4.4 Класс SAV (выборочные значения)
 * <p>
 * В классе приведено определение класса общих данных Sampled value (SAV).
 * Этот класс общих данных используют для представления выборок мгновенных значений аналоговых сигналов.
 * Эти значения обычно передаются с помощью модели передачи выборочных значений, определенной в МЭК 61850-7-2.
 */
@Getter
@Setter
public class SAV extends Data {
/*
    todo Измеряемые атрибуты
*/
    /**
     * Величина мгновенного значения измеренного значения
     */
    private AnalogValue instMag = new AnalogValue();
    /**
     * Качество атрибута(ов), представляющего значение данных.
     */
    private Quality q = new Quality();
    /**
     * Timestamp последнего изменения является одним из атрибутов, представляющим
     * значение данных или в атрибуте q.
     */
    private TimeStamp t = new TimeStamp();
/*
    todo Конфигурация, описание и расширение
*/
    /**
     * Единицы измерения атрибута(ов), представляющие значение данных. В различных
     * классах общих данных CDC атрибут units применяется к следующим атрибутам данных
     */
    private Unit units = new Unit();
    /**
     * Конфигурация масштабируемого значения. Используется для конфигурирования представления
     * масштабируемого значения атрибутов instMag, mag, subMag или setMag
     */
    private ScaledValueConfig sVC = new ScaledValueConfig();
    /**
     * Минимальное значение измерения процесса, для которого значения / или f рассматриваются
     * в пределах значений процесса. Если значение ниже, должно быть установлено
     * соответствующее значение q (validity = questionable, detailQual = outOfRange)
     */
    private AnalogValue min = new AnalogValue();
    /**
     * Максимальное значение измерения процесса, для которого значения / или f рассматриваются
     * в пределах значений процесса. Если значение выше, должно быть установлено
     * соответствующее значение q (validity = questionable, detailQual = outOfRange)
     */
    private AnalogValue max = new AnalogValue();

    private String d;
    private String dU;
    private String cdcNs;
    private String cdcName;
    private String dataNs;

}
