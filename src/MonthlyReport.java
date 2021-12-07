import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class MonthlyReport {
    String folder = "resources/";
    String[] fileNames = {"m.202101.csv", "m.202102.csv", "m.202103.csv"};
    String[] monthNames = { "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август",
            "Сентябрь", "Октябрь", "Ноябрь", "Декабрь" };
    HashMap<String, ArrayList<Product>> report = new HashMap<>();

    public void uploadingData() {
        for (String fileName : fileNames) {
            String monthNumber = fileName.substring(6, 8);
            String filePath = this.folder + fileName;
            String fileContent = readFileContentsOrNull(filePath);
            ArrayList<Product> month = deserialize(fileContent);
            report.put(monthNumber, month);
        }
    }

    public int getTotalIncome(String monthNum) {
        ArrayList<Product> month = report.get(monthNum);
        int incomeSum = 0;
        for (Product product : month) {
            if (!product.isExpense) {
                incomeSum += product.getSumOfAll();
            }
        }
        return incomeSum;
    }

    public int getTotalExpense(String monthNum) {
        ArrayList<Product> month = report.get(monthNum);
        int expenseSum = 0;
        for (Product product : month) {
            if (product.isExpense) {
                expenseSum += product.getSumOfAll();
            }
        }
        return expenseSum;
    }

    public void printInfoAllMonthlyReports() {
        for(String monthNum : report.keySet()) {
            String monthName = monthNames[Integer.parseInt(monthNum) - 1];
            Product maxProfitableProduct = getMaxProfitableProduct(report.get(monthNum));
            Product maxExpenseProduct = getMaxExpense(report.get(monthNum));

            System.out.println("\n" + monthName);
            System.out.println("Самый прибыльный товар - " + maxProfitableProduct.itemName +
                    ", на общую сумму " + maxProfitableProduct.getSumOfAll() + " рублей.");
            System.out.println("Самая большая трата - " + maxExpenseProduct.itemName +
                    ", на общую сумму " + maxExpenseProduct.getSumOfAll() + " рублей.");
        }
    }

    private Product getMaxExpense (ArrayList<Product> monthlyReport) {
        Product maxExpenseProduct = null;
        int maxExpense = 0;
        for (Product product : monthlyReport) {
            if ((product.isExpense) && (product.getSumOfAll() > maxExpense)) {
                maxExpenseProduct = product;
                maxExpense = product.getSumOfAll();
            }
        }
        return maxExpenseProduct;
    }

    private Product getMaxProfitableProduct (ArrayList<Product> monthlyReport) {
        Product maxProfitableProduct = null;
        int maxProfit = 0;
        for (Product product : monthlyReport) {
            if ((!product.isExpense) && (product.getSumOfAll() > maxProfit)) {
                maxProfitableProduct = product;
                maxProfit = product.getSumOfAll();
            }
        }
        return maxProfitableProduct;
    }

    private ArrayList<Product> deserialize(String fileContents) {
        ArrayList<Product> products = new ArrayList<>();
        String[] lines = fileContents.split("\\n");

        for (int i = 1; i < lines.length; i++) {
            String[] line = lines[i].split(",");
            String itemName = line[0];
            boolean isExpense = Boolean.parseBoolean(line[1]);
            int quantity = Integer.parseInt(line[2]);
            int sumOfOne = Integer.parseInt(line[3]);
            Product product = new Product(itemName, isExpense, quantity, sumOfOne);
            products.add(product);
        }
        return products;
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