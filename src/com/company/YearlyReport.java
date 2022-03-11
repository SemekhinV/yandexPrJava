package com.company;
import java.util.*;

public class YearlyReport {

    ArrayList<String> YearReport;

    YearlyReport(){
        YearReport = new ArrayList<>();
    }

    void addRecord(String lines){
        YearReport.add(lines);
    }

    public int incomeExpenseForMonth(String line) {                       //Метод извлекает приведенные к типу Int значения
        String[] content = line.split(",");
        if(content[2].startsWith("true")){                  //Все попытки привести переменную к типу bool,
                                                            //почему-то, успехом не увенчались, всегда был false
            return (int)Double.parseDouble(content[1]) * (-1);
        } else {
            return (int)Double.parseDouble(content[1]);
        }
    }



}
