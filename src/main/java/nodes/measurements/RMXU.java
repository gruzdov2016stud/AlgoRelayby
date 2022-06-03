package nodes.measurements;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.data.typeData.Vector;
import objects.descriptionInfo.LPL;
import objects.measured.SAV;
import objects.measured.WYE;

import java.util.ArrayList;

/**Логический узел «Дифференциальные измерения»
 Функции, моделируемые логическим узлом RMXU:
 1) Режим работы логического узла;
 2) Выдача дифференциальных измерений.
 Применяется для реализации функции расчета дифференциальных измерений для нужд защит.
 Применяется один экземпляр ЛУ на одну группу измерений. Экземпляры ЛУ
 размещаются в логических устройствах РЗА. */
@Getter @Setter
public class RMXU extends LN {
    ///////////////////////////////////////////////////////////////////////////
    // todo Входные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Ток местного измерителя*/
    private WYE Aloc = new WYE();
    ///////////////////////////////////////////////////////////////////////////
    // todo Выходные параметры
    ///////////////////////////////////////////////////////////////////////////
    /**Дифференциальный ток */
    private WYE DifACIc = new WYE();
    /**Ток торможения */
    private WYE RstA = new WYE();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    /** Входные сигналы */
    private ArrayList<WYE> inputsA = new ArrayList<>();
    /** Временный вектор для хранения промежуточного значения для тормазного тока*/
    private Vector rstCurrent = new Vector();
    private Vector Idifa = new Vector();
    private Vector Idifb = new Vector();
    private Vector Idifc = new Vector();

    double St = 500*(Math.pow(10,3));
    ArrayList<Float> U = new ArrayList<>();
    ArrayList<Float> Kt = new ArrayList<>();
    ArrayList<Float> basis = new ArrayList<>();

    /** Общий случай*/
    public RMXU(double Ibasis) {
        for (int j = 0; j != 2; j++) {
            basis.add((float) Ibasis);
        }
    }

    public RMXU() {
        U.add(500F);
        U.add(10F);
        Kt.add(160F);
        Kt.add(8000F);
        for (int j = 0; j != 2; j++) {
            basis.add((float) (St / (U.get(j) * Kt.get(j) * Math.sqrt(3))));
        }
    }

    @Override
    public void process() {
        for (int j = 0; j != 2; j++) {
            for(WYE w: inputsA){
                inputsA.get(j).getPhsA().getCVal().getMag().setValue(
                        inputsA.get(j).getPhsA().getCVal().getMag().getValue()/basis.get(j)
                );
                inputsA.get(j).getPhsB().getCVal().getMag().setValue(
                        inputsA.get(j).getPhsA().getCVal().getMag().getValue()/basis.get(j)
                );
                inputsA.get(j).getPhsC().getCVal().getMag().setValue(
                        inputsA.get(j).getPhsA().getCVal().getMag().getValue()/basis.get(j)
                );
            }
        }


        /*Обнулим начальный вектор тормозного тока*/
        rstCurrent.setValueO(0, 0);
        for (WYE Aloc : inputsA) {

            /*Суммирование векторов по осям для каждой фазы*/
            Idifa.Slozhenie(inputsA.get(0).getPhsA().getCVal(), inputsA.get(1).getPhsA().getCVal());
            Idifa.setValueO(Idifa.getOrtX().getValue(),Idifa.getOrtY().getValue());

            Idifb.Slozhenie(inputsA.get(0).getPhsB().getCVal(), inputsA.get(1).getPhsB().getCVal());
            Idifb.setValueO(Idifb.getOrtX().getValue(),Idifb.getOrtY().getValue());

            Idifc.Slozhenie(inputsA.get(0).getPhsC().getCVal(), inputsA.get(1).getPhsC().getCVal());
            Idifc.setValueO(Idifc.getOrtX().getValue(),Idifc.getOrtY().getValue());


            /*Поиск максимального значения тормозного тока */
            if (Aloc.getPhsA().getCVal().getMag().getValue() > rstCurrent.getMag().getValue()) {
                rstCurrent.setValueO(Aloc.getPhsA().getCVal().getOrtX().getValue(), Aloc.getPhsA().getCVal().getOrtY().getValue());
            }
            if (Aloc.getPhsB().getCVal().getMag().getValue() > rstCurrent.getMag().getValue()) {
                rstCurrent.setValueO(Aloc.getPhsB().getCVal().getOrtX().getValue(), Aloc.getPhsB().getCVal().getOrtY().getValue());
            }
            if (Aloc.getPhsC().getCVal().getMag().getValue() > rstCurrent.getMag().getValue()) {
                rstCurrent.setValueO(Aloc.getPhsC().getCVal().getOrtX().getValue(), Aloc.getPhsC().getCVal().getOrtY().getValue());
            }
        }
        /** Расчёт значений по-фазно*/
        DifACIc.getPhsA().setCVal(Idifa);
        DifACIc.getPhsB().setCVal(Idifb);
        DifACIc.getPhsC().setCVal(Idifc);

        RstA.getPhsA().getCVal().setValueO(rstCurrent.getOrtX().getValue(), rstCurrent.getOrtY().getValue());
        RstA.getPhsB().getCVal().setValueO(rstCurrent.getOrtX().getValue(), rstCurrent.getOrtY().getValue());
        RstA.getPhsC().getCVal().setValueO(rstCurrent.getOrtX().getValue(), rstCurrent.getOrtY().getValue());
    }

    // ================================================ Не используемые ================================================

// todo Описание
    /**Информация о логическом узле */
    private LPL NamPlt = new LPL();
// todo Информация о состоянии
    /**Индикация поведения */
    //ENS Beh
    /**Индикация исправности */
    //ENS Health
// todo Измеряемые параметры
    /**Ток местного измерителя фазы A */
    private SAV AmpLocPhsA = new SAV();
    /**Ток местного измерителя фазы B*/
    private SAV AmpLocPhsB = new SAV();
    /**Ток местного измерителя фазы C */
    private SAV AmpLocPhsC = new SAV();
    /**Остаточный ток местного измерителя*/
    private SAV AmpLocRes = new SAV();
// todo Элементы управления
    /**Режим работы */
    //ENC Mod
}
