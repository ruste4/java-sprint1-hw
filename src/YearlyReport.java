import java.util.ArrayList;

public class YearlyReport {
    String folder = "resources/";
    String[] monthNames;
    String fileName;
    Integer yearlyNum;
    ArrayList<Month> report;

    YearlyReport(String fileName, String[] monthNames) {
        this.fileName = fileName;
        this.yearlyNum = Integer.parseInt(fileName.substring(2, 6));
        this.report = new ArrayList<>();
        this.monthNames = monthNames;
    }

    public void printYearlyInfo() {
        System.out.println("\n" + this.yearlyNum);
        for (Month month : report) {
            System.out.println("Прибыль в месяце " + month.monthName + " составила " +
                    month.getProfit() + " руб.");
        }
        System.out.println("Средний расход за все месяцы в году " + averageExpense() + " руб.");
        System.out.println("Средний доход за все месяцы в году " + averageIncome() + " руб.");

    }

    private Integer averageExpense() {
        int expenseSum = 0;

        for (Month month : this.report) {
            expenseSum += month.expense;
        }
        return expenseSum / this.report.size();
    }

    private Integer averageIncome() {
        int incomeSum = 0;

        for (Month month : report) {
            incomeSum += month.income;
        }
        return incomeSum / report.size();
    }

    public void uploadingData() {
        this.report.clear();
        String fileContent = FileReader.readFileContentsOrNull(this.folder + this.fileName);
        this.report = deserialize(fileContent);
    }

    private ArrayList<Month> deserialize(String fileContent) {
        ArrayList<Month> parsedYearlyReportRows = new ArrayList<>();

        if (fileContent != null) {
            String[] contentLines = fileContent.split("\n");
            Integer income = null;
            Integer expense = null;

            for (int i = 1; i < contentLines.length; i++) {
                String[] line = contentLines[i].split(",");
                int monthlyNum = Integer.parseInt(line[0]);
                String monthName = this.monthNames[monthlyNum - 1];
                int amount = Integer.parseInt(line[1]);
                boolean isExpense = Boolean.parseBoolean(line[2]);

                if (isExpense) {
                    expense = amount;
                } else {
                    income = amount;
                }

                if ((expense != null) && (income != null)) {
                    parsedYearlyReportRows.add(new Month(monthName, monthlyNum, income, expense));
                    expense = null;
                    income = null;
                }
            }
        }
        return parsedYearlyReportRows;
    }
}