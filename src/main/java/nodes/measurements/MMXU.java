package nodes.measurements;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import nodes.measurements.filter.Filter;
import nodes.measurements.filter.Fourier;
import nodes.measurements.filter.RMS;
import objects.info.INS;
import objects.measured.DEL;
import objects.measured.MV;
import objects.measured.SAV;
import objects.measured.WYE;

/**
 * LN: Измерения - Применяется в основном для оперативных приложений. Для получения значений от СТ-трансформаторов и
 * VT-трансформаторов и вычисления измеряемых величин, таких как среднеквадратическое
 * значение тока и напряжения или потокораспределение мощности, из полученных выборок напряжения и тока.
 * Эти значения обычно используются в эксплуатации, например для контроля и управления потокораслределением
 * мощности, экранных отображений, оценки состояния и т. д. Должна быть обеспечена необходимая точность этих функций.
 * Процедуры измерения в защитных устройствах являются частью специального алгоритма защиты, представленного логическими
 * узлами Pxyz. Алгоритмы защиты, в том числе любая функция не являются объектом стандартизации серии стандартов
 *
 * МЭК 61850. Следовательно. LN типа Mxyz не будет использован как вход для Pxyz. Данные, связанные с неисправностью,
 * такие как пиковое значение короткого замыкания и т. д. всегда предоставляются LN типа Pxyz. а не LN типа Mxyz
 *
 *
 * Данный логический узел используется для расчета тока, напряжения, мощности в трехфазной системе.
 *
 */
@Getter @Setter
public class MMXU extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Мгновенные Токи */
    private SAV instMagIa = new SAV();
    private SAV instMagIb = new SAV();
    private SAV instMagIc = new SAV();
    /**Мгновенные Напряжения*/
    private SAV instMagUa = new SAV();
    private SAV instMagUb = new SAV();
    private SAV instMagUc = new SAV();
    /**Фазные токи (IL1, IL2, IL3)*/
    private WYE A = new WYE();
    /**Фазное напряжение (VL1ER, ...)*/
    private WYE PhV = new WYE();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**
     *Активная мощность фазы (Р)
     */
    private WYE W = new WYE();
    /**
     *Суммарная активная мощность (суммарная Р)
     */
    private MV TotW = new MV();
    /**
     *Суммарная реактивная мощность (суммарная Q)
     */
    private MV TotVAr = new MV();
    /**
     *Суммарная фиксируемая мощность (суммарная S)
     */
    private MV TotVA = new MV();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    /**
     *Реактивная мощность фазы (Q)
     */
    private WYE VAr = new WYE();
    /**
     *Фиксируемая мощность фазы (S)
     */
    private WYE VA = new WYE();
    /*    *//**Фильтр RMS*//*
    Filter rmsFilterIa = new RMS();
    Filter rmsFilterIb = new RMS();
    Filter rmsFilterIc = new RMS();*/
    /**Фильтр Fourier*/
    private Filter fourierIa = new Fourier();
    private Filter fourierIb = new Fourier();
    private Filter fourierIc = new Fourier();
    private Filter fourierUa = new Fourier();
    private Filter fourierUb = new Fourier();
    private Filter fourierUc = new Fourier();

    @Override
    public void process() {
        /*-----------------------------------------------ЛР№1--------------------------------------------------------*/
/*      rmsFilterIa.process(instIa, A.getPhsA().getCVal());
        rmsFilterIb.process(instIb, A.getPhsB().getCVal());
        rmsFilterIc.process(instIc, A.getPhsC().getCVal());*/
        /*-----------------------------------------------ЛР№2--------------------------------------------------------*/
        fourierIa.process(instMagIa, A.getPhsA().getCVal());
        fourierIb.process(instMagIb, A.getPhsB().getCVal());
        fourierIc.process(instMagIc, A.getPhsC().getCVal());
        fourierUa.process(instMagUa, PhV.getPhsA().getCVal());
        fourierUb.process(instMagUb, PhV.getPhsB().getCVal());
        fourierUc.process(instMagUc, PhV.getPhsC().getCVal());
        /*Расчет углов по фазам*/
        float angA = (float) Math.toRadians(
                PhV.getPhsA().getCVal().getAng().getValue() - A.getPhsA().getCVal().getAng().getValue());
        float angB = (float) Math.toRadians(
                PhV.getPhsB().getCVal().getAng().getValue() - A.getPhsB().getCVal().getAng().getValue());
        float angC = (float) Math.toRadians(
                PhV.getPhsC().getCVal().getAng().getValue() - A.getPhsC().getCVal().getAng().getValue());
        /*Расчет cos и sin*/
        float cosPhiA = (float) Math.cos(angA);
        float sinPhiA = (float) Math.sin(angA);
        float cosPhiB = (float) Math.cos(angB);
        float sinPhiB = (float) Math.sin(angB);
        float cosPhiC = (float) Math.cos(angC);
        float sinPhiC = (float) Math.sin(angC);
        float cosPhi  = (cosPhiA + cosPhiB + cosPhiC) / 3;
        float sinPhi  = (sinPhiA + sinPhiB + sinPhiC) / 3;
        /*Расчет полной мощности*/
        float SA = PhV.getPhsA().getCVal().getMag().getValue() * A.getPhsA().getCVal().getMag().getValue();
        float SB = PhV.getPhsB().getCVal().getMag().getValue() * A.getPhsB().getCVal().getMag().getValue();
        float SC = PhV.getPhsC().getCVal().getMag().getValue() * A.getPhsC().getCVal().getMag().getValue();
        float S  = SA + SB + SC;
        VA.getPhsA().getCVal().getMag().setValue(SA);
        VA.getPhsB().getCVal().getMag().setValue(SB);
        VA.getPhsC().getCVal().getMag().setValue(SC);
        TotVA.getMag().getF().setValue(S);
        /*Расчет полной активной мощности*/
        W.getPhsA().getCVal().getMag().setValue(SA * cosPhiA);
        W.getPhsB().getCVal().getMag().setValue(SB * cosPhiB);
        W.getPhsC().getCVal().getMag().setValue(SC * cosPhiC);
        TotW.getMag().getF().setValue(S * cosPhi);
        /*Расчет полной реактивной мощность*/
        VAr.getPhsA().getCVal().getMag().setValue(SA * sinPhiA);
        VAr.getPhsB().getCVal().getMag().setValue(SB * sinPhiB);
        VAr.getPhsC().getCVal().getMag().setValue(SC * sinPhiC);
        TotVAr.getMag().getF().setValue(S * sinPhi);
    }
    // ================================================ Не используемые ================================================
    // todo Информация об общих логических узлах
    /**
     *Состояние работоспособности внешнего оборудования (внешние
     * сенсорные устройства)
     */
    private INS EEHealth = new INS();
    // todo Измеренные значения
    /**
     *Средний коэффициент мощности (суммарный коэффициент мощности
     * PF)
     */
    private MV TotPF = new MV();
    /**
     *Частота
     */
    private MV Hz = new MV();
    /**
     *Линейное напряжение (VL1VL2, ...)
     */
    private DEL PPV = new DEL();

    /**
     *Коэффициент мощности фазы
     */
    private WYE PF = new WYE();
    /**
     *Полное сопротивление фазы
     */
    private WYE Z = new WYE();

}
