package nodes.registration;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.enums.Direction;
import objects.info.ACD;
import objects.info.ACT;
import objects.info.SPS;
import objects.managment.INC;
import objects.measured.SEQ;
import objects.measured.WYE;
import objects.setStatus.ING;
import objects.setStatus.SPG;
import objects.task.ASG;

@Getter
@Setter
/**Блокировка при качаниях, если true, то значит колебания обнаружены и нужно блокировать защиту*/
public class RPSB extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /** Объект из класса MSQI ПП, ОП, НП тока */
    private SEQ SeqA;
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /** Срабатывание (отключение по асинхронному режиму) */
    private ACT Op = new ACT();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void process() {
        if (Math.abs(SeqA.getС2().getCVal().getMag().getValue()) < 0.4f){
            Op.getGeneral().setValue(true); // Колебания обнаружены, необходимо заблокировать защиту
        } else{
            Op.getGeneral().setValue(false); // Колебания не обнаружены
        }
    }


    // ================================================ Не используемые ================================================
    /** Счётчик числа переключений со сбросом */
    private INC OpCntRs = new INC();
    /** Блокировка коррелированной зоны PDIS */
    private SPS BlkZn = new SPS();
    /** Ноль разрешён */
    private SPG ZeroEna = new SPG();
    /** Контроль тока обратной последовательности разрешён */
    private SPG NgEna = new SPG();
    /** Контроль максимального тока разрешён */
    private SPG MaxEna = new SPG();
    /** Изменение колебаний мощности - Delta*/
    private ASG SwgVal = new ASG();
    /** Изменение колебаний мощности - Delta R */
    private ASG SwgRis = new ASG();
    /** Изменение колебаний мощности - Delta X */
    private ASG SwgReact = new ASG();
    /** Продолжительность колебаний мощности*/
    private ING SwgTmms = new ING();
    /** Время разблокировки */
    private ING UnBlkTmms = new ING();
    /** Максимальное ичсло фаз проскальзывания до отключения (Op, отключение по асинхронному режиму) */
    private ING MaxNumSlp = new ING();
    /** Время выполнения оценки (временной интервал, отключение по асинхронному режиму) */
    private ING EvTmms = new ING();

}

