package nodes.registration.protection;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.typeData.Quality;
import objects.descriptionInfo.CSD;
import objects.info.ACD;
import objects.info.ACT;
import objects.managment.INC;
import objects.managment.SPC;
import objects.measured.WYE;
import objects.setStatus.ING;
import objects.task.ASG;
import objects.task.CURVE;

/**
 * LN: Максимальная токовая защита без выдержки времени или защита
 * по нарастанию параметров
 * Реле максимального тока без выдержки времени или по нарастанию параметров представляет собой реле, которое срабатывает без выдержки времени при значительном превышении значения тока или при значительном нарастании величины тока
 */
@Getter
@Setter
public class PIOC extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /** Дифференциальный ток из RMXU */
    private WYE DifACIc = new WYE();
    /**
     * Уставка - Уровень контролируемой величины, при которой активируется назначенное действия соответствующей функции из main
     */
    private ASG StrVal = new ASG();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**
     * Пуск на отключение (тип атрибута ACT класса общих данных) — означает решение функции защиты (логического узла) об отключении. Команда на отключение выдается в узле PTRC в CSWI
     */
    private ACT Op = new ACT();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    public PIOC(double strVal) {
        this.StrVal.getSetMag().setValue((float) strVal);
    }
    public PIOC(){
    }
    @Override
    public void process() {
        /**Проверка уставки*/
        boolean phsA = DifACIc.getPhsA().getCVal().getMag().getValue() > StrVal.getSetMag().getValue();
        boolean phsB = DifACIc.getPhsB().getCVal().getMag().getValue() > StrVal.getSetMag().getValue();
        boolean phsC = DifACIc.getPhsC().getCVal().getMag().getValue() > StrVal.getSetMag().getValue();
        boolean general = phsA || phsB || phsC;

        /**Инициализация Пуска на отключение (решение защиты об отключении) при превышении уставки по времени*/
        Op.getPhsA().setValue(phsA);
        Op.getPhsB().setValue(phsB);
        Op.getPhsC().setValue(phsC);
        Op.getGeneral().setValue(general);
    }
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
     * Это тип графика сброса, используемый для координирования сбросов в электромагнитных
     * реле, не приводимых мгновенно в исходное состояние
     */
    private ING TypRsCrv = new ING();

}
