package objects.data.typeData;

import lombok.Getter;
import lombok.Setter;
import objects.data.AnalogValue;
import objects.data.Data;

/**
 * МЭК 61850_7_3_6.5 Тип Range configuration (конфигурация диапазона значений)
 * Тип Range configuration используется для конфигурирования предельных значений
 */
@Getter
@Setter
public class RangeConfig extends Data {
    /**
     * Атрибуты hhLim, hLim, ILim, IILim — параметры конфигурирования, их применяют в
     * контексте атрибута диапазона, как определено в разделе 8.
     */
    private AnalogValue hhLim = new AnalogValue();
    /**
     * Атрибуты hhLim, hLim, ILim, IILim — параметры конфигурирования, их применяют в
     * контексте атрибута диапазона, как определено в разделе 8.
     */
    private AnalogValue hLim = new AnalogValue();
    /**
     * Атрибуты hhLim, hLim, ILim, IILim — параметры конфигурирования, их применяют в
     * контексте атрибута диапазона, как определено в разделе 8.
     */
    private AnalogValue ILim = new AnalogValue();
    /**
     * Атрибуты hhLim, hLim, ILim, IILim — параметры конфигурирования, их применяют в
     * контексте атрибута диапазона, как определено в разделе 8.
     */
    private AnalogValue IILim = new AnalogValue();
    /**
     * Атрибут min (минимум) представляет минимальное измерение процесса,
     * для которого значения / или f рассматриваются в области предельных значений процесса.
     * Если значение ниже, соответственно устанавливается значение q (validity = questionable,
     * detailQual = outOfRange).
     */
    private AnalogValue min = new AnalogValue();
    /**
     * Атрибут max (максимум) представляет максимальное измерение
     * процесса, для которого значения / или f рассматриваются в области предельных значений
     * процесса. Если значение выше, соответственно устанавливается значение q
     * (validity = questionable, detailQual = outOfRange).
     */
    private AnalogValue max = new AnalogValue();

}
