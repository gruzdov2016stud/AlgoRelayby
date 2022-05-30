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
    /**Ток небаланса */
    private WYE ImbCur = new WYE();
    ///////////////////////////////////////////////////////////////////////////
    // todo Реализация узла
    ///////////////////////////////////////////////////////////////////////////
    /** Входные сигналы */
    private ArrayList<WYE> inputs = new ArrayList<>();
    /** Временный вектор для хранения промежуточного значения для тормазного тока*/
    private Vector rstCurrent = new Vector();
    double St = 500*(Math.pow(10,3));
    ArrayList<Float> U = new ArrayList<>();
    ArrayList<Float> Kt = new ArrayList<>();
    ArrayList<Float> basis = new ArrayList<>();

    /** Общий случай*/
    public RMXU() {
        U.add(500F);
        U.add(10F);
        Kt.add(160F);
        Kt.add(8000F);
    }
    /**
     * @param aloc ...aloc_n - Токи измерителя на концах линии из MMXU
     */
    public RMXU(WYE...aloc) {
        U.add(500F);
        U.add(10F);
        Kt.add(160F);
        Kt.add(8000F);
        // Расчитать базисный токи ВН и ННArray float
        for(int i = 0; i < inputs.size(); i++) {
            this.inputs.add(aloc[i]);
        }
    }

    @Override
    public void process() {
        for (int j = 0; j != 2; j++) {
            basis.add((float) (St / (U.get(j)* Kt.get( j)*Math.sqrt(3))));
            for(WYE w: inputs){
                inputs.get(j).getPhsA().getCVal().getMag().setValue(
                        inputs.get(j).getPhsA().getCVal().getMag().getValue()/basis.get(j)
                );
                inputs.get(j).getPhsB().getCVal().getMag().setValue(
                        inputs.get(j).getPhsA().getCVal().getMag().getValue()/basis.get(j)
                );
                inputs.get(j).getPhsC().getCVal().getMag().setValue(
                        inputs.get(j).getPhsA().getCVal().getMag().getValue()/basis.get(j)
                );
            }
        }

        float dAx = 0, dAy = 0;
        float dBx = 0, dBy = 0;
        float dCx = 0, dCy = 0;
        /*Обнулим начальный вектор тормозного тока*/
        rstCurrent.setValueO(0, 0);

        for (WYE Aloc : inputs) {
            /*Суммирование векторов по осям для каждой фазы*/
            dAx += Aloc.getPhsA().getCVal().getOrtX().getValue();
            dAy += Aloc.getPhsA().getCVal().getOrtY().getValue();

            dBx += Aloc.getPhsB().getCVal().getOrtX().getValue();
            dBy += Aloc.getPhsB().getCVal().getOrtY().getValue();

            dCx += Aloc.getPhsC().getCVal().getOrtX().getValue();
            dCy += Aloc.getPhsC().getCVal().getOrtY().getValue();
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
        DifACIc.getPhsA().getCVal().setValueO(dAx, dAy);
        DifACIc.getPhsB().getCVal().setValueO(dBx, dBy);
        DifACIc.getPhsC().getCVal().setValueO(dCx, dCy);
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
