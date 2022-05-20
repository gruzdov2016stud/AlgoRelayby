package objects.data.typeData;

import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.СmdQual;


/** МЭК 61850_7_3_6.7 Тип Pulse configuration (конфигурация импульса
 *  Тип Pulse Configuration служит для конфигурирования выходного импульса, генерируемого командой
 * */
@Getter
@Setter
public class PulseConfig extends Data {
    /**
     * cmdQual определяет, является ли управляющий выход импульсным выходом или это выход постоянного значения.
     * Если он настроен на значение pulse, тогда с помощью идентификаторов onDur, offDur и numPls определяется длительность импульса.
     * Если он настроен на значение persistent,деактивация выходного импульса— локальная проблема, решаемая на уровне
     * сервера. Например, когда выключатель, управляемый этим выходом, достигает конечного положения, локальная
     * управляющая логика в устройстве, реализующем сервер, отключает выход.
     */
    private DataAttribute<СmdQual> cmdQual = new DataAttribute<>(СmdQual.PERSISTENT);
    private DataAttribute<Integer> onDur = new DataAttribute<>(0);
    private DataAttribute<Integer> offDur = new DataAttribute<>(0);
    private DataAttribute<Integer> numPls = new DataAttribute<>(0);


}
