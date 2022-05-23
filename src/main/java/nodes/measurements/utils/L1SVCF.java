package nodes.measurements.utils;

import lombok.Getter;
import lombok.Setter;
import nodes.common.LN;
import objects.measured.SAV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class L1SVCF extends LN {
    @Getter @Setter
    /** Выборка мгновенных значений фналоговых сигналов*/
    private List<SAV> signals = new ArrayList<>();
    /** Считанный файл CSV */
    private List<String> csvFileLines = new ArrayList<>();
    private Iterator<String> iterator;

    /** Загрузить CSV файл (.csv) */
    public void readCSV(String csvPath){
        csvFileLines = readFile(csvPath + ".csv");
        iterator = csvFileLines.iterator();
        for (int i = 0; i < 100; i++) {
            signals.add(new SAV());
        }

    }
    /** Загрузить содержимое файла */
    public List<String> readFile(String path){
        List<String> fileEntry = new ArrayList<>();

        try {
            File file = new File(path);
            if(!file.exists()) System.err.println(path + " - Файл не найден, неправильно указан путь");

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            bufferedReader.readLine(); // Пропускаем первую строку
            String line = bufferedReader.readLine();
            while(line!=null){
                fileEntry.add(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) { e.printStackTrace(); }

        return fileEntry;
    }
    /** Функция осуществляет парсинг строк и добавляет значения токов Ia, Ib, Ic */
    @Override
    public void process() {
        if(iterator.hasNext()){
            String[] split = iterator.next().split(",");
            for (int i = 0; i <= 2; i++) {
                float value = Float.parseFloat(split[i+1]);
                SAV sav = signals.get(i); // Ia  Ib  Ic
                sav.getInstMag().getF().setValue(value * 1000);
            }
        }
    }
    /** Функция осуществляет итерацию по строкам*/
    public boolean hasNext() {
        return iterator.hasNext();
    }
}