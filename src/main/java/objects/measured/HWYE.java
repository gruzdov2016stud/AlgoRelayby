package objects.measured;

 import lombok.Getter;
 import lombok.Setter;
 import objects.data.Data;
 import objects.data.DataAttribute;
 import objects.data.typeData.Vector;

 import java.util.ArrayList;

/**  Harmonic value for WYE (HWYE) - Значение гармоник для звезды */
@Getter
@Setter
public class HWYE extends Data {

    /* Гармоники и интергармоники */

    /** Массивы гармонических и субгармонических или интергармонических значений фаз*/
    private ArrayList<Vector> phsAHar = new ArrayList<>(); //аррей лист из мнгновенных значений гармоник
    private ArrayList<Vector> phsBHar = new ArrayList<>();
    private ArrayList<Vector> phsCHar = new ArrayList<>();

    /** Массив гармонических и субгармонических или интергармонических значений по отношению к нейтрали */
    private ArrayList<Vector> neutHar = new ArrayList<>();

    /**
     * Массив гармонических и субгармонических или интергармонических значений, содержащихся в сальдо тока
     */
    private ArrayList<Vector> netHar = new ArrayList<>();

    /**
     * Массив гармонических и субгармонических или интергармонических значений, содержащихся в остаточном токе
     */
    private ArrayList<Vector> resHar = new ArrayList<>();


    public HWYE() {
        for (int i = 0; i < numHar.getValue(); i++) {
            phsAHar.add(new Vector());
            phsBHar.add(new Vector());
            phsCHar.add(new Vector());
            neutHar.add(new Vector());
        }
    }



    /* Конфигурация, описание и расширение */

    /**
     * Число гармонических и субгармонических или интергармонических значений
     */
    private DataAttribute<Integer> numHar = new DataAttribute<>(10);

    /**
     * Число циклов промышленной частоты
     */
    private DataAttribute<Integer> numCyc = new DataAttribute<>(0);

    /**
     * Окно времени применяется к расчету гармоник, в мс
     */
    private DataAttribute<Integer> evalTm = new DataAttribute<>(0);

    /**
     * Определяет в соответствии с теоремой Котельникова наивысшую возможную определяемую гармонику или интергармонику
     * минимальное значение составляет двойное значение основной частоты
     */
    private DataAttribute<Integer> smpRate = new DataAttribute<>(0);

    /**
     * Номинальная частота энергосистемы или некоторая другая основная частота в Гц
     */
    private DataAttribute<Float> frequency = new DataAttribute<>((float) 0);

    /**
     * Число периодов промышленной частоты
     */
    private DataAttribute<Integer> rmsCyc = new DataAttribute<>(0);
}
