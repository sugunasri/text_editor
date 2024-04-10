import java.util.Scanner;
import notepad.Notepad;

public class App {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        Notepad notepad = new Notepad(100);
        do {
            System.out.println("Choose your option:");
            System.out.println("1. Display the text");
            System.out.println("2. Display the text from given lines");
            System.out.println("3. Insert the text at a specific line number");
            System.out.println("4. Insert the text at the next available line");
            System.out.println("5. Delete a line");
            System.out.println("6. Delete lines from given range");
            System.out.println("7. Copy text from given range");
            System.out.println("8. Paste text at a line");
            System.out.println("9. Undo your action");
            System.out.println("10. Redo your action");
            System.out.println("11. Exit");

            System.out.println("Enter your choice");
            int choice = scn.nextInt();

            switch (choice) {
                case 1:
                    notepad.display();
                    break;
                case 2:
                    System.out.println("Enter the starting line and ending line value");
                    int startingLine = scn.nextInt();
                    int endingLine = scn.nextInt();
                    notepad.display(startingLine - 1, endingLine);
                    break;
                case 3:
                    scn.nextLine(); // Consume newline character
                    System.out.println("Enter the line number where you want to insert the text");
                    int lineNumber = scn.nextInt();
                    scn.nextLine(); // Consume newline character
                    System.out.println("Enter the text");
                    String text1 = scn.nextLine();
                    notepad.insertLine(lineNumber, text1);
                    break;
                case 4:
                    scn.nextLine(); // Consume newline character
                    System.out.println("Enter the text to insert");
                    String text2 = scn.nextLine();
                    notepad.insertLine(text2);
                    break;
                case 5:
                    System.out.println("Enter the line number to delete");
                    int lineToDelete = scn.nextInt();
                    notepad.delete(lineToDelete);
                    break;
                case 6:
                    System.out.println("Enter the starting line and ending line value to delete");
                    int startDelete = scn.nextInt();
                    int endDelete = scn.nextInt();
                    notepad.delete(startDelete, endDelete);
                    break;
                case 7:
                    System.out.println("Enter the starting line and ending line value to copy");
                    int startCopy = scn.nextInt();
                    int endCopy = scn.nextInt();
                    notepad.copy(startCopy - 1, endCopy);
                    break;
                case 8:
                    System.out.println("Enter the line number to paste");
                    int lineToPaste = scn.nextInt();
                    notepad.paste(lineToPaste - 1);
                    break;
                case 9:
                    notepad.undo();
                    break;
                case 10:
                    notepad.redo();
                    break;
                case 11:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        } while (true);
    }
}
