package objects.setStatus;

import objects.data.Data;
import objects.data.DataAttribute;

/** 7.7.2 */
public class SPG extends Data { /**  Single point setting (SPG) - Установка состояния одноэлементная */

    /* Установка состояния */
    /** Настройка параметра состояния */
    private DataAttribute<Boolean> setVal = new DataAttribute<>(false);
}
