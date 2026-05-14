import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private static int nextID = 1;
    private int transactionID;

    private String title;
    private double amount;
    private String category;
    private LocalDate date;

    public Transaction(String title, double amount, String category, LocalDate date){

        this.transactionID = nextID++;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }


    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        return  "Transaction ID: " + transactionID +
                "\nTitle: " + title +
                "\nAmount: £" + String.format("%.2f",amount) +
                "\nCategory: " + category +
                "\nDate: " + date.format(formatter);
    }


    public String toFileString(){
        return title + "," + amount + "," + category + "," + date;
    }


    // setters
    public void setTitle(){
        this.title = title;
    }

    public void setAmount(){
        this.amount = amount;
    }

    public void setCategory(){
        this.category = category;
    }

    public void setDate(){
        this.date = date;
    }

    // getters
    public int getID(){
        return transactionID;
    }

    public String getTitle(){
        return title;
    }

    public double getAmount(){
        return amount;
    }

    public String getCategory(){
        return category;
    }

    public LocalDate getDate(){
        return date;
    }
}
