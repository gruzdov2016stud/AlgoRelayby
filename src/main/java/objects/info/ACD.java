package objects.info;

import lombok.Getter;
import lombok.Setter;
import objects.data.DataAttribute;
import objects.data.enums.Direction;
/**7.3.6 Класс ACD (сведения об активации направленной защиты
 */
@Getter
@Setter
public class ACD extends ACT {
    /**
     *Общее направление короткого замыкания. Если короткие замыкания отдельных фаз
     * имеют различное направление, этот атрибут должен быть установлен на оба направления
     */
    private DataAttribute<Direction> dirGeneral = new DataAttribute<>(Direction.UNKNOWN);
    /**Направление короткого замыкания для фазы А*/
    private DataAttribute<Direction> dirPhsA = new DataAttribute<>(Direction.UNKNOWN);
    /**Направление короткого замыкания для фазы B*/
    private DataAttribute<Direction> dirPhsB = new DataAttribute<>(Direction.UNKNOWN);
    /**Направление короткого замыкания для фазы C*/
    private DataAttribute<Direction> dirPhsC = new DataAttribute<>(Direction.UNKNOWN);
    /**Направление короткого замыкания для фазы dirNeut*/
    private DataAttribute<Direction> dirNeut = new DataAttribute<>(Direction.UNKNOWN);

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