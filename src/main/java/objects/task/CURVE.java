package objects.task;

import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.SetCharact;


/**7.8.3 Класс CURVE (определение кривой)
 */
@Getter
@Setter
public class CURVE extends Data {

/*
    todo Задание значений аналоговой переменной
*/
    private DataAttribute<SetCharact> setCharact;

    private DataAttribute<Float> setParA = new DataAttribute<>(0f);
    private DataAttribute<Float> setParB  = new DataAttribute<>(0f);
    private DataAttribute<Float> setParC = new DataAttribute<>(0f);
    private DataAttribute<Float> setParD = new DataAttribute<>(0f);
    private DataAttribute<Float> setParE = new DataAttribute<>(0f);
    private DataAttribute<Float> setParF = new DataAttribute<>(0f);

    /*
        todo Конфигурация, описание и расширение
    */
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
