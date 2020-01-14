package databaseApp;

import java.util.Scanner;

public class Main {

    public static String option;
     private static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) throws Exception {



     Database database = new Database();
     database.getConnection();
// database.updateSlovakCity();
//        database.deleteSlovakCity();
      // database.insertSLovakCity();


selectOption();


    }

    public static void printMenu() {

        System.out.println("Select action to do with application:");
        System.out.println("1 - Return list of the Slovak cities");
        System.out.println("2 - Insert city into slovak cities");
        System.out.println("3 - Update Slovak City");
        System.out.println("4 - Delete Slovak City");
        System.out.println("5 - Exit Application");
        System.out.println("Enter option: ");
       option = scanner.nextLine();
    }

    public static void selectOption() throws Exception {
        Database database = new Database();

try {
    System.out.println("Welcome to my application");
    printMenu();



     switch (option) {
         case "1":
             database.selectSlovakCities();
             break;
         case "2":
             database.insertSLovakCity();
             break;
         case "3":
             database.updateSlovakCity();
             break;
         case "4":
             database.deleteSlovakCity();
             break;
         case "5":
             System.out.println("Exiting...");
             return;

         default:
             System.out.println("Wrong input");
             break;
     }

}catch (Exception e){
    e.printStackTrace();
}

    }
}
