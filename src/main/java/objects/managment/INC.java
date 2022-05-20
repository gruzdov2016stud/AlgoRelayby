package objects.managment;


import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.typeData.*;

/**7.5.4 Класс INC (целочисленное управление и состояние)
 */
@Getter
@Setter
public class INC extends Data {

/*
    todo Управление и состояние
*/
    /**
     * Определяет управляющее воздействие.
     */
    private DataAttribute<Integer> ctIVal = new DataAttribute<>(0);
    /**Если выполняется сервис TimeActivatedOperate, данный атрибут специфицирует абсолютное
     время выполнения команды */
    private TimeStamp operTm = new TimeStamp();
    /**Содержит информацию об инициаторе последнего изменения контролируемого значения
     данных */
    private Originator origin = new Originator();
    /**Если изменение состояния вызвано управляющим воздействием, содержание должно
     показывать номер последовательности управления сервиса управления. Все сервисные
     примитивы, принадлежащие к одной последовательности управления, определяются
     одним и тем же номером последовательности управления. Использование
     атрибута ctINum остается на усмотрение клиента. Единственное действие, выполняемое
     сервером с атрибутом ctINum, — это включение его в ответы модели управления
     и в отчеты об изменении состояния, вызванного командой */
    private DataAttribute<Integer> ctINum = new DataAttribute<>(0);
    /**Значение состояния данных */
    private DataAttribute<Integer> stVal = new DataAttribute<>(0);
    /**Качество атрибута(ов), представляющего значение данных.*/
    private Quality q = new Quality();
    /**Timestamp последнего изменения является одним из атрибутов, представляющим
     значение данных или в атрибуте q.*/
    private TimeStamp t = new TimeStamp();
    /**Состояние контролируемых данных selected (выбранное) */
    private DataAttribute<Boolean> stSeld = new DataAttribute<>(false);
    /*
            todo Замещение
    */
    /**Используется для разрешения замещения.*/
    private DataAttribute<Boolean> subEna = new DataAttribute<>(false);
    /**Значение используется для замещения атрибута, представляющего значение экземпляра
     данных. В различных классах общих данных CDC атрибут subVal применяется
     для замещения следующих атрибутов данных */
    private DataAttribute<Integer> subVal = new DataAttribute<>(0);
    /**Значение используется для замещения атрибута данных q */
    private Quality subQ = new Quality();
    /** Показывает адрес устройства, выполнившего замещение. Значение NULL (нуль) используется,
     если атрибут subEna имеет значение FALSE или если устройство неизвестно*/
    private String subID;
/*
    todo Конфигурация, описание и расширение
*/
    /**Специфицирует модель управления, описанную в МЭК 61850-7-2, которая соответствует
     поведению данных */
    private CtIModels ctIModel = new CtIModels();
    /**В соответствии с моделью управления, описанной в МЭК 61850-7-2, соответствующей
     поведению данных, специфицирует временное прерывание (timeout). Значение должно
     быть указано в мс */
    private DataAttribute<Integer> sboTimeout = new DataAttribute<>(0);
    /**В соответствии с моделью управления, описанной в МЭК 61850-7-2, соответствующей
     поведению данных, специфицирует SBO-класс. Определены следующие значения:
     operate-once (однократно) После запроса сервиса operate объект управления возвращается
     в невыбранное состояние
     operate-many (многократно) После запроса сервиса operate объект управления остается
     в состоянии готовности до истечения срока sboTimeout
     */
    private SboClasses sboClass = new SboClasses();
    /**Наряду с атрибутом maxVal определяет диапазон настройки атрибута ctIVal (классы
     INC, BSC, ISC в классе общих данных CDC), атрибута setVal (класс ING в классе общих
     данных CDC) или атрибута setMag (классы АРС, ASG в классе общих данных CDC) */
    private DataAttribute<Integer> minVal = new DataAttribute<>(0);
    /**Наряду с атрибутом minVal определяет диапазон настройки атрибута ctIVal (классы
     INC, BSC, ISC в классе общих данных CDC), атрибута setVal (класс ING в классе общих
     данных CDC) или атрибута setMag (классы АРС, ASG класса общих данных CDC) */
    private DataAttribute<Integer> maxVal = new DataAttribute<>(0);
    /**Определяет шаг между отдельными значениями, которые будет принимать атрибут
     ctIVal (классы INC, BSC, ISC в классе общих данных CDC), атрибут setVal (класс ING в
     классе общих данных CDC) или атрибут setMag (классы АРС, ASG в классе общих данных
     CDC)*/
    private DataAttribute<Integer> stepSize = new DataAttribute<>(0);
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
