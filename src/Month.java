import java.util.Objects;

public class Month {
    String monthName;
    String monthNum;
    int income;
    int expense;

    Month(String monthName, String monthNum, int income, int expense) {
        this.monthName = monthName;
        this.monthNum = monthNum;
        this.income = income;
        this.expense = expense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Month month = (Month) o;
        return income == month.income && expense == month.expense &&
                Objects.equals(monthName, month.monthName) &&
                Objects.equals(monthNum, month.monthNum);
    }

    public int getProfit() {
        return this.income - this.expense;
    }
}
