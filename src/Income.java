import java.time.LocalDate;

public class Income extends Transaction {

    private String source;

    public Income(String title, double amount, String category, LocalDate date, String source){
        super(title, amount, category, date); // variables from super class Transaction
        this.source = source;
    }


    @Override
    public String toString(){
        return super.toString() +
        "\nSource: " + source;
    }

    @Override
    public String toFileString(){
        return "Income," + super.toFileString() + "," + source;
    }

    // setter
    public void setSource(String source){
        this.source = source;
    }


    // getter
    public String getSource(){
        return source;
    }
}
