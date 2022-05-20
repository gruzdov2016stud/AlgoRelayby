package objects.measured;


import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.AngRefCMV;
import objects.data.enums.Range;
import objects.data.typeData.*;

/**7.4.3 Класс CMV (комплексные измеряемые значения)
 */
@Getter
@Setter
public class CMV extends Data {

/*
    todo  Измеряемые атрибуты
*/
    /**Качество атрибута(ов), представляющего значение данных.*/
    private Quality q = new Quality();
    /**Timestamp последнего изменения является одним из атрибутов, представляющим
     значение данных или в атрибуте q.*/
    private TimeStamp t = new TimeStamp();
    /**Мгновенное значение типа Vector*/
    private Vector instCVal = new Vector();
    /**Комплексное значение зоны нечувствительности. Базируется на вычислении зоны
     нечувствительности на основе атрибута instCVal. Вычисление зоны нечувствительности
     выполняется независимо на основе как атрибута nstCVal.mag, так и атрибута
     instCVal.ang. Подробнее о вычислении зоны нечувствительности см. атрибут mag*/
    private Vector cVal = new Vector();
    /**Диапазон текущего значения атрибутов instMag или instCVal.mag. Он может быть
     использован для выдачи сообщения о событии в случае изменения текущего значения
     и перехода в другой диапазон. Диапазон должен быть использован в контексте
     с атрибутами конфигурации, например hhLim, hLim, ILim, IILim, min и max, как показано
     ниже..*/
    private DataAttribute<Range> rangeCMV= new DataAttribute<>(Range.NORMAL);
/*
    todo Замещение
*/
    /**Используется для разрешения замещения.*/
    private DataAttribute<Boolean> subEna = new DataAttribute<>(false);
    /**Значение используется для замещения атрибута данных instCVal*/
    private Vector subCVal = new Vector();
    /**Значение используется для замещения атрибута данных q */
    private Quality subQ = new Quality();
    /** Показывает адрес устройства, выполнившего замещение. Значение NULL (нуль) используется,
     если атрибут subEna имеет значение FALSE или если устройство неизвестно*/
    private String subID;
/*
    todo Конфигурация, описание и расширение
*/
    private Unit units = new Unit();
    /** 0...100 000 */
    private DataAttribute<Integer> db = new DataAttribute<>(0);
    /** 0...100 000 */
    private DataAttribute<Integer> zeroDb = new DataAttribute<>(0);
    private RangeConfig rangeC = new RangeConfig();
    private ScaledValueConfig magSVC = new ScaledValueConfig();
    private ScaledValueConfig angSVC = new ScaledValueConfig();

    private DataAttribute<AngRefCMV> angRefCMV= new DataAttribute<>(AngRefCMV.A);

    private DataAttribute<Integer> smpRate = new DataAttribute<>(0);

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
