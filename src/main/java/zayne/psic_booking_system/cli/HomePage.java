package zayne.psic_booking_system.cli;

import java.util.Scanner;

public class HomePage extends App {
    public HomePage() {
        super();
        clearConsole();
        displayIntroMsg();
    }

    @Override
    public void displayIntroMsg() {
        var welcomeMsg = "Welcome to PSIC booking system.";
        System.out.println(welcomeMsg);
        System.out.println("===============");
        System.out.println("A. Patient Subsystem.");
        System.out.println("B. Physician Subsystem.");
        System.out.println("C. Report.");
        System.out.println("Z. Exit.");
        System.out.println("===============");

        var choice = "";
        var invalidInput = true;

        while (invalidInput) {
            System.out.print("Select the system (and press `Enter`): ");
            choice = new Scanner(System.in).nextLine().strip().substring(0, 1).toLowerCase();
            switch (choice) {
            case "a":
                invalidInput = false;
                new PatientSubsystem();
                break;
            case "b":
                invalidInput = false;
                new PhysicianSubsystem();
                break;
            case "c":
                invalidInput = false;
                new ReportSubsystem();
                break;
            case "z":
                exit();
                break;
            default:
                System.out.print("!Invalid Input! - ");
                break;
            }
        }
    }

    @Override
    public void handleUserInput() {

    }
}
