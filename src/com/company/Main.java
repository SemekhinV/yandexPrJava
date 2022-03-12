package com.company;
import java.util.*;


public class Main {

    public static void main(String[] args) {
        userMenu helper = new userMenu();
        Scanner scanner = new Scanner(System.in);
        fileMaster CSVReader = new fileMaster();

        MonthlyReport mReport = null;
        YearlyReport yReport = null;

        int command;

        System.out.println("""
                                
                Вас приветствует автоматизированная бухгалтерия!
                Для использования введите одну из цирф в пределе диапазона :)
                """);

        boolean flag = true;

        while(flag) {
            helper.TUI();

            System.out.print("\nВведите команду: ");
            command = scanner.nextInt();

            switch (command) {
                case 1 -> {                                 //Считать все месячные отчеты
                    System.out.println("Добавьте все необходимые отчеты в папку resources.\n");
                    mReport = CSVReader.readMonthReports();
                }
                case 2 -> {
                    System.out.println("Добавьте все необходимые отчеты в папку resources.\n");
                    yReport = CSVReader.readYearReport();   //Считать годовой отчет
                }
                case 3 -> {                                 //Сверить отчеты
                    if (CSVReader.reportsCompare()){
                        System.out.println("\nСравнение доходов и расходов завершено, ошибок не обнаружено.");
                    }
                }
                case 4 -> {                                 //Вывести информацию о всех месячных отчетах
                    if(mReport == null){
                        System.out.println("Месячные отчеты не сосканированны!\nПожалуйста, вызовите соответсвтующий метод");
                        break;
                    }
                    System.out.println("\nИнформация о всех месячных отчетах: \n");
                    for (int i = 0; i < mReport.getMonth().size(); i++){
                        System.out.println(mReport.getShortReport(i) + "\n");
                    }
                }
                case 5 -> {                                 //Вывести информацию о всех годовых отчетах
                    if (yReport == null || mReport == null){
                        System.out.println("Информации об одном из видов отчетов не найдено." +
                                "\nДля работы необходимо считать годовой и месячный отчет");
                        break;
                    }
                    CSVReader.shortReportYear(mReport.getMonth());
                }
                case 6 -> {
                    System.out.println("Введите новый путь до корневой папки: ");
                    String newPath = scanner.nextLine();
                    CSVReader.setPathToRoot(newPath);
                }
                default -> {
                    System.out.println("Завершение работы...");
                    flag = false;
                }
            }

        }
    }
}
