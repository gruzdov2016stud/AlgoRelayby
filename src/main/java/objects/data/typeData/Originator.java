package objects.data.typeData;

import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.OrCat;

/** МЭК 61850_7_3_6.8 Тип Originator (инициатор)
 * Тип Originator должен содержать сведения об инициаторе последнего изменения атрибута данных,
 * представляющего значение контролируемых данных.
 */
@Getter
@Setter
public class Originator extends Data {
    /**
     *  Атрибут orCat специфицирует категорию инициатора, который вызвал изменение значения.
     * */
    private DataAttribute<OrCat> orCat = new DataAttribute<>(OrCat.NOT_SUPPORTED);
    /**
     *  Атрибут orIdent: идентификация инициатора должна содержать адрес отправителя, по чьей инициативе произошло
     *   изменение значения. Значение NULL (нуль) должно быть зарезервировано для указания на то,
     *   что инициатор определенного воздействия неизвестен или его данные не сообщаются.
     * */
    private String orIdent;

}
