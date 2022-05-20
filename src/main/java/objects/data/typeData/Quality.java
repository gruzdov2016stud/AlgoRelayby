package objects.data.typeData;

import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.Source;
import objects.data.enums.Validity;

import java.util.ArrayList;
import java.util.List;
/** 6.2 Тип Quality (качество) Описание:
 * Атрибут Quality содержит сведения о качестве полученной с сервера информации.
 *  Качество в 61850 относится к качеству информации, полученной с сервера. К клиенту локальной базы данных может быть
 *  предъявлено требование по использованию дополнительной информации по качеству. Этот вопрос следует решать
 *  на локальном уровне, он выходит за рамки области применения настоящего стандарта. В то же время качество клиента
 *  может влиять на качество, обеспечиваемое сервером во взаимодействии клиент-сервер на более высоком уровне
 *
 * Различные идентификаторы качества не являются автономными.
 * В основном используют следующие идентификаторы качества:
 * - применимость (validity); - (хороший, недействительный, зарезервированный, сомнительный)
 * -детализация качества - detailQual;
 * - источник;
 * -тестирование; - test
 * - блокирован оператором. - operatorBlocked
 * extends Data Наследовано из класса Data (см. МЭК 61850-7-2)
 * DataAttribute*/
@Getter
@Setter
public class Quality extends Data {
    /**
     * Validity должен быть настроен на значение invalid или questionable
     */
    private DataAttribute<Validity> validity = new DataAttribute<>(Validity.GOOD);
    /**
     * detailQual (детализация качества) - Основание для признания значения атрибута недействительным или сомнительным может
     * быть определено более детально с использованием дополнительных идентификаторов качества. При наличии одного из
     * этих идентификаторов атрибут
     * Таблица МЭК61850-7-3 стр.5 показываетотношение идентификаторов детализации качества с недействительным
     * или сомнительным качеством
     */
    private List<Float> detailQual = new ArrayList<>();
    private DataAttribute<Boolean> overflow = new DataAttribute<>(false);
    private DataAttribute<Boolean> outOfRange = new DataAttribute<>(false);
    private DataAttribute<Boolean> badReference = new DataAttribute<>(false);
    private DataAttribute<Boolean> oscillatory = new DataAttribute<>(false);
    private DataAttribute<Boolean> failure = new DataAttribute<>(false);
    private DataAttribute<Boolean> oldData = new DataAttribute<>(false);
    private DataAttribute<Boolean> inconsistent = new DataAttribute<>(false);
    private DataAttribute<Boolean> inaccurate = new DataAttribute<>(false);


    /**
     * Идентификатор Source содержит информацию о происхождении значения. Значение может быть получено
     * из процесса или может быть замещенным значением.
     */
    private DataAttribute<Source> source = new DataAttribute<>(Source.SUBSTITUTED_DEF_PROCESS);
    /**
     * Дополнительный идентификатор test может быть использован для указания тестового значения,
     * которое не предназначено для использования в операционных целях.
     */
    private DataAttribute<Boolean> test = new DataAttribute<>(false);

    /**
     * Идентификатор operatorBlocked (блокирован оператором) устанавливается в случае, если оператор блокирует
     * дальнейшее обновление значения. Значением является информация, полученная перед блокировкой.
     * Если этот идентификатор установлен, должен быть также установлен дополнительный идентификатор oldData
     * идентификатора detailQual.
     */
    private DataAttribute<Boolean> operatorBlocked = new DataAttribute<>(false);

}