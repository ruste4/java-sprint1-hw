public class Month {
    String monthName;
    int monthNum;
    int income;
    int expense;

    Month(String monthName, int monthNum, int income, int expense) {
        this.monthName = monthName;
        this.monthNum = monthNum;
        this.income = income;
        this.expense = expense;
    }

    public int getProfit() {
        return this.income - this.expense;
    }
}
