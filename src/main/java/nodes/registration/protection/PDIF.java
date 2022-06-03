package nodes.registration.protection;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.typeData.Point;
import objects.data.typeData.Vector;
import objects.descriptionInfo.CSD;
import objects.info.ACD;
import objects.info.ACT;
import objects.info.SPS;
import objects.managment.INC;
import objects.measured.WYE;
import objects.setStatus.ING;
import objects.task.ASG;
import objects.task.CURVE;

/**Данный логический узел (LN) используется для всех видов дифференциально-токовой защиты.
 * Дифференциальная защита
 *
 * Реле дифференциальной защиты представляет собой защитное реле, которое срабатывает по разнице
 * процентного соотношения или фазового угла либо другой количественной разнице двух токов
 * или некоторых других электрических величин*/
@Getter
@Setter
public class PDIF extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Дифференциальный ток */
    private WYE DifACIc = new WYE();
    /** Временный вектор для хранения промежуточного значения для тормозного тока*/
    private Vector RstCurrent = new Vector();
    /** Направляет Динамические характеристики графика*/
    private CSD TmASt = new CSD();
    /**Блокировка зависимых функций защиты по гармонике */
    private SPS BlkOpPHAR = new SPS();
    /**Блокировка при неисправности токовых цепей */
    private SPS BlkOpSCTR = new SPS();
    /** Минимальное время срабатывания Элемент данных Minimum Operating Time (минимальное время действия в мс) логического узла используется для координирования работы с более старыми электромеханическими реле*/
    private ING MinOpTmms = new ING();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /** Пуск на отключение (тип атрибута ACT класса общих данных) — означает решение функции защиты (логического узла) об отключении. Команда на отключение выдается в узле PTRC*/
    private ACT Op = new ACT();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    private WYE StrRst = new WYE();
    /**Срабатывание (атрибут ACD класса общих данных) означает, что обнаружено нарушение или недопустимое состояние. Элемент Str может включать в себя информацию о фазе и направлении*/
    private ACD Str = new ACD();
    /** Счетчик времени после пуска реле защиты */
    private double breakerTimeA = 0, breakerTimeB = 0, breakerTimeC = 0;
    /** Для построения тормозной характеристики*/
    private float dif0 = 0;
    /** угол наклона характеристики в зоне тормажения */
    private float kT1;
    /** Тормозная точка */
    private float rst0 = 0;
    private float rst1 = 0;
    private float x0 = 0, y0 = 0;
    /**
     * @param Itorm_Idiff x и у - координаты для задания формы кривой по Х и У координаты для для задания формы кривой по
     * @param DlTmms - Минимальное время срабатывания
     */
    public PDIF(int DlTmms, double kt1, double... Itorm_Idiff) {
        this.kT1 = (float) kt1;
        MinOpTmms.getSetVal().setValue(DlTmms);
        for(int i = 0; i < Itorm_Idiff.length; i += 2) {
            this.x0 = (float) Itorm_Idiff[i];
            this.y0 = (float) Itorm_Idiff[i+1];
            TmASt.getCrvPts().add(new Point(x0,y0));
        }
    }
    /*расчет значений для характеристики*/
    public void init(){
        dif0 = TmASt.getCrvPts().get(1).getYVal().getValue();
        rst0 = TmASt.getCrvPts().get(1).getXVal().getValue();
        rst1 = TmASt.getCrvPts().get(2).getXVal().getValue();
//        kT1 = (TmASt.getCrvPts().get(2).getYVal().getValue() - TmASt.getCrvPts().get(1).getYVal().getValue()) /
//                (TmASt.getCrvPts().get(2).getXVal().getValue() - TmASt.getCrvPts().get(1).getXVal().getValue());
    }
    @Override
    public void process() {
        if (RstCurrent.getMag().getValue() < rst0) {
            StrRst.getPhsA().getCVal().setValue(RstCurrent.getMag().getValue(), 0);
            StrRst.getPhsB().getCVal().setValue(RstCurrent.getMag().getValue(), 0);
            StrRst.getPhsC().getCVal().setValue(RstCurrent.getMag().getValue(), 0);
        } else if(RstCurrent.getMag().getValue() >= rst0 && RstCurrent.getMag().getValue() < rst1){
            StrRst.getPhsA().getCVal().setValue(dif0, 0);
            StrRst.getPhsB().getCVal().setValue(dif0, 0);
            StrRst.getPhsC().getCVal().setValue(dif0, 0);
        } else {
            StrRst.getPhsA().getCVal().setValue(RstCurrent.getMag().getValue() * kT1, 0);
            StrRst.getPhsB().getCVal().setValue(RstCurrent.getMag().getValue() * kT1, 0);
            StrRst.getPhsC().getCVal().setValue(RstCurrent.getMag().getValue() * kT1, 0);
        }

        /**Проверка уставки*/
        boolean phsA = DifACIc.getPhsA().getCVal().getMag().getValue() > StrRst.getPhsA().getCVal().getMag().getValue();
        boolean phsB = DifACIc.getPhsB().getCVal().getMag().getValue() > StrRst.getPhsB().getCVal().getMag().getValue();
        boolean phsC = DifACIc.getPhsC().getCVal().getMag().getValue() > StrRst.getPhsC().getCVal().getMag().getValue();
        boolean general = phsA || phsB || phsC;

        /**Инициализация Пуска на Срабатывание (обнаружено нарушение или недопустимое состояние) */
        Str.getPhsA().setValue(phsA);
        Str.getPhsB().setValue(phsB);
        Str.getPhsC().setValue(phsC);
        Str.getGeneral().setValue(general);

        if (Str.getPhsA().getValue()) breakerTimeA ++; else breakerTimeA = 0;
        if (Str.getPhsB().getValue()) breakerTimeB ++; else breakerTimeB = 0;
        if (Str.getPhsC().getValue()) breakerTimeC ++; else breakerTimeC = 0;

        /** Сброс выдержки, если блокировка по гармоническим составляющим*/
        if(BlkOpPHAR.getStValPhA().getValue())breakerTimeA = 0;
        if(BlkOpPHAR.getStValPhB().getValue())breakerTimeB = 0;
        if(BlkOpPHAR.getStValPhC().getValue())breakerTimeC = 0;

        /** Сброс выдержки, если блокировка при неисправности токовых цепей*/
        if(BlkOpSCTR.getStValPhA().getValue())breakerTimeA = 0;
        if(BlkOpSCTR.getStValPhB().getValue())breakerTimeB = 0;
        if(BlkOpSCTR.getStValPhC().getValue())breakerTimeC = 0;

        /**Инициализация Пуска на отключение (решение защиты об отключении) при превышении уставки по времени*/
        if (breakerTimeA > MinOpTmms.getSetVal().getValue()) Op.getPhsA().setValue(true);
        if (breakerTimeB > MinOpTmms.getSetVal().getValue()) Op.getPhsB().setValue(true);
        if (breakerTimeC > MinOpTmms.getSetVal().getValue()) Op.getPhsC().setValue(true);
        Op.getGeneral().setValue(Op.getPhsA().getValue() || Op.getPhsB().getValue() || Op.getPhsC().getValue());
    }
    // ================================================ Не используемые ================================================
    // todo Информация об общих логических узлах
    /** Счетчик числа переключений со сбросом */
    private INC OpCntRs = new INC();
    // todo Информация о статусе
    /**Срабатывание детектора насыщения*/
    private ACT SatDet = new ACT();
    // todo Параметры настройки
    /**Емкость линии (для токов нагрузки) */
    private ASG LinCapac = new ASG();
    /**Нижний порог срабатывания, процент номинального тока */
    private ING LoSet = new ING();
    /**Верхний порог срабатывания, процент номинального тока */
    private ING HiSet = new ING();
    /**
     *Максимальное время срабатывания Элемент данных Maxium Operating Time (максимальное время действия) [в миллисекундах
     * (мс)] логического узла используется для координирования действия соответствующей функции
     */
    private ING MaxOpTmms = new ING();
    /**
     Режим ограничения
     */
    private ING RstMod = new ING();
    /**
     *Время задержки сброса - Время выдержки в миллисекундах (мс) до сброса, как только будут выполнены условия для
     * сброса
     */
    private ING RsDITmms = new ING();
    /**
     *График характеристик для выполнения действия по защите, который выражен в виде зависимости:
        * у = f(x), где х = А (ток) и у = Т т (время). Целые числа, которыми представлены различные
     * кривые, приведены в определении графика класса общих данных (CDC)
     */
    private CURVE TmACrv = new CURVE();
    /**Ток небаланса */
    private WYE ImbCur = new WYE();




}
