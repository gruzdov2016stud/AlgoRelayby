package nodes.protection;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.enums.Direction;
import objects.data.enums.Validity;
import objects.data.typeData.Quality;
import objects.descriptionInfo.CSD;
import objects.info.ACD;
import objects.info.ACT;
import objects.managment.INC;
import objects.managment.SPC;
import objects.measured.MV;
import objects.measured.WYE;
import objects.setStatus.ING;
import objects.task.ASG;
import objects.task.CURVE;

/**
 * LN:Максимальная токовая защита с выдержкой времени - МТЗ в цепях переменного тока с выдержкой времени представляет
 * собой реле, которое срабатывает при превышении заранее определенного значения входного переменного тока и
 * когда входной токи время срабатывания обратно пропорционально фактической доле производительности
 * <p>
 * Данный логический узел (LN) используется для моделирования направленной максимальной токовой
 * защиты с выдержкой времени (PDOC/IEEE 67). Конкретная максимальная токовая защита с выдержкой
 * времени (также и PTOC/IEEE 51) моделируется с помощью РТОС и выбора соответствующего
 * графика.
 */
@Getter
@Setter
public class PTOC extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Вектор тока из MSQI
     */
    private WYE A = new WYE();
    /**
     * Уставка - Уровень контролируемой величины, при которой активируется назначенное действия соответствующей функции из main
     */
    private ASG StrVal = new ASG();
    /**
     * Время задержки срабатывания - Время выдержки в миллисекундах (мс) до начала выполнения действия, как только будут выполнены условия для срабатывания из main
     */
    private ING OpDLTmms = new ING();
    /**
     * Режим направленной защиты из main
     */
    private ING DirMod = new ING();
    /**
     * Направление мощности из RDIR
     */
    private ACD Dir = new ACD();
    /**
     * Режим автоматического ускорения (false выкл, true вкл) из main - false
     * Ненаправленные защиты - Режим автоматического ускорения(вводится только для 3-й ступени, так как этот режим влияет только на 3-ю ступень ненаправленной защиты)
     * Направленные защиты - Режим автоматического ускорения(вводится для всех направленных ступеней)
     */
    private SPC automaticAccelearation = new SPC();
    /**
     * Качество мгновенных значений Ia,Ib,Ic,Ua,Ub,Uc представляющего значение данных. из ParseQuality
     */
    private Quality qualityIa, qualityIb, qualityIc, qualityUa, qualityUb, qualityUc;
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Пуск на Срабатывание - обнаружено нарушение или недопустимое состояние. Элемент Str может включать в себя информацию о фазе и направлении в CSWI
     */
    private ACD Str = new ACD();
    /**
     * Пуск на отключение (тип атрибута ACT класса общих данных) — означает решение функции защиты (логического узла) об отключении. Команда на отключение выдается в узле PTRC в CSWI
     */
    private ACT Op = new ACT();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Счетчик времени после пуска реле защиты
     */
    private double breakerTimeA = 0, breakerTimeB = 0, breakerTimeC = 0;
    /**
     * Cчетчик времени после ввода АУ
     */
    private double dLTmmsAutomaticAccelearation = 0;

    @Override
    public void process() {
        /**Проверка уставки*/
        boolean phsA = A.getPhsA().getCVal().getMag().getValue() > StrVal.getSetMag().getF().getValue();
        boolean phsB = A.getPhsB().getCVal().getMag().getValue() > StrVal.getSetMag().getF().getValue();
        boolean phsC = A.getPhsC().getCVal().getMag().getValue() > StrVal.getSetMag().getF().getValue();
        boolean general = phsA || phsB || phsC;

        /**Инициализация Пуска на Срабатывание (обнаружено нарушение или недопустимое состояние)*/
        Str.getPhsA().setValue(phsA);
        Str.getPhsB().setValue(phsB);
        Str.getPhsC().setValue(phsC);
        Str.getGeneral().setValue(general);

        if (Str.getPhsA().getValue()) breakerTimeA += 0.25;
        if (Str.getPhsB().getValue()) breakerTimeB += 0.25;
        if (Str.getPhsC().getValue()) breakerTimeC += 0.25;


/**Условия проверки контролирования уставки направления защиты (направленной == 1, FORWARD | ненаправленная == 0, BACKWARD)*/
        if (DirMod.getSetVal().getValue() == 1) {
            /**Если направление фаз "за спину"*/
            if (Dir.getDirGeneral().getValue() == Direction.FORWARD) {
                breakerTimeA = 0;
                breakerTimeB = 0;
                breakerTimeC = 0;
            }
        } else {
            if (Dir.getDirGeneral().getValue() == Direction.BACKWARD) {
                breakerTimeA = 0;
                breakerTimeB = 0;
                breakerTimeC = 0;
            }
        }
        /**Автоматическое ускорение*/
        if (automaticAccelearation.getCtIVal().getValue()) OpDLTmms.getSetVal().setValue(0);

        /**Инициализация Пуска на отключение (решение защиты об отключении) при превышении уставки по времени*/
        if (breakerTimeA > OpDLTmms.getSetVal().getValue()) Op.getPhsA().setValue(true);
        if (breakerTimeB > OpDLTmms.getSetVal().getValue()) Op.getPhsB().setValue(true);
        if (breakerTimeC > OpDLTmms.getSetVal().getValue()) Op.getPhsC().setValue(true);
        /**Срабатывание защиты при достижение уставки по времени*/
        if (Op.getPhsA().getValue() || Op.getPhsB().getValue() || Op.getPhsC().getValue()) {
            Op.getGeneral().setValue(true);
//            breakerTimeA = 0;
//            breakerTimeB = 0;
//            breakerTimeC = 0;
        }

//        /**Проверка качества сигнала и принятия решения о предотвращении сработки защиты*/
//        setQuality(qualityIa = new Quality());
//        setQuality(qualityIb = new Quality());
//        setQuality(qualityIc = new Quality());
//        setQuality(qualityUa = new Quality());
//        setQuality(qualityUb = new Quality());
//        setQuality(qualityUc = new Quality());

    }

    /**
     * Функция реализующая проверку качества сигнала
     * @param quality - сигнал для которого следует сделать проверку качества.
     */
//    public void setQuality(Quality quality) {
//        /**Если качество сигнало плохое, то защита не сработает*/
//        if (quality.getValidity().getValue() == Validity.INVALID || quality.getValidity().getValue() == Validity.QUESTIONABLE) {
//            breakerTimeA = 0;
//            breakerTimeB = 0;
//            breakerTimeC = 0;
//        }
//    }


    // ================================================ Не используемые ================================================
    // todo Информация об общих логических узлах
    /**
     * Счетчик числа переключений со сбросом
     */
    private INC OpCntRs = new INC();
    // todo Информация о статусе
    /**
     * Направляет Динамические характеристики графика
     */
    private CSD TmASt = new CSD();
    // todo Параметры настройки
    /**
     * График характеристик для выполнения действия по защите, который выражен в виде зависимости:
     * у = f(x), где х = А (ток) и у = Т т (время). Целые числа, которыми представлены различные
     * кривые, приведены в определении графика класса общих данных (CDC)
     */
    private CURVE TmACrv = new CURVE();

    /**
     * Этот элемент данных представляет собой умножитель уставок времени или установку времени
     * на круговой шкале, используемый в основном для защиты
     */
    private ASG TmMult = new ASG();
    /**
     * Минимальное время срабатывания Элемент данных Minimum Operating Time (минимальное время действия в мс) логического
     * узла используется для координирования работы с более старыми электромеханическими
     * реле
     */
    private ING MinOpTmms = new ING();
    /**
     * Максимальное время срабатывания Элемент данных Maxium Operating Time (максимальное время действия) [в миллисекундах
     * (мс)] логического узла используется для координирования действия соответствующей функции
     */
    private ING MaxOpTmms = new ING();

    /**
     * Это тип графика сброса, используемый для координирования сбросов в электромагнитных
     * реле, не приводимых мгновенно в исходное состояние
     */
    private ING TypRsCrv = new ING();
    /**
     * Время задержки сброса - Время выдержки в миллисекундах (мс) до сброса, как только будут выполнены условия для
     * сброса
     */
    private ING RsDITmms = new ING();


}
