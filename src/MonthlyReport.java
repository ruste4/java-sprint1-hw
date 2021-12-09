import java.util.ArrayList;

public class MonthlyReport {
    String folder = "resources/";
    String[] monthNames;
    String fileName;
    Integer monthlyNum;
    String monthlyName;
    ArrayList<Product> report;

    MonthlyReport(String fileName, String[] monthNames) {
        this.fileName = fileName;
        this.report = new ArrayList<>();
        this.monthlyNum = Integer.parseInt(fileName.substring(6, 8));
        this.monthNames = monthNames;
        this.monthlyName = this.monthNames[this.monthlyNum - 1];
    }

    public void uploadingData() {
        String fileContent = FileReader.readFileContentsOrNull(folder + fileName);
        this.report = deserialize(fileContent);
    }

    private ArrayList<Product> deserialize(String fileContent) {
        ArrayList<Product> products = new ArrayList<>();

        if (fileContent != null) {
            String[] contentLines = fileContent.split("\n");
            for (int i = 1; i < contentLines.length; i++) {
                String[] line = contentLines[i].split(",");
                String itemName = line[0];
                boolean isExpense = Boolean.parseBoolean(line[1]);
                int quantity = Integer.parseInt(line[2]);
                int sumOfOne = Integer.parseInt(line[3]);

                products.add(new Product(itemName, isExpense, quantity, sumOfOne));
            }
        }
        return products;
    }

    public Integer getTotalIncome() {
        int totalIncome = 0;

        for (Product product : this.report) {
            if (!product.isExpense) {
                totalIncome += product.getSumOfAll();
            }
        }
        return totalIncome;
    }

    public Integer getTotalExpense() {
        int totalExpense = 0;

        for (Product product : this.report) {
            if (product.isExpense) {
                totalExpense += product.getSumOfAll();
            }
        }
        return totalExpense;
    }

    public void printMonthlyInfo() {
        System.out.println("\n" + monthlyName);
        System.out.println("Самый прибыльный товар - " + getMaxProfitableProduct().itemName +
                ", на общую сумму " + getMaxProfitableProduct().getSumOfAll() + " рублей.");
        System.out.println("Самая большая трата - " + getMaxExpense().itemName +
                ", на общую сумму " + getMaxExpense().getSumOfAll() + " рублей.");
    }

    private Product getMaxProfitableProduct() {
        Product maxProfitableProduct = null;
        int maxProfit = 0;

        for (Product product : this.report) {
            if ((!product.isExpense) && (product.getSumOfAll() > maxProfit)) {
                maxProfitableProduct = product;
                maxProfit = product.getSumOfAll();
            }
        }
        return maxProfitableProduct;
    }

    private Product getMaxExpense() {
        Product maxExpenseProduct = null;
        int maxExpense = 0;

        for (Product product : this.report) {
            if ((product.isExpense) && (product.getSumOfAll() > maxExpense)) {
                maxExpenseProduct = product;
                maxExpense = product.getSumOfAll();
            }
        }
        return maxExpenseProduct;
    }
}