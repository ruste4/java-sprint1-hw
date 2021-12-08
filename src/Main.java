import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] monthlyFiles = {"m.202101.csv", "m.202102.csv", "m.202103.csv"};
        String yearlyFile = "y.2021.csv";
        HashMap<Integer, MonthlyReport> monthlyReports = new HashMap<>();
        YearlyReport yearlyReport = null;

        while (true) {
            printMenu();
            String command = scanner.next();

            if (command.equals("quit")) {
                break;
            }

            switch (command) {
                case "1":
                    monthlyReports.clear();
                    for (String mothlyFile : monthlyFiles) {
                        MonthlyReport report = new MonthlyReport(mothlyFile);
                        report.uploadingData();
                        monthlyReports.put(report.monthlyNum, report);
                    }
                    break;
                case "2":
                    yearlyReport = new YearlyReport(yearlyFile);
                    yearlyReport.uploadingData();
                    if (yearlyReport.report.isEmpty()) {
                        System.out.println("Не получилось загрузить годовой отчет");
                        yearlyReport = null;
                    }
                    break;
                case "3":
                    if (yearlyReport != null || !monthlyReports.isEmpty()) {
                        verifyReports(monthlyReports, yearlyReport);
                    } else {
                        System.out.println("\nЗагрузите месячные и годовой отчеты");
                    }
                    break;
                case "4":
                    if (!monthlyReports.isEmpty()) {
                        for (MonthlyReport report : monthlyReports.values()) {
                            report.printMonthlyInfo();
                        }
                    } else {
                        System.out.println("\nЗагрузите месячные отчеты");
                    }
                    break;
                case "5":
                    if (yearlyReport != null) {
                        yearlyReport.printYearlyInfo();
                    } else {
                        System.out.println("\nФайл с годовым отчетом не загружен");
                    }
                    break;
                default:
                    System.out.println("Такой команды нет");
            }


        }
    }

    private static void verifyReports(HashMap<Integer, MonthlyReport> monthlyReports,
                                      YearlyReport yearlyReport) {
        Boolean verifyResult = true;

        for (Month yMonth : yearlyReport.report) {
            Integer monthNum = yMonth.monthNum;
            MonthlyReport mMonth = monthlyReports.get(monthNum);
            int yIncome = yMonth.income;
            int yExpense = yMonth.expense;
            int mIncome = mMonth.getTotalIncome();
            int mExpense = mMonth.getTotalExpense();

            if ((yIncome != mIncome) || (yExpense != mExpense)) {
                System.out.println("В отчетах за " + yMonth.monthName + " несоответствие");
                verifyResult = false;
            }
        }

        if (verifyResult) {
            System.out.println("Сверка отчетов прошла успешно");
        }
    }

    public static void printMenu() {
        System.out.println("\nВедите номер пункта меню чтобы выбрать действие ...");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте \n");
        System.out.println("quit - Завершить работу");
    }
}
