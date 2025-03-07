import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class Menu {
    static Scanner scanner = new Scanner(System.in);

    /**
     * This method displays the first menu, which asks the user to decide where to go next
     *
     * @return The choice of which screen to navigate to
     */
    static int display() {
        int response = 0;

        System.out.println("""
                Your options are:
                
                * Input 1 to go to employee login
                * Input 2 to go to admin login
                * Input 3 to exit program
                """);
        System.out.print("Please input your choice: ");

        if (scanner.hasNextInt()) {
            return scanner.nextInt();
        } else {
            System.out.println("Not a valid option");
            return 0;
        }
    }

    /**
     * This method navigates to whichever screen was passed in through the screen parameter
     *
     * @param screen The screen to navigate to
     * @return true if the program should continue to run, false if the program should terminate
     */
    static boolean navigate(int screen) {
        return switch (screen) {
            case 1 -> employeeLogin();
            case 2 -> adminLogin();
            case 3 -> false;
            default -> {
                System.out.println("Not a valid option");
                yield true;
            }
        };
    }

    /**
     * This method acts as the employee login screen
     *
     * @return true so that the program continues to run
     */
    static boolean employeeLogin() {
        System.out.println("You've reached the employee login screen");
        System.out.print("Please Input your username: ");
        String uName = scanner.nextLine();
        String savedHash = file.search(uName);
        return true;
    }

    /**
     * This method acts as a placeholder for the admin login screen
     *
     * @return true so that the program continues to run
     */
    static boolean adminLogin() {
        System.out.println("You've reached the admin login screen placeholder");
        return true;
    }
}

class file {
    static String filename = "users.txt";

    /**
     * This method adds the given data to the file
     *
     * @param data The data to append to the file
     * @return the message to print for success or failure
     */
    static String write(String data) {
        String message = "";
        try {
            FileWriter myWriter = new FileWriter(filename, true);
            myWriter.write(data);
            myWriter.close();
            message ="Successfully wrote to the file.";
        } catch (IOException e) {
            message = "An error occurred.";
            e.printStackTrace();
        }
        return message;
    }

    /**
     * This method reads data from the file
     */
    static String read() {
        String fileData = "";
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                fileData = fileData + data;
            }
            myReader.close();
            return fileData;
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return "error";
        }
    }

    /**
     * This method searches the file to find a given username, and returns that users password hash
     *
     * @param user The user to search for
     * @return The found users password hash, or an empty string if that user doesn't exist
     */
    static String search(String user) {
        String hash = "";
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (user.equals(data)) {
                    hash = myReader.nextLine();
                    myReader.close();
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return hash;
    }

}

class MD5 {
    static String getMd5(String input) {
        try {
            // Static getInstance method is called with hashing MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            // digest() method is called to calculate message digest
            // of an input digest() return array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Main {
    public static void main(String[] args) {
        boolean run = true;

        System.out.println(file.read());

        while (run) {
            run = Menu.navigate(Menu.display());
        }
    }
}