package nodes.protection;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.info.ACD;
import objects.info.ACT;
import objects.managment.INC;
import objects.setStatus.ING;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
/**LN: Общий сигнал срабатывания защит.
 * Функции, моделируемые логическим узлом PTRC:
 * 1) Режим работы логического узла;
 * 2) Пуск и срабатывание функции.
 * Применяется для реализации объединения сигналов защит для выдачи отключающего воздействия.
 * Экземпляры ЛУ размещаются в логических устройствах РЗА.
 * */
public class PTRC extends LN {
    /**
     *todo Входные параметры
     * */
    /**
     *Пуск на отключение
     * Срабатывание (сочетание сигналов срабатывания, получаемых по подписке «Ор» — от функций защиты).
     *
     * (тип атрибута ACT класса общих данных) — означает решение функции защиты
     * (логического узла) об отключении. Команда на отключение выдается в узле PTRC
     */
    private List<ACT> Op = new ArrayList<>();
    /**
     *todo Выходные параметры
     * */
    /**
     Отключение
     */
    private ACT Tr = new ACT();
    /**
     * Сумма всех пусков всех подключенных логических узлов
     */
    private ACD Str = new ACD();

    @Override
    public void process() {
        boolean trip = false;
        for (ACT signal : Op) {
            trip = trip | signal.getGeneral().getValue();
        }
        Str.getGeneral().setValue(trip);
    }

    // ================================================ Не используемые ================================================

    // todo Информация об общих логических узлах
    /**
     *Счетчик числа переключений со сбросом
     */
    private INC OpCntRs = new INC();
    // todo Информация о статусе
    // todo Параметры настройки
    /**Режим отключения*/
    private ING TrMod = new ING();
    /**Длительность импульса отключения*/
    private ING TrPIsTmms = new ING();
}