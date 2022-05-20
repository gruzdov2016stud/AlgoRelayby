package nodes.gui.other;

import nodes.common.LN;
import nodes.gui.NHMI;
import nodes.gui.NHMIP;
import objects.data.DataAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Александр Холодов
 * @created 12/2020
 * @project RPA-MPEI
 * @description Пример использования графиков
 */
public class NHMIexample{

    /** Все узлы в данной работе */
    private static final List<LN> logicalNodes = new ArrayList<>();


    public static void main(String[] args) {

        NHMI nhmi = new NHMI();
        logicalNodes.add(nhmi);

        DataAttribute<Double> signal1 = new DataAttribute<>(0.0);
        DataAttribute<Double> signal2 = new DataAttribute<>(0.0);

        nhmi.addSignals(
                new NHMISignal("TestSignal1", signal1),
                new NHMISignal("TestSignal2", signal2));
        nhmi.addSignals(
                "График",
                new NHMISignal("TestSignal2", signal2));


        NHMIP nhmip = new NHMIP();
        logicalNodes.add(nhmip);
        nhmip.addSignals(new NHMISignal("TestSignal", signal1, signal2));


        /**
         * Пример зоны срабатывания
         */
        double x0 = 0, y0 = 0, r = 10;
        List<NHMIPoint<Double, Double>> pointsList = new ArrayList<>();

        for(double x= -2*r; x<= 2*r; x += 0.1) {
            double y = Math.sqrt(Math.pow(r, 2) - Math.pow((x-x0), 2)) + y0;
            pointsList.add(new NHMIPoint<>(x, y));
            pointsList.add(new NHMIPoint<>(x, -y));
        }
        nhmip.drawCharacteristic("Characteristic", pointsList);



        for(double i = 1; i<101; i++){
            signal1.setValue(1 * i);
            signal2.setValue(2 * i);

//            logicalNodes.forEach(nodes::process);
        }
    }
}
