package nodes.gui.other;


import objects.data.DataAttribute;

/**
 * @author Александр Холодов
 * @created 01.2021
 * @project RPA-MPEI
 * @description Сигнал для построения графиков
 */
public class NHMISignal{

    private final String name;
    private final DataAttribute<?> dataX, dataY;

    public NHMISignal(String name, DataAttribute<?> data) { this.name = name; this.dataX = null; this.dataY = data; }
    public NHMISignal(String name, DataAttribute<?> dataX, DataAttribute<?> dataY) { this.name = name; this.dataX = dataX; this.dataY = dataY; }

    public String getName() { return name; }
    public DataAttribute<?> getDataX() { return dataX; }
    public DataAttribute<?> getDataY() { return dataY; }
}
