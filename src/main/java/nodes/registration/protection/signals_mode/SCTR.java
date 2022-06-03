package nodes.registration.protection.signals_mode;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.descriptionInfo.LPL;
import objects.info.ACT;
import objects.info.SPS;
import objects.managment.INC;
import objects.measured.MV;
import objects.measured.WYE;
import objects.setStatus.ING;
import objects.setStatus.SPG;
import objects.task.ASG;

/**
 * Класс логического узла SCTR «Контроль цепей тока»
 * Функции, моделируемые классом логического узла SCTR:
 * 1) Режим работы логического узла;
 * 2) Сигналы о статусе параметров;
 * 3) Мониторинг параметров;
 * 4) Уставки.
 * Применяется для реализации функции КЦТ. Применяется один экземпляр ЛУ на функцию.
 * Экземпляры ЛУ размещаются в логических устройствах управления РЗА. */
@Getter
@Setter
public class SCTR extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Дифференциальный ток */
    private WYE DifACIc = new WYE();
    /**Выдержка времени для состояния блокировки */
    private ING BlkDlTmms = new ING();
    /** Уставка - Уровень контролируемой величины, при которой активируется назначенное действия соответствующей функции из main */
    private ASG StrVal = new ASG();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Блокировка зависимых функций защиты */
    private SPS BlkOp = new SPS();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    /**
     * @param DlTmms -  Выдержка времени для состояния блокировки
     */
    public SCTR(double strVal, int DlTmms) {
        this.StrVal.getSetMag().setValue((float) strVal);
        this.BlkDlTmms.getSetVal().setValue(DlTmms);
        this.BlkOp.getStValPhA().setValue(false);
        this.BlkOp.getStValPhC().setValue(false);
        this.BlkOp.getStValPhB().setValue(false);
        this.BlkOp.getStValPhGeneral().setValue(false);
    }
    @Override
    public void process() {

        /**Проверка уставки*/
        boolean phsA = DifACIc.getPhsA().getCVal().getMag().getValue() > StrVal.getSetMag().getValue();
        boolean phsB = DifACIc.getPhsB().getCVal().getMag().getValue() > StrVal.getSetMag().getValue();
        boolean phsC = DifACIc.getPhsC().getCVal().getMag().getValue() > StrVal.getSetMag().getValue();
        boolean general = phsA || phsB || phsC;

        /**Инициализация Пуска на блакировку защиты PDIF при превышении уставки*/
        BlkOp.getStValPhA().setValue(phsA);
        BlkOp.getStValPhC().setValue(phsB);
        BlkOp.getStValPhB().setValue(phsC);
        BlkOp.getStValPhGeneral().setValue(general);
    }
    // ================================================ Не используемые ================================================
// todo Описание
    /**Информация о логическом узле */
    private LPL NamPlt = new LPL();
// todo Информация о состоянии
    /**Срабатывание детектора насыщения*/
    private ACT SatDet = new ACT();
    /**Выдержка времени для состояния сигнализации */
    private ING AlmDlTmms = new ING();
    /**Общая сигнализация трансформатора */
    private SPS Alm = new SPS();
    /**Уставка небаланса для сигнализации */
    private ASG UnbAlmLev = new ASG();
    /**Уставка небаланса для блокировки */
    private ASG UnbBlkLev = new ASG();
    /**Индикация поведения */
    //ENS Beh
    /**Индикация исправности */
    //ENS Health
    /**Измеряемое контрольное значение небаланса */
    private MV UnbVal = new MV();
// todo Элементы управления
    /**Режим работы */
    //ENC Mod
    /**Сбрасываемый счетчик операций */
    private INC OpCntRs = new INC();
// todo Параметры
    /**Включение блокировки */
    private SPG EnaBlk = new SPG();


}
