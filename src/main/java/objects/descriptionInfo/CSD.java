package objects.descriptionInfo;

import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.typeData.Point;
import objects.data.typeData.Unit;

import java.util.ArrayList;
import java.util.List;

/** 7.9.4 Класс CSD (описание формы кривой)
 */
@Getter
@Setter
public class CSD extends Data {
/*
    todo Конфигурация, описание и расширение
*/
    /**Единица оси х кривой*/
    private Unit xUnit = new Unit();
    /**Описание значения оси x кривой*/
    private String xD;
    /**Единица оси у кривой*/
    private Unit yUnit = new Unit();
    /**Описание значения оси у кривой*/
    private String yD;
    /**Число точек, используемых для определения кривой*/
    private DataAttribute<Integer> numPts = new DataAttribute<>(0);
    /**Массив с точками, специфицирующими форму кривой*/
    private List<Point> crvPts = new ArrayList<>();
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
