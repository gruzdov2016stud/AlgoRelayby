package nodes.measurements;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import nodes.measurements.filter.Fourier;
import objects.descriptionInfo.DPL;
import objects.info.INS;
import objects.measured.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**Описание данного логического узла (LN) приведено в МЭК 61850-5. Данный логический узел используется
 для расчета гармоник и интергармоник в трехфазной системе. Возможны экземпляры логических
 узлов для гармоник (в том числе дробных гармоник и гармоник с дробными частотами) или интергармоник
 в зависимости от значения основных уставок, */
@Getter
@Setter
public class MHAI extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Мгновенное значение Тока фазы А */
    private SAV instIa = new SAV();
    /**Мгновенное значение Тока фазы B */
    private SAV instIb = new SAV();
    /**Мгновенное значение Тока фазы C */
    private SAV instIc = new SAV();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Последовательность тока гармоник или интергармоник*/
    private HWYE HA = new HWYE();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    /**Фильтр Фурье фазы А первой и второй гармоники */
    private List<Fourier> fIaList = new LinkedList<>();
    /**Фильтр Фурье фазы B первой и второй гармоники */
    private List<Fourier> fIbList = new LinkedList<>();
    /**Фильтр Фурье фазы C первой и второй гармоники */
    private List<Fourier> fIcList = new LinkedList<>();
    /**Лист из первой и второй гармоники */
    private List<Integer> harmonics = new LinkedList<>();

    /**
     *
     * @param instMagIa Мгновенное значение Ia из узла LSVC
     * @param instMagIb Мгновенное значение Ib из узла LSVC
     * @param instMagIc Мгновенное значение Ic из узла LSVC
     * @param harmonicS Указать требуемые гармоники
     */
    public MHAI(SAV instMagIa, SAV instMagIb, SAV instMagIc, int... harmonicS) {
        this.instIa = instMagIa;
        this.instIb = instMagIb;
        this.instIc = instMagIc;
        this.harmonics.clear();
        for(int i = 0; i < harmonicS.length; i++) {
            int harmoS = harmonicS[i];
            this.harmonics.add(harmoS);
        }
        for(int hS= 0; hS < 10; hS++){
            fIaList.add(new Fourier(hS));
            fIbList.add(new Fourier(hS));
            fIcList.add(new Fourier(hS));
        }
    }

    @Override
    public void process() {
        for(int h: harmonics){
            fIaList.get(h).process(instIa, HA.getPhsAHar().get(h));
            fIbList.get(h).process(instIb, HA.getPhsBHar().get(h));
            fIcList.get(h).process(instIc, HA.getPhsCHar().get(h));
        }
    }
    // ================================================ Не используемые ================================================

    // todo Информация об общих логических узлах
    /**
     *Состояние работоспособности внешнего оборудования (внешние
     * сенсорные устройства)
     */
    private INS EEHealth = new INS();
    /**
     Паспортная табличка внешнего оборудования
     */
    private DPL EEName = new DPL();
    // todo Измеренные значения
    /**
     *Частота
     */
    private MV Hz = new MV();
    /**
     *Последовательность фазного напряжения гармоник или интергармоник
     */
    private HWYE HPhV = new HWYE();
    /**
     *Последовательность линейного напряжения гармоник или интергармоник
     */
//    private HDEL HPPV = new HDEL();
    /**
     *Последовательность активной мощности гармоник или интергармоник
     */
    private HWYE HW = new HWYE();
    /**
     *Последовательность реактивной мощности гармоник или интергармоник
     */
    private HWYE HVAr = new HWYE();
    /**
     *Последовательность фиксируемой мощности гармоник или интергармоник
     */
    private HWYE HVA = new HWYE();
    /**
     *Среднеквадратичное значение тока гармоник или интергармоник
     * (ненормированный полный коэффициент гармоник, Thd)
     */
    private WYE HRmsA = new WYE();
    /**
     *Среднеквадратичное значение напряжения гармоник или интергармоник
     * (ненормированный Thd) при фазном напряжении
     */
    private WYE HRmsPhV = new WYE();
    /**
     *Среднеквадратичное значение напряжения гармоник или интергармоник
     * (ненормированный Thd) при линейном напряжении
     */
    private DEL HRmsPPV = new DEL();
    /**
     Полнофазная активная мощность гармоник или интергармоник
     (неосновной гармоники), итого без знака
     */
    private WYE HTuW = new WYE();
    /**
     Полнофазная активная мощность гармоник или интергармоник
     (неосновной гармоники), итого со знаком
     */
    private WYE HTsW = new WYE();
    /**
     Производное ток х время
     */
    private WYE HATm = new WYE();
    /**
     Коэффициент К
     */
    private WYE HKf = new WYE();

}
