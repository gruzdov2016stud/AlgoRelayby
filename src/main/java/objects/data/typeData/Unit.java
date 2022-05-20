package objects.data.typeData;


import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.enums.Multiplier;
import objects.data.enums.SIUnit;

/** МЭК 61850_7_3_6.9 Тип Unit (единица измерения)
 * Тип Range configuration используется для конфигурирования предельных значений
 */
@Getter
@Setter
public class Unit extends Data {
    /**
     * Атрибут SIUnit: определяет физические величины в системных единицах СИ в соответствии с приложением А.
     */
    private SIUnit siUnit;
    /**
     * Атрибут multiplier: определяет значение множителя в соответствии с приложением А. Значение по умолчанию равно 0 (т. е. множитель = 1).
     */
    private Multiplier multiplier;

}
