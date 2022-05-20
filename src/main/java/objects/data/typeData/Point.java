package objects.data.typeData;


import lombok.Getter;
import lombok.Setter;
import objects.data.DataAttribute;
@Getter @Setter
/**
 * 6.11 Тип Point (точка)
 */
public class Point {
    /**
     * Атрибут xVal: значение координаты х точки кривой.
     */
   private DataAttribute<Float> xVal;
    /**
     * Атрибут yVal: значение координаты у точки кривой.
     */
    private DataAttribute<Float> yVal;

}
