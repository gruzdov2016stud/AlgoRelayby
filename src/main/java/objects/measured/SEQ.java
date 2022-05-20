package objects.measured;


import lombok.Getter;
import lombok.Setter;
import objects.data.Data;
import objects.data.DataAttribute;
import objects.data.enums.AngRefWYE;
import objects.data.enums.PhsRef;
import objects.data.enums.SeqT;

/**7.4.7 Класс SEQ (последовательность)
 * В классе приведено определение общих данных класса Sequence (SEQ).
 * Этот класс представляет собой набор компонентов последовательности значений.
 */
@Getter
@Setter
public class SEQ extends Data {

/*
    todo Состояние
*/
    /**Компонент последовательности 1. Семантическое значение */
    private CMV С1 = new CMV();
    /**Компонент последовательности 2. Семантическое значение */
    private CMV С2 = new CMV();
    /**Компонент последовательности 0. Семантическое значение */
    private CMV С3 = new CMV();
/*
    todo Измеряемые атрибуты
*/
    /**Этот атрибут специфицирует тип последовательности напр.- c1*/
    private DataAttribute<SeqT> seqPOS = new DataAttribute<>(SeqT.POS);
    /**Этот атрибут специфицирует тип последовательности напр.- c1*/
    private DataAttribute<SeqT> seqDIR = new DataAttribute<>(SeqT.DIR);
    /**Этот атрибут специфицирует тип последовательности отриц.- c2*/
    private DataAttribute<SeqT> seqNEG = new DataAttribute<>(SeqT.NEG);
    /**Этот атрибут специфицирует тип последовательности квадр.- c2*/
    private DataAttribute<SeqT> seqQUAD = new DataAttribute<>(SeqT.QUAD);
    /**Этот атрибут специфицирует тип последовательности нуль- c0*/
    private DataAttribute<SeqT> seqZERO = new DataAttribute<>(SeqT.ZERO);

/*w
    todo Конфигурация, описание и расширение
*/
    /**Показывает, что фаза "А" была использована в качестве ссылки для преобразования фазных значений в значения последовательности */
    private DataAttribute<PhsRef> phsRefA = new DataAttribute<>(PhsRef.A);
    /**Показывает, что фаза "B" была использована в качестве ссылки для преобразования фазных значений в значения последовательности */
    private DataAttribute<PhsRef> phsRefB = new DataAttribute<>(PhsRef.B);
    /**Показывает, что фаза "C" была использована в качестве ссылки для преобразования фазных значений в значения последовательности */
    private DataAttribute<PhsRef> phsRefC = new DataAttribute<>(PhsRef.C);

    private String d;
    private String dU;
    private String cdcNs;
    private String cdcName;
    private String dataNs;

}

