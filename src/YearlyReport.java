import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class YearlyReport {
    String fileName = "y.2021.csv";
    String folder = "resources/";
    ArrayList<Month> report;
    String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
            "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };

    public void uploadingData() {
        String path = this.folder + this.fileName;
        String fileContents = readFileContentsOrNull(path);
        this.report = deserialize(fileContents);
    }

    public void printInfoAllMonthlyReports() {
        System.out.println(fileName.substring(2,6) + " год");
        for (Month month : report) {
            System.out.println("Прибыль в месяце " + month.monthName + " составила " +
                    month.getProfit() + " руб.");
        }
        System.out.println("Средний расход за все месяцы в году " + averageExpense() + " руб.");
        System.out.println("Средний доход за все месяцы в году " + averageIncome() + " руб.");
    }

    private int averageExpense() {
        int expenseSum = 0;

        for (Month month : report) {
            expenseSum += month.expense;
        }
        return expenseSum / report.size();
    }

    private int averageIncome() {
        int incomeSum = 0;

        for (Month month : report) {
            incomeSum += month.income;
        }
        return incomeSum / report.size();
    }

    private ArrayList<Month> deserialize(String fileContents) {
        ArrayList<Month> months = new ArrayList<>();
        String[] lines = fileContents.split("\\n");
        for (int i = 1; i < lines.length; i++) {
            String[] line = lines[i].split(",");
            String monthNum = line[0];
            String monthName = monthNames[Integer.parseInt(monthNum) - 1];
            int income = findMonthlyIncome(monthNum, lines);
            int expense = findMonthlyExpense(monthNum, lines);
            Month month = new Month(monthName, monthNum, income, expense);

            if (!months.contains(month)) {
                months.add(month);
            }
        }
        return months;
    }

    private int findMonthlyIncome(String requiredMonthNum, String[] lines) {
        for (int i = 1; i < lines.length; i++) {
            String[] line = lines[i].split(",");
            boolean isExpense = Boolean.parseBoolean(line[2]);
            String monthNum = line[0];
            int amount = Integer.parseInt(line[1]);
            if (!isExpense && monthNum.equals(requiredMonthNum)) {
                return amount;
            }
        }
        return 0;
    }

    private int findMonthlyExpense(String requiredMonthNum, String[] lines) {
        for (int i = 1; i < lines.length; i++) {
            String[] line = lines[i].split(",");
            boolean isExpense = Boolean.parseBoolean(line[2]);
            String monthNum = line[0];
            int amount = Integer.parseInt(line[1]);
            if (isExpense && monthNum.equals(requiredMonthNum)) {
                return amount;
            }
        }

        return 0;
    }

    private String readFileContentsOrNull(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, " +
                    "файл не находится в нужной директории.");
            return null;
        }
    }
}