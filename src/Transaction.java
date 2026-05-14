import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Transaction {

    private static int nextID = 1;
    private int transactionID;

    private String title;
    private double amount;
    private String category;
    private LocalDate date;

    // constructor used for creating new objects (no ID)
    public Transaction(String title, double amount, String category, LocalDate date){

        this.transactionID = nextID++;
        this.title = title;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }


    // constructor used for loading and saving existing objects
    public Transaction(int id, String title, double amount, String category, LocalDate date){

        this.transactionID = id;

        // Updates the nextID so next transaction doesn't have the same ID
        if(id >= nextID){
            nextID = id + 1;
        }

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
        return transactionID + "," + title + "," + amount + "," + category + "," + date;
    }


    // setters
    public void setTitle(String title){
        this.title = title;
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public void setDate(LocalDate date){
        this.date = date;
    }

    // getters
    public int getTransactionID(){
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
