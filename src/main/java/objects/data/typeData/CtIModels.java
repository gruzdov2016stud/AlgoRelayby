package objects.data.typeData;


import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.CtIModelsAtr;

/** Тип CtIModels (модели управления)
 * */
@Getter
@Setter
public class CtIModels extends Data {

    private DataAttribute<CtIModelsAtr> ctIModelsAtr = new DataAttribute<>(CtIModelsAtr.DIRECT_WITH_ENHANCED_SECURITY);

}
