package nodes.measurements.filter;

import objects.data.typeData.Vector;
import objects.measured.SAV;

public class RMS extends Filter{

    private int size = 20;
    private float[] buffer = new float[size];
    private float sum = 0;
    private int count = 0;


    @Override
    public void process(SAV sav, Vector vector) {
        float S = sav.getInstMag().getF().getValue();
        sum += S*S - buffer[count];
        buffer[count] = S*S;
        vector.getMag().setValue((float)Math.sqrt(sum/size));
        if(++count >= size) count = 0;
    }
}
