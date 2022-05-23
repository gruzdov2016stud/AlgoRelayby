package nodes.controls;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.enums.StVal;
import objects.info.ACT;
import objects.info.SPS;
import objects.managment.DPC;
import objects.managment.INC;

import java.util.ArrayList;
import java.util.List;

/**
 * Данный логический узел используется
 * для управления всеми состояниями переключений выше технологического уровня. Логический узел
 * должен выполнить подписку на получение данных POWCap — point-on-wave switching capability (фаза точки
 * переключения) от узла XCBR, если это возможно. Если получена команда на переключение, например
 * Select-before-Operate (выбрать, затем управлять), и в выключателе поддерживается функция переключения
 * в заданной фазе, то команда передается на логический узел CPOW. Элементы данных ОрОрп и OpCIs
 * используются при отсутствии сервисов, работающих в режиме реального времени между логическими
 * узлами CSWI и XCBR (см. GSE в МЭК 61850-7-2).
 */
@Getter @Setter
public class CSWI extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /** Действие «Отключить выключатель»*/
    private ACT OpOpn = new ACT();
    /**Действие «Отключить выключатель» от 1ст*/
    private ACT OpOpn1 = new ACT();
    /**Действие «Отключить выключатель» от 2ст*/
    private ACT OpOpn2 = new ACT();
    /**Действие «Отключить выключатель» от 3ст*/
    private ACT OpOpn3 = new ACT();
    /**Действие «Отключить выключатель» от 4ст*/
    private ACT OpOpn4 = new ACT();
    /**Действие «Отключить выключатель» от 5ст*/
    private ACT OpOpn5 = new ACT();
    /**Действие «Включить выключатель» из main*/
    private ACT OpCls = new ACT();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Выключатель общий*/
    private DPC Pos = new DPC();
    /**Выключатель L1 Этот элемент данных используется для переключений, когда одна фаза А может быть контролируемой отдельно*/
    private DPC PosA = new DPC();
    /**Выключатель L2 Этот элемент данных используется для переключений, когда одна фаза В может быть контролируемой отдельно*/
    private DPC PosB = new DPC();
    /**Выключатель L3 Этот элемент данных используется для переключений, когда одна фаза С может быть контролируемой отдельно*/
    private DPC PosC = new DPC();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    @Override
    public void process() {
        //объединяем сигналы на отключениe. Используется в ЛР2, при запуске раскомментировать
//        if(     OpOpn1.getGeneral().getValue() ||
//                OpOpn2.getGeneral().getValue() ||
//                OpOpn3.getGeneral().getValue() ||
//                OpOpn4.getGeneral().getValue() ||
//                OpOpn5.getGeneral().getValue()){
//            OpOpn.getGeneral().setValue(true);
//        } else{
//            OpOpn.getGeneral().setValue(false);
//        }
        /**Если General из Op пуск на отключение - true - значит защита сработала, следовательно надо отключить
         * StVal() из Pos меняет своё положение на off и отправляет данные узлу XCBR на выключение
         * то CtIVal из Pos Определяет положение выключателя - false - значит выключен */
        if (OpOpn.getGeneral().getValue() && Pos.getStVal().getValue() == StVal.ON){
            Pos.getCtIVal().setValue(false);
            Pos.getStVal().setValue(StVal.OFF);
        }
    }

    // ================================================ Не используемые ================================================
    /**
     *Локальная операция
     *
     * Данное переключение всегда выполняют по месту с помощью механического ключа или тумблера.
     * У механического ключа или тумблера может быть набор контактов, позволяющих
     * считывать данные о положении. Этот элемент данных означает переключение между локальным
     * и дистанционным управлением; локальное = TRUE; дистанционное = FALSE. «Локальный
     * » (local) при обозначении секции указывает управление из блока присоединения, а «дистанционный
     * » (remote) указывает управление со станции. На технологическом уровне «локальный
     * » указывает действие, выполняемое непосредственно на технологическом устройстве, например
     * на выключателе, а «дистанционный» указывает управление с секции. Если в логическом
     * устройстве элемент данных Loc в режиме LLN0 противоречит элементу данных Loc в ином
     * режиме, который включен в логический узел, «локальный» всегда доминирует
     */
    private SPS Loc = new SPS();
    /**
     *Счетчик числа переключений со сбросом
     *
     * Этот элемент данных представляет счетчик числа переключений в логическом узле со сбросом.
     * Использование атрибута INC класса общих данных позволяет установить настройки счетчика
     * не только на «0», но и на иное число
     */
    private INC OpCntRs = new INC();

}
