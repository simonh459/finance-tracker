import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        FinanceManager finance = new FinanceManager();
        Scanner scanner = new Scanner(System.in);
        boolean online = true;

        System.out.println("Finance Manager Application Loading...");
        finance.loadTransactionDetails();
        finance.saveTransactionDetails(); // saves recurring expenses that are added

        int choice;
        while(online){

            System.out.println("<<<<<<<<<<< PERSONAL FINANCE TRACKER >>>>>>>>>>>");
            System.out.println("1. Add Income");
            System.out.println("2. Add Expenses");
            System.out.println("3. Calculate balance");
            System.out.println("4. View Transactions");
            System.out.println("5. Balance forecast");
            System.out.println("6. Turn off recurring expense");
            System.out.println("7. Save to file");
            System.out.println("8. Exit Program");

            try{
                System.out.print("Enter a number to run an option: ");
                choice = scanner.nextInt();

                if(choice >= 1 && choice <= 8){

                    if(choice == 1){
                        finance.createIncome(scanner);
                    }
                    else if(choice == 2){
                        finance.createExpense(scanner);
                        finance.addRecurringExpenses();

                    }
                    else if(choice == 3){
                        finance.calculateBalance();
                    }
                    else if(choice == 4){
                        finance.transactionHistory();
                    }
                    else if(choice == 5){
                        finance.balanceForecast();
                    }
                    else if(choice == 6){
                        finance.turnRecurringOff(scanner);
                    }
                    else if(choice == 7){
                        finance.saveTransactionDetails();
                    }
                    else if(choice == 8){
                        System.out.println("Exiting program...");
                        online = false;
                    }
                }
                else{
                    System.out.println("Enter a number BETWEEN 1 - 9.");
                    System.out.println();
                }
            }
            catch(InputMismatchException e){
                System.out.println("Invalid Input, please enter a number!");
                scanner.next();
                System.out.println();
            }


        }
        scanner.close();
        finance.saveTransactionDetails();
    }
}
