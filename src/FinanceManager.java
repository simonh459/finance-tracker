import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class FinanceManager {

    private ArrayList<Transaction> transactions = new ArrayList<>();


    // method used to validate the data entered by user FOR STRINGS
    private static String checkValidString(Scanner input, String prompt){
        String value;
        while(true){
            System.out.print(prompt);
            value = input.nextLine();

            if(!value.trim().isEmpty()){
                return value;
            }
            else{
                System.out.println("This field cannot be empty");
            }
        }
    }


    // method used to validate user date from input FOR INTEGERS
    private static double checkValidNumber(Scanner input, String prompt, double minValue, double maxValue){
        while(true){
            System.out.print(prompt); // retells the prompt entered
            try{
                double value = Double.parseDouble(input.nextLine());

                if(value >= minValue && value <= maxValue){
                    return value;
                }
                else{
                    System.out.println("Value must be between " + minValue + " - " + maxValue);
                }
            }
            catch(NumberFormatException e){
                System.out.println("Invalid input. You must enter a number");
            }
        }
    }


    private LocalDate checkValidDate(Scanner scanner) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        while(true){

            try{

                System.out.print("Enter the date (dd.MM.yyyy): ");
                return LocalDate.parse(scanner.nextLine(), formatter);

            }
            catch(DateTimeParseException e){
                System.out.println("Invalid date format.");
            }
        }
    }


    private boolean checkValidBoolean(Scanner scanner){
        while(true){
            System.out.print("Is the payment recurring (true / false): ");
            String input = scanner.nextLine();

            if(input.equalsIgnoreCase("true")){
                return true;
            }
            else if(input.equalsIgnoreCase("false")){
                return false;
            }
            else{
                System.out.println("Enter `true` or `false`...");
            }
        }
    }


    // add transaction
    public Transaction addIncome(Scanner scanner){

        System.out.println(); // spacing
        System.out.println("--- ADD INCOME ---");
        scanner.nextLine(); // clear buffer

        String title = checkValidString(scanner,"Enter the title: ");
        double amount = checkValidNumber(scanner, "Enter the amount (£): ", 0.01, 99999);
        String category = checkValidString(scanner, "Enter the category: ");
        LocalDate date = checkValidDate(scanner);
        String source = checkValidString(scanner, "Enter the source: ");

        return new Income(title, amount, category, date, source);
    }


    public void createIncome(Scanner scanner){
        transactions.add(addIncome(scanner));

        System.out.println("Income added to tracker!");
        System.out.println(); // spacing
    }


    public Transaction addExpense(Scanner scanner){

        System.out.println(); // spacing
        System.out.println("--- ADD EXPENSE ---");
        scanner.nextLine(); // clear buffer

        String title = checkValidString(scanner, "Enter the title: ");
        double amount = checkValidNumber(scanner, "Enter the amount (£): ", 0.01, 99999);
        String category = checkValidString(scanner, "Enter the category: ");
        LocalDate date = checkValidDate(scanner);
        boolean recurring = checkValidBoolean(scanner);

        return new Expense(title, amount, category, date, recurring);
    }


    public void createExpense(Scanner scanner){
        transactions.add(addExpense(scanner));

        System.out.println("Expense added to tracker!");
        System.out.println(); // spacing
    }


    public void calculateBalance(){
        System.out.println(); // spacing

        double balance;
        double incomeAmount = 0;
        double expenseAmount = 0;
        for(Transaction t : transactions){
            if(t instanceof Income){
                double amount = t.getAmount();
                incomeAmount += amount;
            }
            else if(t instanceof Expense){
                double amount = t.getAmount();
                expenseAmount += amount;
            }
        }

        balance = incomeAmount - expenseAmount;

        System.out.printf("Total Income: £%.2f\n",incomeAmount);
        System.out.printf("Total Expenses: £%.2f\n\n", expenseAmount);
        System.out.printf("Total Balance: £%.2f\n\n", + balance);

    }


    public void addRecurringExpenses(){
        ArrayList<Transaction> newTransactions = new ArrayList<>();

        for(Transaction t : transactions){
            if(t instanceof Expense){         // compiler doesn't see t as an Expense, not allowing it to call Expense methods
                Expense expense = (Expense) t;

                if(expense.getRecurring()){

                    LocalDate newDate = expense.getDate().plusMonths(1);

                    // if date is before the current date, a new Expense is created
                    while(newDate.isBefore(LocalDate.now()) || newDate.equals(LocalDate.now())){
                        Expense nextMonthExpense = new Expense(expense.getTitle(),
                                expense.getAmount(),
                                expense.getCategory(),
                                newDate,
                                true);

                        if(!recurringExpenseExists(expense.getTitle(), newDate)){
                            newTransactions.add(nextMonthExpense);
                        }

                        newDate = newDate.plusMonths(1);
                    } // end while
                } // end if (checks if Expense recurring variable is "true"
            } // end if (checks if Transaction is subclass "Expense"
        } // end for
        transactions.addAll(newTransactions);

    }


    // prevents duplicate recurring expenses being added to the ArrayList
    // returns true if 2 Expenses have the same title and date
    private boolean recurringExpenseExists(String title, LocalDate date){

        for(Transaction t : transactions){
            if(t instanceof Expense){
                Expense e = (Expense) t;

                if(e.getTitle().equalsIgnoreCase(title) && e.getDate().equals(date)){
                    return true;
                }
            }
        }
        return false;
    }


    public void turnRecurringOff(Scanner scanner){

        boolean recurringFound = false;
        for(Transaction t : transactions){
            if(t instanceof Expense){
                Expense e = (Expense) t;
                if(e.getRecurring()){
                    System.out.println("ID: " + e.getTransactionID() + "\tTitle: " + e.getTitle());
                    recurringFound = true;
                 }
            }
        }

        if(!recurringFound){
            System.out.println("There are no recurring expenses in your transactions.");
            return;
        }
        scanner.nextLine();
        int choice = (int) checkValidNumber(scanner, "Enter the ID of the transaction you would like to turn off recurring bill: ", 0, Integer.MAX_VALUE);
        for(Transaction t : transactions){
            if(t instanceof Expense && t.getTransactionID() == choice){
                Expense e = (Expense) t;

                if(e.getRecurring()){
                    e.setRecurring(false);
                    System.out.println("Recurring turned OFF for " + e.getTitle());
                    System.out.println();

                    // turns off recurring bill for expenses with the same title (copies of the original)
                    for(Transaction t2 : transactions){
                        if(t2 instanceof Expense){
                            Expense copy = (Expense) t2;
                            if(copy.getTitle().equalsIgnoreCase(e.getTitle()) && copy.getRecurring()){
                                copy.setRecurring(false);
                            }
                        }
                    } // end for
                }
                else{
                    System.out.println("ID: " + choice + " already has recurring OFF.");
                    System.out.println();
                }
                return;
            } // end ID check
        } // end for
        System.out.println("ID " + choice + " does not exist in your transactions...");
        System.out.println();

    } // end method


    public void transactionHistory(){

        System.out.println(); // spacing

        // sort dates by oldest to recent
        transactions.sort(Comparator.comparing(Transaction::getDate));

        for(Transaction transaction : transactions){
            System.out.println(transaction);
            System.out.println("=====--------=====");
        }
        System.out.println(); // spacing
    }


    public void saveTransactionDetails(){
        try{
            FileWriter fWriter = new FileWriter("TransactionDetails.txt");
            BufferedWriter bWriter = new BufferedWriter(fWriter);

            for(Transaction t : transactions){
                bWriter.write(t.toFileString()); // method in Animal class to make code neater
                bWriter.newLine(); // new space (\n)
            }

            System.out.println("Transactions saved successfully!");
            bWriter.close();
        }
        catch(IOException e){
            System.out.println("An error occurred saving details");
        }
    }


    public void loadTransactionDetails(){
        try{
            File file = new File("TransactionDetails.txt");
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] pieces = line.split(",");
                Transaction transaction = null;

                String type = pieces[0];
                String title = pieces[2];
                double amount = Double.parseDouble(pieces[3]);
                String category = pieces[4];
                LocalDate date = LocalDate.parse(pieces[5]);

                if (type.equalsIgnoreCase("Income")) {
                    String source = pieces[6];
                    transaction = new Income(title, amount, category, date, source);
                }
                else if (type.equalsIgnoreCase("Expense")) {
                    boolean recurring = Boolean.parseBoolean(pieces[6]);
                    transaction = new Expense(title, amount, category, date, recurring);

                }

                if(transaction != null){
                    transactions.add(transaction);
                }
            } // end while
            scanner.close();

       }
       catch(FileNotFoundException e){
           System.out.println("File not found..");
      }

    } // end method

} // end class