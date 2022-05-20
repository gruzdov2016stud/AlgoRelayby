package objects.task;

import lombok.Getter;
import lombok.Setter;
import objects.data.AnalogValue;
import objects.data.Data;
import objects.data.typeData.ScaledValueConfig;
import objects.data.typeData.Unit;

/**7.8.2 Класс ASG (задание значения аналогового сигнала)
 *
 */
@Getter
@Setter
public class ASG  extends Data {
    /*
    todo Задание значений аналоговой переменной
    */
    /**Значение является настройкой параметра или заданным значением аналогового сигнала */
    private AnalogValue setMag = new AnalogValue();
    /*
        todo Конфигурация, описание и расширение
    */
    /**Единицы измерения атрибута(ов), представляющие значение данных. В различных
     классах общих данных CDC атрибут units применяется к следующим атрибутам данных */
    private Unit units = new Unit();
    /**Конфигурация масштабируемого значения. Используется для конфигурирования пред
     ставления масштабируемого значения атрибутов instMag, mag, subMag или setMag */
    private ScaledValueConfig sVC = new ScaledValueConfig();
    /**Наряду с атрибутом maxVal определяет диапазон настройки атрибута ctIVal (классы
     INC, BSC, ISC в классе общих данных CDC), атрибута setVal (класс ING в классе общих
     данных CDC) или атрибута setMag (классы АРС, ASG в классе общих данных CDC) */
    private AnalogValue minVal = new AnalogValue();
    /**Наряду с атрибутом minVal определяет диапазон настройки атрибута ctIVal (классы
     INC, BSC, ISC в классе общих данных CDC), атрибута setVal (класс ING в классе общих
     данных CDC) или атрибута setMag (классы АРС, ASG класса общих данных CDC) */
    private AnalogValue maxVal = new AnalogValue();
    /**Определяет шаг между отдельными значениями, которые будет принимать атрибут
     ctIVal (классы INC, BSC, ISC в классе общих данных CDC), атрибут setVal (класс ING в
     классе общих данных CDC) или атрибут setMag (классы АРС, ASG в классе общих данных
     CDC) */
    private AnalogValue stepSize = new AnalogValue();/** CF 1 ... (maxVal - minVal) О*/
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
