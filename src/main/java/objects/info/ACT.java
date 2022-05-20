package objects.info;

import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.typeData.Quality;
import objects.data.typeData.TimeStamp;

/**7.3.5 Класс ACT (сведения об активации защиты)
 */
@Getter @Setter
public class ACT extends Data {
    /*
        todo Состояние
    */
    /**Логическое or (или) состояния фаз, например, пуск защиты или отключение. Атрибут
     устанавливается также в том случае, если не все фазы находятся в состоянии короткого
     замыкания*/
    private DataAttribute<Boolean> general = new DataAttribute<>(false);
    /**Качество атрибута(ов), представляющего значение данных.*/
    private Quality q = new Quality();
    /**Timestamp последнего изменения является одним из атрибутов, представляющим
     значение данных или в атрибуте q.*/
    private TimeStamp t = new TimeStamp();

    /**Событие срабатывания или пуска защиты фазы phsA*/
    private DataAttribute<Boolean> phsA = new DataAttribute<>(false);
    /**Событие срабатывания или пуска защиты фазы phsB*/
    private DataAttribute<Boolean> phsB = new DataAttribute<>(false);
    /**Событие срабатывания или пуска защиты фазы phsC*/
    private DataAttribute<Boolean> phsC = new DataAttribute<>(false);
    /**Событие срабатывания или пуска защиты фазы neut*/
    private DataAttribute<Boolean> neut = new DataAttribute<>(false);
    /*
        todo Конфигурация, описание и расширение
    */
    /**Время выполнения операции. Применяется для фазы точки переключения*/
    private TimeStamp operTm = new TimeStamp();
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
