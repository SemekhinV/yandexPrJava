package com.company;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;


public class fileMaster {
    String pathToRoot;                              //Изменяемая переменная, чтобы задать путь до директории с отчетами
    List<Path> pathToFiles;                         //Список путей до файлов с отчетами в директории
    List<Integer> expenseIncomeForMonth;            //Двумерный массив трат и доходов за месяц
    List<Integer> expenseIncomeForYear;             //Аналогично для года, но индексом будет true - трата, false - прибыль



    fileMaster()  {
        pathToRoot = "./resources";
        pathToFiles = new ArrayList<>();
        expenseIncomeForMonth = new ArrayList<>();
        expenseIncomeForYear = new ArrayList<>();
    }

    public void setPathToRoot(String path){
        pathToRoot = path;
    }

    private void getPath(){

        try {                                        //Сохраняем путь до всех имеющихся файлов для работы в цикле
            pathToFiles = Files.list(Path.of(pathToRoot)).collect(Collectors.toList());

            System.out.println("В каталоге " + pathToRoot + " находятся следующие файлы: \n" + pathToFiles + "\n");

        }catch (IOException e){

            System.out.println("Каталог пуст!");
        }
    }

    private String readFileContent(Path path){
        try {
            return Files.readString(path);
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл!");
            return null;
        }
    }

    public MonthlyReport readMonthReports(){      //Блок обработки файла

        getPath();                                //Получение всех файлов в директории было вынесено
                                                  //В отдельный метод чтобы не писать одно и тоже
        int counter = 1;

        MonthlyReport mReports = new MonthlyReport();

        for(Path path : pathToFiles) {

            String file = readFileContent(path);

            if(path.getFileName().toString().equals("m.20210" + counter + ".csv")) {  //Отсекаем всевозможные файлы кроме ежемесячных отчетов
                                                                //С помощью примера с сайта практики считываем файл

                if (file != null) {                             //Если есть данные - разбиваем на строчки
                    System.out.println("\nСчитываем файл " + "\u001B[33m" + path + "\u001B[0m");
                    String[] lines = file.split("\\n");   //После чего построчно заносим значение

                    int income = 0;
                    int expense = 0;

                    int maxIncome = 0;
                    String maxIncomeName = " ";

                    for (String line : lines) {                 //В класс для хранения ежемесячных отчетов
                        if (line.startsWith("item_name")) {     //По ТЗ хранить строки с индексами не нужно
                            continue;
                        }
                        mReports.addRecord(line);

                        if(mReports.expenseOrIncome(line)){            //При работе с отчетами сразу будем искать лучший товар
                            expense += mReports.quantityOnSum(line);  //Для дальнейшего выведения в кратком отчете

                        } else {
                            income += mReports.quantityOnSum(line);

                            if (mReports.quantityOnSum(line) > maxIncome){
                                maxIncome = mReports.quantityOnSum(line);
                                maxIncomeName = mReports.getProductName(line);
                            }
                        }
                        //System.out.println(line);
                    }
                    expenseIncomeForMonth.addAll(Arrays.asList(income, expense));      //В конце файла добавляем запись
                                                                                       //Чтобы получился двумерный массив
                                                                                       //Прибыли и трат на выходе
                    mReports.setProduct(maxIncomeName, maxIncome);
                    //Добавляем запись о нужных товарах для краткого отчета по всем месяцам

                    mReports.setMonth(Month.of(counter).toString());

                    counter++;

                } else {
                    break;
                }

            } else {counter++;}
        }

        return mReports;
    }

    public YearlyReport readYearReport(){

        getPath();                                       //Метод получения списка всех файлов

        YearlyReport yReport = new YearlyReport();       //Примерно аналогичная схема

        for (Path path : pathToFiles){

            String file = readFileContent(path);

            if (path.getFileName().toString().equals(("y.2021.csv"))){

                System.out.println("\nСчитываем файл " + "\u001B[33m" + path + "\u001B[0m");
                if(file != null) {
                    String[] lines = file.split("\\n");
                    for (String line : lines) {
                        if (line.startsWith("month")) {
                            continue;
                        }
                        yReport.addRecord(line);                //В множество трат и доходов сохраняется
                                                                //Числовое значение. Если > 0 - прибыль, 0 <
                        expenseIncomeForYear.add(yReport.incomeExpenseForMonth(line));

                        //System.out.println(line);
                    }
                } else {
                    break;
                }
            }
        }
        return yReport;
    }

    public boolean reportsCompare(){                                           //Подсчет значений встроен в методы
        if(expenseIncomeForMonth.isEmpty() || expenseIncomeForYear.isEmpty()){                    //Обработки месячных и годовых отчетов
            System.out.println("Данные из отчетов не были обработаны.\nВызовите соответсвующий метод.");
            return false;
        }

        for (int spendOrGet : expenseIncomeForYear){

            int index = expenseIncomeForYear.indexOf(spendOrGet) + 1;   //Индекс текущей переменной на случай не совпадения данных в отчетах

            if(spendOrGet < 0) {                             //Если больше нуля - доход, меньше нуля - доход
                spendOrGet *= -1;
            }
                                                             //Использую Math.celi() чтобы при делении округлялось в большую сторону
            if(!expenseIncomeForMonth.contains(spendOrGet)) {
                System.out.println("В месячнем отчете под номером №" + (int)Math.ceil(index/2.0) + " содержится ошибка, проверьте правильность отчета.");
            }
        }
        return true;
    }

    public void shortReportYear(List<String> Month){
        System.out.println("""

                Информация о годовом отчете:\s
                Сейчас 2021 год.
                """);

        int tempSum = 0;
        int indexForMonth = 0;
        for (int i = 0; i < expenseIncomeForYear.size(); i++){
            if (i % 2 != 0) {
                System.out.println("Прибыль за " + Month.get(indexForMonth) + " Составила - " + tempSum);
                indexForMonth++;
            }
            tempSum += expenseIncomeForYear.get(i);
        }
    }

}
