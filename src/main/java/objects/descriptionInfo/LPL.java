package objects.descriptionInfo;


import lombok.Getter;
import lombok.Setter;
import objects.data.Data;

/**7.9.3 Класс LPL (паспортная табличка логического узла)
 extends Data Наследовано из класса Data (см. МЭК 61850-7-2)
 DataAttribute
 */
@Getter
@Setter
public class LPL extends Data {

/*
    todo Конфигурация, описание и расширение
*/
    private String vendor;
    private String swRev;
    private String d;
    private String dU;
    private String configRev;
    private String IdNs;
    private String InNs;
    private String cdcNs;
    private String cdcName;
    private String dataNs;
/*
    todo Методы
*/
}
