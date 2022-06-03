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
    private DataAttribute<Float> xVal = new DataAttribute<>((float) 0);
    /**
     * Атрибут yVal: значение координаты у точки кривой.
     */
    private DataAttribute<Float> yVal = new DataAttribute<>((float) 0);

    public Point(){}

    public Point(double x, double y) {
        xVal.setValue((float) x);
        yVal.setValue((float) y);
    }

}
