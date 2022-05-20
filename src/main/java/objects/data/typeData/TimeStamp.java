package objects.data.typeData;

import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;

/** МЭК 61850_7_2_5.5.3.7 Тип TimeStamp (временная метка)
 *  Тип TimeStamp (временная метка) основывается на требованиях, описанных в разделе 18.
 *  Этот раздел необходимо прочитать в первую очередь. Представление типа TimeStamp определено
 *  в специфических отображениях сервиса связи (SCSM).
 * */
@Getter
@Setter
public class TimeStamp extends Data {
    /**
     * Атрибут SecondSinceEpoch представляет собой интервал в секундах, отсчитываемых непрерывно с начала отсчета
     * 1970-01-01 00:00:00 UTC
     */
    private DataAttribute<Integer> SecondSinceEpoch = new DataAttribute<>(0);
    /**
     * Атрибут FractionOfSecond является той долей текущей секунды, во время которой было определено значение TimeStamp.
     * Эта доля секунды должна быть рассчитана как (SUM выражения b*2**-(i+1) секунд при i = 0...23).
     */
    private DataAttribute<Integer> FractionOfSecond = new DataAttribute<>(0);
    /**
     *Атрибут TimeQuality обеспечивает информацию об источнике времени передающего IED-устройства.
     */
    private TimeQuality TimeQuality = new TimeQuality();

}
