package nodes.registration.protection;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.enums.Direction;
import objects.data.typeData.Quality;
import objects.descriptionInfo.CSD;
import objects.info.ACD;
import objects.info.ACT;
import objects.managment.INC;
import objects.managment.SPC;
import objects.measured.WYE;
import objects.setStatus.ING;
import objects.setStatus.SPG;
import objects.task.ASG;
import objects.task.CURVE;

/**
 *
 */
@Getter
@Setter
public class PDIS extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Пуск (колебания мощности обнаружены) из RPSB
     */
    private ACD StrColeb = new ACD();
    /**
     * Сопротивление из MMXU
     */
    private WYE Z = new WYE();
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
     * Полярная область - это диаметр на круговой диаграмме проводимости
     */
    private ASG PoRch = new ASG();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Пуск - обнаружено нарушение или недопустимое состояние. Элемент Str может включать в себя информацию о фазе и направлении в CSWI
     */
    private ACD Str = new ACD();
    /**
     * Срабатывание (тип атрибута ACT класса общих данных) — означает решение функции защиты (логического узла) об отключении. Команда на отключение выдается в узле PTRC в CSWI
     */
    private ACT Op = new ACT();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Счетчик времени после пуска реле защиты
     */
    private double breakerTimeA = 0, breakerTimeB = 0, breakerTimeC = 0;

    @Override
    public void process() {

        /**Проверка уставки*/
        boolean phsA = Z.getPhsA().getCVal().getMag().getValue() < PoRch.getSetMag().getValue();
        boolean phsB = Z.getPhsB().getCVal().getMag().getValue() < PoRch.getSetMag().getValue();
        boolean phsC = Z.getPhsC().getCVal().getMag().getValue() < PoRch.getSetMag().getValue();
        boolean general = phsA || phsB || phsC;

        /**Инициализация Пуска на Срабатывание (обнаружено нарушение или недопустимое состояние)*/
        Str.getPhsA().setValue(phsA);
        Str.getPhsB().setValue(phsB);
        Str.getPhsC().setValue(phsC);
        Str.getGeneral().setValue(general);

        if (Str.getPhsA().getValue()) breakerTimeA += 20.0 / 80;
        else breakerTimeA = 0;
        if (Str.getPhsB().getValue()) breakerTimeB += 20.0 / 80;
        else breakerTimeB = 0;
        if (Str.getPhsC().getValue()) breakerTimeC += 20.0 / 80;
        else breakerTimeC = 0;

//        /*Фаза A*/
//        if (DirMod.getSetVal().getValue() == 1) { // Режим направленной защиты
//            if (Dir.getDirPhsA().getValue() == Direction.FORWARD) { // Входит в защищаемую зону
////                Op.getPhsA().setValue(true);
//                breakerTimeA += 20.0 / 80;
//            } else { // Не входит в защищаемую зону
////                Op.getPhsA().setValue(false);
//                breakerTimeA = 0;
//            }
//        } else { // Режим ненаправленной защиты
//            breakerTimeA += 20.0 / 80;
////            Op.getPhsA().setValue(true);
//        }
//        /*Фаза B*/
//        if (DirMod.getSetVal().getValue() == 1) { // Режим направленной защиты
//            if (Dir.getDirPhsB().getValue() == Direction.FORWARD) { // Входит в защищаемую зону
//                breakerTimeB += 20.0 / 80;
//            } else { // Не входит в защищаемую зону
//                breakerTimeB = 0;
//            }
//        } else { // Режим ненаправленной защиты
//            breakerTimeB += 20.0 / 80;
//        }
//        /*Фаза C*/
//        if (DirMod.getSetVal().getValue() == 1) { // Режим направленной защиты
//            if (Dir.getDirPhsC().getValue() == Direction.FORWARD) { // Входит в защищаемую зону
//                breakerTimeC += 20.0 / 80;
//            } else { // Не входит в защищаемую зону
//                breakerTimeC = 0;
//            }
//        } else { // Режим ненаправленной защиты
//            breakerTimeC += 20.0 / 80;
//        }
//
//        /**Инициализация Пуска на отключение (решение защиты об отключении) при превышении уставки по времени*/
//        if (breakerTimeA > OpDLTmms.getSetVal().getValue()) Op.getPhsA().setValue(true);
//        if (breakerTimeB > OpDLTmms.getSetVal().getValue()) Op.getPhsB().setValue(true);
//        if (breakerTimeC > OpDLTmms.getSetVal().getValue()) Op.getPhsC().setValue(true);
//        if (Op.getPhsA().getValue() || Op.getPhsB().getValue() || Op.getPhsC().getValue()) {
//            Op.getGeneral().setValue(true);
//            breakerTimeA = 0;
//            breakerTimeB = 0;
//            breakerTimeC = 0;
//        }

//        else {
//            Op.getGeneral().setValue(false);
//        }

        if (DirMod.getSetVal().getValue() == 1) {
            /**Если направление "за спину"*/
            if (Dir.getDirPhsA().getValue() == Direction.FORWARD) {
                breakerTimeA = 0;
            }
            if (Dir.getDirPhsB().getValue() == Direction.FORWARD) {
                breakerTimeB = 0;
            }
            if (Dir.getDirPhsC().getValue() == Direction.FORWARD) {
                breakerTimeC = 0;
            }
            /**Инициализация Пуска на отключение (решение защиты об отключении) при превышении уставки по времени*/
            if (breakerTimeA > OpDLTmms.getSetVal().getValue()) Op.getPhsA().setValue(true);
            if (breakerTimeB > OpDLTmms.getSetVal().getValue()) Op.getPhsB().setValue(true);
            if (breakerTimeC > OpDLTmms.getSetVal().getValue()) Op.getPhsC().setValue(true);
            Op.getGeneral().setValue(Op.getPhsA().getValue() || Op.getPhsB().getValue() || Op.getPhsC().getValue());
        }
        else{
            Op.getGeneral().setValue(false);
        }


    }

    // ================================================ Не используемые ================================================
    /**
     * Счётчик числа переключений со сбросом
     */
    private INC OpCntRs = new INC();

    /**
     * Начальное фазное значение
     */
    private ASG PhStr = new ASG();
    /**
     * Начальное значение нулевой последовательности
     */
    private ASG GndStr = new ASG();

    /**
     * Процент области действия
     */
    private ASG PctRch = new ASG();
    /**
     * Смещение
     */
    private ASG Ofs = new ASG();
    /**
     * Процент смещения
     */
    private ASG PctOfs = new ASG();
    /**
     * Область сопротивления для зоны нагрузки
     */
    private ASG RisLod = new ASG();
    /**
     * Угол для зоны нагрузки
     */
    private ASG AngLod = new ASG();
    /**
     * Режим задержки времени срабатывания
     */
    private SPG TmDIMod = new SPG();
    /**
     * Многофазный режим задержки времени срабатывания
     */
    private SPG PhDIMod = new SPG();
    /**
     * Время задержки срабатывания при многофазных КЗ
     */
    private ING PhDITmms = new ING();
    /**
     * Однофазный режим задержки времени срабатывания
     */
    private SPG GndDIMod = new SPG();
    /**
     * Время задержки срабатывания при однофазных замыканиях на землю
     */
    private ING GndDITmms = new ING();
    /**
     * Реактивное сопротивление линии (обалсти действия) прямой последовательности
     */
    private ASG X1 = new ASG();
    /**
     * Угол сдвига фаз
     */
    private ASG LinAng = new ASG();
    /**
     * Область резистивного заземления
     */
    private ASG RisGndRch = new ASG();
    /**
     * Область резистивной фазы
     */
    private ASG RisPhRch = new ASG();
    /**
     * Коэффициент остаточной компенсации K0
     */
    private ASG K0Fact = new ASG();
    /**
     * Угол коэффициента остаточной компенсации K0
     */
    private ASG K0FactAng = new ASG();
    /**
     * Время задержки сброса
     */
    private ING RsDITmms = new ING();

}





