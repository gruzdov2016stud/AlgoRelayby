package nodes.measurements.filter;

import objects.data.typeData.Vector;
import objects.measured.SAV;

public abstract class Filter {
    public abstract void process(SAV sav, Vector vector);
}
