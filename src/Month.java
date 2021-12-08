public class Month {
    String monthName;
    Integer monthNum;
    Integer income;
    Integer expense;

    Month(String monthName, Integer monthNum, Integer income, Integer expense) {
        this.monthName = monthName;
        this.monthNum = monthNum;
        this.income = income;
        this.expense = expense;
    }

    public int getProfit() {
        return this.income - this.expense;
    }
}
