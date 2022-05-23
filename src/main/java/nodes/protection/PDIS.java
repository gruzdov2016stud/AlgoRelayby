package nodes.protection;

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

/** */
@Getter
@Setter
public class PDIS extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Пуск (колебания мощности обнаружены) из RPSB*/
    private ACD StrColeb = new ACD();
    /**Сопротивление из MMXU */
    private WYE Z = new WYE();
    /** Уставка - Уровень контролируемой величины, при которой активируется назначенное действия соответствующей функции из main */
    private ASG StrVal = new ASG();
    /** Время задержки срабатывания - Время выдержки в миллисекундах (мс) до начала выполнения действия, как только будут выполнены условия для срабатывания из main */
    private ING OpDLTmms = new ING();
    /** Режим направленной защиты из main */
    private ING DirMod = new ING();
    /** Направление мощности из RDIR*/
    private ACD Dir = new ACD();
    /** Полярная область - это диаметр на круговой диаграмме проводимости*/
    private ASG PoRch = new ASG();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////

    /** Пуск - обнаружено нарушение или недопустимое состояние. Элемент Str может включать в себя информацию о фазе и направлении в CSWI*/
    private ACD Str = new ACD();
    /** Срабатывание (тип атрибута ACT класса общих данных) — означает решение функции защиты (логического узла) об отключении. Команда на отключение выдается в узле PTRC в CSWI */
    private ACT Op = new ACT();

    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    /** Пуск (появление первого, связанного с КЗ, направления мощности)*/
    private ACD StrDir = new ACD();
    /** Текущая задержка*/
    private int tmms;
    /** Срабатывание защиты*/
    private boolean activationA;
    private boolean activationB;
    private boolean activationC;
    /**
     * Счетчик времени после пуска реле защиты
     */
    private double breakerTimeA = 0, breakerTimeB = 0, breakerTimeC = 0;

    @Override
    public void process() {


        if (!StrColeb.getGeneral().getValue()) { //
            if (Z.getPhsA().getCVal().getMag().getValue() < PoRch.getSetMag().getF().getValue()) { // Проверка на фазу А
                breakerTimeA += 20.0/80;
                if (DirMod.getSetVal().getValue() == 1) { // Режим направленной защиты
                    if (StrDir.getPhsA().getValue()) { // Входит в защищаемую зону
                        activationA = true;
                        Op.getPhsA().setValue(activationA);
                    } else { // Не входит в защищаемую зону
                        activationA = false;
                        Op.getPhsA().setValue(activationA);
                    }
                } else { // Режим ненаправленной защиты
//                    Op.getGeneral().setValue(true);
                    activationA = true;
                }
            } else breakerTimeA = 0;
            if (Z.getPhsB().getCVal().getMag().getValue() < PoRch.getSetMag().getF().getValue()) { // Проверка на фазу А
                breakerTimeB += 20.0/80;
                if (DirMod.getSetVal().getValue() == 1) { // Режим направленной защиты
                    if (StrDir.getPhsB().getValue()) { // Входит в защищаемую зону
                        Op.getPhsB().setValue(true);
                    } else { // Не входит в защищаемую зону
                        Op.getPhsB().setValue(false);

                    }
                } else { // Режим ненаправленной защиты
//                    Op.getGeneral().setValue(true);
                    activationB = true;
                }
            } else breakerTimeB = 0;
            if (Z.getPhsC().getCVal().getMag().getValue() < PoRch.getSetMag().getF().getValue()) { // Проверка на фазу А
                breakerTimeC += 20.0/80;
                if (DirMod.getSetVal().getValue() == 1) { // Режим направленной защиты
                    if (StrDir.getPhsC().getValue()) { // Входит в защищаемую зону
//                        Op.getGeneral().setValue(true);
                        activationC = true;
                    } else { // Не входит в защищаемую зону
                        activationC = false;
                    }
                } else { // Режим ненаправленной защиты
//                    Op.getGeneral().setValue(true);
                    activationC = true;
                }
            } else { // Защита не должна срабатывать
                breakerTimeC = 0;
                activationC = false;
            }
        }

        if (activationA | activationB | activationC) {
            if (tmms++ >= OpDLTmms.getSetVal().getValue()) { // Время срабатывания
                Op.getGeneral().setValue(true);
                activationA = false;
                activationB = false;
                activationC = false;
            }
        } else {
            Op.getGeneral().setValue(false);
        }


    }

    // ================================================ Не используемые ================================================
    /** Счётчик числа переключений со сбросом*/
    private INC OpCntRs = new INC();

    /** Начальное фазное значение*/
    private ASG PhStr = new ASG();
    /** Начальное значение нулевой последовательности*/
    private ASG GndStr = new ASG();

    /** Процент области действия*/
    private ASG PctRch = new ASG();
    /** Смещение*/
    private ASG Ofs = new ASG();
    /** Процент смещения*/
    private ASG PctOfs = new ASG();
    /** Область сопротивления для зоны нагрузки*/
    private ASG RisLod = new ASG();
    /** Угол для зоны нагрузки*/
    private ASG AngLod = new ASG();
    /** Режим задержки времени срабатывания*/
    private SPG TmDIMod = new SPG();
    /** Многофазный режим задержки времени срабатывания*/
    private SPG PhDIMod = new SPG();
    /** Время задержки срабатывания при многофазных КЗ*/
    private ING PhDITmms = new ING();
    /** Однофазный режим задержки времени срабатывания*/
    private SPG GndDIMod = new SPG();
    /** Время задержки срабатывания при однофазных замыканиях на землю*/
    private ING GndDITmms = new ING();
    /** Реактивное сопротивление линии (обалсти действия) прямой последовательности*/
    private ASG X1 = new ASG();
    /** Угол сдвига фаз*/
    private ASG LinAng = new ASG();
    /** Область резистивного заземления*/
    private ASG RisGndRch = new ASG();
    /** Область резистивной фазы*/
    private ASG RisPhRch = new ASG();
    /** Коэффициент остаточной компенсации K0*/
    private ASG K0Fact = new ASG();
    /** Угол коэффициента остаточной компенсации K0*/
    private ASG K0FactAng = new ASG();
    /** Время задержки сброса*/
    private ING RsDITmms = new ING();

}





