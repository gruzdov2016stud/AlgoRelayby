package objects.data.typeData;


import lombok.Getter;
import objects.data.AnalogValue;
import objects.data.Data;
import objects.data.DataAttribute;

/**
 * 6.10 Тип Vector (вектор)
 */
@Getter
public class Vector extends Data {
    /** Атрибут mag модуль комплексного значения w*/
    private DataAttribute<Float> mag = new DataAttribute<>(0f);
    /** Атрибут ang: угол комплексного значения.
     Единицей измерения является Градусы.
     Базовое значение угла определяется в контексте вместе с типом Vector.  */
    private DataAttribute<Float> ang = new DataAttribute<>(0f);
    private DataAttribute<Float> angRad = new DataAttribute<>(0f);
    /** Атрибут ortX проекция ось X*/
    private DataAttribute<Float> ortX = new DataAttribute<>(0f);
    /** Атрибут ortY проекция ось Y*/
    private DataAttribute<Float> ortY = new DataAttribute<>(0f);

    /** magValue - модуль, angValue - в радианах */
    public void setValue(float magValue, float angValue){
        mag.setValue(magValue);
        ang.setValue((float) Math.toDegrees(angValue));
        angRad.setValue(angValue);
        ortX.setValue(magValue * (float) Math.cos(angRad.getValue()));
        ortY.setValue(magValue * (float) Math.sin(angRad.getValue()));
    }
    /** Задаем вектор в декартовых координатах */
    public void setValueO(float x, float y){
        ortX.setValue(x);
        ortY.setValue(y);
        mag.setValue((float) Math.sqrt(x*x + y*y));
        angRad.setValue((float) Math.atan2(y, x)); //угол в радианах
        ang.setValue((float) Math.toDegrees(angRad.getValue()));
    }

}
