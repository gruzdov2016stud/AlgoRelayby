package objects.data;

import lombok.Getter;
import lombok.Setter;


/**  7.3.6.4 Конфигурация типа Analogue value (значение аналогового сигнала)
 *
 */
@Getter @Setter
public class AnalogValue {
    /**
     * значение с плавающей точкой, атрибут f
     */
    private DataAttribute<Float> f = new DataAttribute<>(0f);

}
