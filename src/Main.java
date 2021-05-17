import java.sql.Array;
import java.util.Collection;
import java.util.Scanner;
import java.util.ArrayList;
import javaClasses.User;
import java.util.Collections;

public class Main {

    private static ArrayList<User> storage = new ArrayList<>();

    public static void main ( String[] args ) {

        showTitle();
        while ( true ) {
            int choice = askMenu();
            switch ( choice ) {
                case 1:
                    share();
                    break;
                case 2:
                    update();
                    break;
                case 3:
                    delete();
                    break;
                default:
                    close();
                    return;
            }
        }

    }

    public static void printSeparator( int length ) {
        System.out.print("+");
        for ( int i = 0 ; i < length-2 ; i++ ) System.out.print("=");
        System.out.println("+");
    }

    public static void showTitle() {
        System.out.println(" _______      ___             _______  __   __  _______  ______    _______ ");
        System.out.println("|  _    |    |   |           |       ||  | |  ||   _   ||    _ |  |       |");
        System.out.println("| |_|   |    |   |   ____    |  _____||  |_|  ||  |_|  ||   | ||  |    ___|");
        System.out.println("|       |    |   |  |____|   | |_____ |       ||       ||   |_||_ |   |___ ");
        System.out.println("|  _   |  ___|   |           |_____  ||       ||       ||    __  ||    ___|");
        System.out.println("| |_|   ||       |            _____| ||   _   ||   _   ||   |  | ||   |___ ");
        System.out.println("|_______||_______|           |_______||__| |__||__| |__||___|  |_||_______|");
    }

    public static int askMenu() {
        Scanner scan = new Scanner(System.in);
        int answer = -1;
        do {
            showMenu();
            System.out.print("Choice >> ");
            try {
                answer = scan.nextInt();
            } catch ( Exception e ) {
                answer = 0;
            }
        } while ( answer < 1 || answer > 5 );
        return answer;
    }

    public static void showMenu() {
        printSeparator(24);
        System.out.println("+Options               +");
        printSeparator(24);
        System.out.println("+1. Start Sharing      +");
        System.out.println("+2. Update Participant +");
        System.out.println("+3. Delete Participant +");
        System.out.println("+4. Close App          +");
        printSeparator(24);
    }

    public static void share() {

        Scanner scan = new Scanner(System.in);
        Integer shareNumber = -1;
        do {
            System.out.print("Input a number [0 - 100]: ");
            try {
                shareNumber = scan.nextInt();
                scan.nextLine();
            } catch ( Exception e ) {
                shareNumber = -1;
                System.out.println("Input must be numeric");
            }
        } while ( shareNumber < 0 || shareNumber > 100 );
        String username;
        boolean duplicate = true;
        do {
            duplicate = false;
            System.out.print("Could you give me your username [5 - 20 characters]? ");
            username = scan.nextLine();
            if ( !storage.isEmpty() ) {
                for ( User u : storage ) {
                    if ( u.getUsername().equals(username) ) {
                        duplicate = true;
                        break;
                    }
                }
            }
            if ( duplicate ) {
                System.out.println("username has already been taken!!");
            }
        } while ( duplicate || username.length() < 5 || username.length() > 20 );
        System.out.printf("You are the %d that joins the game\n", storage.size()+1);
        System.out.printf("Your share number : %d\n", shareNumber);
        System.out.printf("Your username : %s\n", username);
        User addUser = new User(shareNumber, username);
        storage.add(addUser);
    }

    public static void update() {
        Scanner scan = new Scanner(System.in);
        if ( !storage.isEmpty() ) {
            int choice = -1;
            do {
                displayData();
                System.out.printf("Which participant you would like to update [1 - %d][0 to exit]? ", storage.size());
                try {
                    choice = scan.nextInt();
                } catch ( Exception e ) {
                    System.out.println("Input must be numeric");
                    choice = -1;
                }
            } while ( choice < 0 || choice > storage.size() );
            if ( choice != 0 ) {
                Integer replacementNumber = -1;
                do {
                    System.out.print("Input a number [0 - 100]: ");
                    try {
                        replacementNumber = scan.nextInt();
                    } catch ( Exception e ) {
                        System.out.println("Input must be numeric");
                        replacementNumber = -1;
                    }
                } while ( replacementNumber < 0 || replacementNumber > 100 );
                storage.get(choice-1).setNumber(replacementNumber);
                System.out.println("Update Successful!");
            }
        }
    }

    public static void displayData() {
        printSeparator(41);
        System.out.printf("+ %-37s +\n", "Share List");
        printSeparator(41);
        for ( int i = 1 ; i <= storage.size() ; i++ ) {
            System.out.printf("| %-3d | %-20s | %-8d |\n", i, storage.get(i-1).getUsername(), storage.get(i-1).getNumber());
        }
        printSeparator(41);
    }

    public static void delete() {
        Scanner scan = new Scanner(System.in);
        if ( !storage.isEmpty() ) {
            int choice = -1;
            do {
                displayData();
                System.out.printf("Which participant you would like to delete [1 - %d][0 to exit]? ", storage.size());
                try {
                    choice = scan.nextInt();
                } catch ( Exception e ) {
                    System.out.println("Input must be numeric");
                    choice = -1;
                }
            } while ( choice < 0 || choice > storage.size() );
            if ( choice != 0 ) {
                storage.remove(choice-1);
                System.out.println("Participant successfully removed from event");
            }
        }
    }

    public static void close() {
        storage.sort((u1, u2) -> u1.getUsername().compareTo(u2.getUsername()));
        ArrayList<Integer> newNumber = new ArrayList<>();
        for ( User u : storage ) {
            newNumber.add(u.getNumber());
        }
        Collections.shuffle(newNumber);
        Collections.reverse(newNumber);
        System.out.println("Here's the completed share list");
        System.out.println("Remember, sharing is caring, happy sharing :D");
        printSeparator(50);
        System.out.printf("+ %-46s +\n", "Share List");
        printSeparator(50);
        System.out.printf("+ %-25s | %-8s | %-7s +\n", "Username", "Before", "After");
        printSeparator(50);
        for ( int i = 0 ; i < storage.size() ; i++ ) {
            System.out.printf("| %-25s | %-8s | %-7s |\n", storage.get(i).getUsername(), storage.get(i).getNumber(), newNumber.get(i));
        }
        printSeparator(50);
    }

}
