package user_interface;

import java.util.Scanner;

/*
    provides a command line user interface for input/output
 */
public class RecipeAppShell {
    final static String GREETING = "Hello! Welcome to recipe book\n" +
            "Login (a)\n" +
            "Forgot my password (b)\n" +
            "Create an account (c)\n" +
            "Settings (d)";
    public static void main(String[] args) {
        boolean run = true;
        System.out.println(GREETING);
        while(run){
            //main loop in which tha program will run
            Scanner scan = new Scanner(System.in);
            String val = scan.nextLine();
            System.out.println(val);
            // input validation, in case we need it
            if(val.equals("exit")){
                run = false;
            }


        }
    }
}
