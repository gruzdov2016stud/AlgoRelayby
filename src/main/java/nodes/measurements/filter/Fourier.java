package nodes.measurements.filter;

import objects.data.typeData.Vector;
import objects.measured.SAV;

public class Fourier extends Filter {
    /*Количество снятий сигнала за период*/
    private int size = 80;
    /*Шаг дискретизации*/
    private double delta_t = 2 * Math.PI/size;
    /*Коэффициент фильтра фурье*/
    private double kf = Math.sqrt(2)/size;
    /*sin и cos для разложения на ортогональные составляющие*/
    private double sin[]= new double[size];
    private double cos[]= new double[size];
    /*Буфер памяти для оси Х и Y*/
    private double[] bufferX = new double[size];
    private double[] bufferY = new double[size];
    /*Сумма для оси Х и Y */
    private double sumX = 0.0;
    private double sumY = 0.0;
    /*Число, для того чтобы "двигать" буфер «прошлого» и «будущего»*/
    private int count =0;


    public Fourier() {
        for (int i=0;i<80; i++) {
            sin[i]=Math.sin(delta_t*i);
            cos[i]=Math.cos(delta_t*i);
        }
    }

    @Override
    public void process(SAV sav, Vector vector) {
        /*Запоминаем новое пришедшее число по Х и по У*/
        double a = sav.getInstMag().getF().getValue()*sin[count];
        double b = sav.getInstMag().getF().getValue()*cos[count];
        /*Сумма по оси Х и Y */
        sumX += a - bufferX[count];
        sumY += b - bufferY[count];
        /*Действующее значение и Угол в радианах */
        float mag = (float) Math.sqrt(sumX * sumX + sumY * sumY);
        float ang = (float) Math.atan2(sumY, sumX);
        /*Заполняем значения из «прошлого» - «будущем»*/
        bufferX[count] = a;
        bufferY[count] = b;
        /*Передаем значения Амплитуды и Угла для дальнейшего обращения */
        vector.setValueO((float) (sumX*kf), (float) (sumY*kf));
        if (++count >= size) count = 0;
    }

}
