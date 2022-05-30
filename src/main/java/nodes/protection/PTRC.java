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

/**LN: Общий сигнал срабатывания защит.
 * Функции, моделируемые логическим узлом PTRC:
 * 1) Режим работы логического узла;
 * 2) Пуск и срабатывание функции.
 * Применяется для реализации объединения сигналов защит для выдачи отключающего воздействия.
 * Экземпляры ЛУ размещаются в логических устройствах РЗА.
 * */
@Getter
@Setter
public class PTRC extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /** Пуск на отключение\блокировки всех пришедших сигналов */
    private List<ACT> OpS = new ArrayList<>();
    /**Пуск на отключение*/
    private ACT Op= new ACT();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Отключение*/
    private ACT Tr = new ACT();
    /** Сумма всех пусков всех подключенных логических узлов*/
    private ACD Str = new ACD();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    private int count = 0;
    private int countOp = 0;
    /**
     * Объединения сигналов защит для выдачи отключающего воздействия ДЗЛ
     * @param op1 - Дифференциальная отсечка
     * @param op2 - Дифференциальная ток
     * @param op3 - Блокировка
     */
    public PTRC(ACT op1, ACT op2, ACT op3) {
        this.OpS.add(op1);
        this.OpS.add(op2);
        this.OpS.add(op3);
        countOp = 3;
    }
    /**
     * Объединения сигналов защит для выдачи отключающего воздействия
     * @param OpI Все пуски на отключение
     */
    public PTRC(ACT...OpI) {
        for(int i = 0; i < OpI.length; i++) {
            this.Op = OpI[i];
            this.OpS.add(Op);
            countOp++;
        }
    }

    @Override
    public void process() {
        for (ACT signal : OpS) {
            if(signal.getGeneral().getValue()) count++;
        }
        if(count == countOp) Tr.getGeneral().setValue(true);
        else Tr.getGeneral().setValue(false);
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
