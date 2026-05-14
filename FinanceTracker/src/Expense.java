import java.time.LocalDate;

public class Expense extends Transaction {

    private Boolean recurring;

    public Expense(String title, double amount, String category, LocalDate date, boolean recurring){
        super(title, amount, category, date);
        this.recurring = recurring;
    }


    @Override
    public String toString(){
        return super.toString() +
        "\nRecurring: " + recurring;
    }

    @Override
    public String toFileString(){
        return "Expense," + super.toFileString() + "," + recurring;
    }


    // setter
    public void setRecurring(boolean recurring){
        this.recurring = recurring;
    }


    // getter
    public boolean getRecurring(){
        return recurring;
    }
}
