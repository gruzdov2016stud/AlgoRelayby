package objects.setStatus;


import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;

/**7.7.3 Класс ING (установка состояния целочисленная
 */
@Getter
@Setter
public class ING extends Data {

/*
    todo Установка состояния
*/
    /**Значение является настройкой параметра состояния */
    private DataAttribute<Integer> setVal = new DataAttribute<>(0);
/*
    todo Конфигурация, описание и расширение
*/
    /**Наряду с атрибутом maxVal определяет диапазон настройки атрибута ctIVal (классы
     INC, BSC, ISC в классе общих данных CDC), атрибута setVal (класс ING в классе общих
     данных CDC) или атрибута setMag (классы АРС, ASG в классе общих данных CDC) */
    private DataAttribute<Integer> minVal = new DataAttribute<>(0);
    /**Наряду с атрибутом minVal определяет диапазон настройки атрибута ctIVal (классы
     INC, BSC, ISC в классе общих данных CDC), атрибута setVal (класс ING в классе общих
     данных CDC) или атрибута setMag (классы АРС, ASG класса общих данных CDC) */
    private DataAttribute<Integer> maxVal = new DataAttribute<>(0);
    /** Определяет шаг между отдельными значениями, которые будет принимать атрибут
     ctIVal (классы INC, BSC, ISC в классе общих данных CDC), атрибут setVal (класс ING в
     классе общих данных CDC) или атрибут setMag (классы АРС, ASG в классе общих данных
     CDC)*/
    private DataAttribute<Integer> stepSize = new DataAttribute<>(0); // CF 1 ... (maxVal - minVal) О
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
