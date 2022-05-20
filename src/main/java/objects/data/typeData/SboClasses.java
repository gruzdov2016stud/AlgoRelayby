package objects.data.typeData;

import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.SlUnitSboClasses;

/** МЭК 61850_7_3_6.13 Тип SboClasses
 * */
@Getter
@Setter
public class SboClasses extends Data {
    private DataAttribute<SlUnitSboClasses> slUnitSboClasses = new DataAttribute<>(SlUnitSboClasses.operateOnce);

}
