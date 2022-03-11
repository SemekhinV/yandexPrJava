package com.company;
import java.util.*;

public class MonthlyReport {
    ArrayList<String> reports;
    List<String> mostIncomeItem;
    List<Integer> maxIncomeForMonth;
    List<String> Month;

    public void setProduct(String name, int maxIncome){
        mostIncomeItem.add(name);
        maxIncomeForMonth.add(maxIncome);
    }

    public String getShortReport(int index){
        return ("Название месяца: " + Month.get(index) +
                "\nСамый прибыльный товар:  " + mostIncomeItem.get(index) +
                "\nЗаработанная сумма: " + maxIncomeForMonth.get(index));
    }

    public void setMonth(String month){
        Month.add(month);
    }

    public List<String> getMonth(){
        return Month;
    }

    MonthlyReport(){
        reports = new ArrayList<>();
        mostIncomeItem = new ArrayList<>();
        maxIncomeForMonth = new ArrayList<>();
        Month = new ArrayList<>();
    }


    String getProductName(String line){
        return line.split(",")[0];
    }

    void addRecord(String lines){
        reports.add(lines);
    }

    public boolean expenseOrIncome(String line){
        String[] content = line.split(",");
        return Boolean.parseBoolean(content[1]);
    }

    public int quantityOnSum(String line) {               //Метод извлекает приведенные к типу Int значения
        String[] content = line.split(",");
                                                           //При попытке сделать приведение типов по-другому
                                                           //Вылетала ошибка о несовместимости типов
        return Integer.parseInt(content[2])*(int)Double.parseDouble(content[3]);
    }
}
