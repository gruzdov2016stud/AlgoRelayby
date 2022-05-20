package objects.data;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Data {
    /**
     * Атрибут DataName должен однозначно определять данные Data в пределах логического узла LN.
     */
    private String dataName;
    /**
     * Атрибут DataRef — объектная ссылка данных, должен быть уникальным именем пути данных DATA.
     */
    private String dataRef;
    /**
     * Атрибут Presence типа BOOLEAN должен описывать, является ли DataAttribute обязательным
     * (Presence = TRUE) или опциональнным (Presence = FALSE).
     */
    private DataAttribute<Boolean> presence = new DataAttribute<>(false);
}
