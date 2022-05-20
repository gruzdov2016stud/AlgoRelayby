package objects.descriptionInfo;


import lombok.Getter;
import lombok.Setter;
import objects.data.Data;

/**7.9.2 Класс DPL (паспортная табличка устройства
 */
@Getter
@Setter
public class DPL extends Data {

/*
    todo Конфигурация, описание и расширение
*/
    /**Имя поставщика*/
    private String vendor;
    private String hwRev;
    private String swRev;
    private String serNum;
    private String model;
    private String location;
    private String cdcNs;
    private String cdcName;
    private String dataNs;
}
