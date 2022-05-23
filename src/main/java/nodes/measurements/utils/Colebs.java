package nodes.measurements.utils;

import nodes.common.LN;
import objects.data.DataAttribute;

public class Colebs extends LN {

    private ObertkaSin[] sins;

    private float[][] calcs;

    private DataAttribute<Float> res = new DataAttribute(0f);

    private int volume = 0;

    private float[] values;

    public Colebs(ObertkaSin[] sins) {
        this.sins = sins;
        calcs = new float[sins.length][sins[0].getTime()];
        for (int i = 0; i < sins.length; i++) {
            for (int j = 0; j < sins[0].getTime(); j++) {
//                float a = (float) (2 * Math.PI * sins[i].getW()*((float) j / (float) sins[i].getPeriod())+sins[i].getAng());
                calcs[i][j] = (float) (sins[i].getMag() * Math.sin(2 * Math.PI *((float) j / (float) sins[i].getPeriod())+sins[i].getAng()));
            }
        }
        System.out.println();

    }

    private int iter = 0;

    @Override
    public void process() {
        float retur = 1;
        retur *= calcs[0][iter];
        retur *= Math.abs(calcs[1][iter]);

        res.setValue(retur);
        iter++;
    }

    public boolean hasNext(){
        return iter < sins[0].getTime();
    }

    public DataAttribute<Float> getRes() {
        return res;
    }
}