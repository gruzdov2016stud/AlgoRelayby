package objects.measured;


import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.AngRefDEL;


/**7.4.6 Класс DEL (А — «треугольник»)
 * В классе приведено определение общих данных Delta (Del — «треугольник»).
 * Этот класс представляет собой набор одновременно измеренных значений междуфазных напряжений в трехфазной сети.
 */
@Getter
@Setter
public class DEL extends Data {
/*
    todo Состояние
*/
    /**Значение межфазных измерений (между А и В). В классе DEL значения для атрибутов
     phsAB, phsBC и phsCA должны быть получены или определены одновременно. Предполагается,
     что любые отклонения между временем получения, определенным для
     атрибутов phsAB, phsBC, phsCA, должны быть пренебрежимо малыми. Допустимые
     отклонения от одновременности определяются полем качества времени */
    private CMV phsAB = new CMV();
    /**Значение межфазных измерений (фазы В/С), подробнее см. атрибут phsAB */
    private CMV phsBC = new CMV();
    /**Значение межфазных измерений (фазы С/А), подробнее см. атрибут phsAB */
    private CMV phsCA = new CMV();

/*
    todo Конфигурация, описание и расширение
*/
    private DataAttribute<AngRefDEL> angRefDEL = new DataAttribute<>(AngRefDEL.Aa);
        /**
     * Текстовое описание данных. В случае класса LPL в классе общих данных описание
     * относится к логическому узлу
     */
    private String d;
    private String dU;
    private String privatecdcNs;
    private String cdcName;
    private String dataNs;
}
