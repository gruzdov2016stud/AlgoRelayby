package objects.data.typeData;

import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.Direction;
import objects.data.enums.TimeAccuracy;

/** Атрибут TimeQuality
 *  TimeQuality обеспечивает информацию об источнике времени передающего IED-устройства.
 **/
@Getter
@Setter
public class TimeQuality extends Data {
    /**
     *LeapSecondsKnown Значение TRUE (логическая единица) атрибута LeapSecondsKnown означает, что в значении
     */
    private DataAttribute<Boolean> LeapSecondsKnown = new DataAttribute<>(false);
    /**
     *Атрибут clockFailure означает, что источник времени передающего устройства ненадежный. Значение TimeStamp должно быть проигнорировано.
     */
    private DataAttribute<Boolean> ClockFailure = new DataAttribute<>(false);
    /**
     *Атрибут ClockNotSynchronized означает, что источник времени передающего устройства не синхронизирован с внешним временем UTC.
     */
    private DataAttribute<Boolean> ClockNotSynchronized = new DataAttribute<>(false);
    /**
     *Атрибут TimeAccuracy представляет класс точности времени источника времени передающего устройства
     */
    private DataAttribute<TimeAccuracy> timeAccuracy = new DataAttribute<>(TimeAccuracy.UNKNOWN);

}
