package objects.info;

import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.typeData.Quality;
import objects.data.typeData.TimeStamp;

/**
 * 7.3.2 Класс SPS (недублированное состояние)
 */
@Getter
@Setter
public class SPS extends Data {
    /*
        todo состояние
    */
    /**Значение состояния данных */
    private DataAttribute<Boolean> stValPhA = new DataAttribute<>(false);
    private DataAttribute<Boolean> stValPhB = new DataAttribute<>(false);
    private DataAttribute<Boolean> stValPhC = new DataAttribute<>(false);
    private DataAttribute<Boolean> stValPhGeneral = new DataAttribute<>(false);
    /**Качество атрибута(ов), представляющего значение данных.*/
    private Quality q = new Quality();
    /**Timestamp последнего изменения является одним из атрибутов, представляющим
     значение данных или в атрибуте q.*/
    private TimeStamp t = new TimeStamp();
    /*
        todo замещение
    */
    /**Используется для разрешения замещения.*/
    private DataAttribute<Boolean> subEna = new DataAttribute<>(false);
    /**Значение используется для замещения атрибута, представляющего значение экземпляра
     данных. В различных классах общих данных CDC атрибут subVal применяется
     для замещения следующих атрибутов данных */
    private DataAttribute<Boolean> subVal = new DataAttribute<>(false);
    /**Значение используется для замещения атрибута данных q */
    private Quality subQ = new Quality();
    /** Показывает адрес устройства, выполнившего замещение. Значение NULL (нуль) используется,
     если атрибут subEna имеет значение FALSE или если устройство неизвестно*/
    private String subID;
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
