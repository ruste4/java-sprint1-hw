import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MonthlyReport monthlyReport = new MonthlyReport();
        YearlyReport yearlyReport = new YearlyReport();

        while (true) {
            printMenu();
            String command = scanner.next();

            if (command.equals("1")) {
                monthlyReport.uploadingData();
            } else if (command.equals("2")) {
                yearlyReport.uploadingData();
            } else if (command.equals("3")) {
                verifyTheData(yearlyReport, monthlyReport);
            } else if (command.equals("4")) {
                monthlyReport.printInfoAllMonthlyReports();
            } else if (command.equals("5")) {
                yearlyReport.printInfoAllMonthlyReports();
            }else if (command.equals("quit")) {
                break;
            } else {
                System.out.println("Такой команды нет");
            }
        }
    }

    private static void verifyTheData(YearlyReport yReport, MonthlyReport mReport) {
        boolean verifyResult = true;
        for (Month month : yReport.report) {
            int totalIncome = mReport.getTotalIncome(month.monthNum);
            int totalExpense = mReport.getTotalExpense(month.monthNum);
            boolean incomeVerify = totalIncome == month.income;
            boolean expenseVerify = totalExpense == month.expense;

            if (!incomeVerify || !expenseVerify) {
                System.out.println("В отчетах за " + month.monthName + " несоответствие");
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
