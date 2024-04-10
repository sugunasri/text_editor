package notepad;

import java.time.LocalDateTime;
import java.util.*;
import action.Action;

public class Notepad {
    Scanner scn = new Scanner(System.in);
    private int id = 1;
    private String[] notepad;
    private Stack<Action> undoAction;
    private Stack<Action> redoAction;
    private Queue<String> clipboard;
    private int capacity;

    public Notepad(int capacity) {
        this.capacity = capacity;
        this.notepad = new String[capacity];
        this.undoAction = new Stack<>();
        this.redoAction = new Stack<>();
        this.clipboard = new LinkedList<>();
    }

    public void display() {
        boolean isEmpty = true;
        for (String line : notepad) {
            if (line != null && !line.isEmpty()) {
                System.out.println(line);
                isEmpty = false;
            }
        }
        if (isEmpty) {
            System.out.println("The notepad is empty.");
        }
    }

    public void display(int startingline, int endingline) {
        if (startingline < 0 || startingline > endingline || endingline > this.capacity) {
            System.out.println("Unable to display text. Please check the input once more");
            return;
        }
        for (int i = startingline; i < endingline; i++) {
            if (notepad[i] != null && !notepad[i].isEmpty()) {
                System.out.println(notepad[i]);
            }
        }
    }

    public void insertLine(int linenumber, String text) {
        if (linenumber < 1 || linenumber > capacity) {
            System.out.println("Invalid line number");
            return;
        }
    
        if (notepad[linenumber - 1] != null && !notepad[linenumber - 1].isEmpty()) {
            System.out.println("There is already text at line " + linenumber);
            System.out.println("Do you want to insert a new line or replace the existing one?");
            System.out.println("1. Insert a new line");
            System.out.println("2. Replace the existing line");
    
            int choice = scn.nextInt();
            scn.nextLine(); // Consume newline character
            switch (choice) {
                case 1:
                    shiftLinesDown(linenumber);
                    notepad[linenumber - 1] = text;
                    break;
                case 2:
                    notepad[linenumber - 1] = text;
                    break;
                default:
                    System.out.println("Invalid choice. Inserting as a new line by default.");
                    shiftLinesDown(linenumber);
                    notepad[linenumber - 1] = text;
                    break;
            }
        } else {
            notepad[linenumber - 1] = text;
        }
    
        undoAction.push(new Action(id++, LocalDateTime.now(), true, linenumber, text));
    }
    
    public void insertLine(String text) {
        int nextAvailableLine = findNextAvailableLine();
        if (nextAvailableLine == -1) {
            System.out.println("Notepad limit reached. Unable to insert.");
            return;
        }
    
        notepad[nextAvailableLine] = text;
    
        undoAction.push(new Action(id++, LocalDateTime.now(), true, nextAvailableLine + 1, text));
    }
    

    private int findNextAvailableLine() {
        for (int i = 0; i < capacity; i++) {
            if (notepad[i] == null || notepad[i].isEmpty()) {
                return i;
            }
        }
        return -1; // Notepad is full
    }

    private void shiftLinesDown(int startingLine) {
        for (int i = capacity - 1; i >= startingLine; i--) {
            if (i + 1 < capacity) {
                notepad[i + 1] = notepad[i];
            }
        }
        notepad[startingLine] = ""; // Clear the line at the specified position to make space for the new line
    }

    public void delete(int linenumber) {
        if (linenumber > capacity) {
            System.out.println("no such row exists");
            return;
        }
        if (notepad[linenumber - 1] == null) {
            System.out.println("Nothing to delete");
            return;
        }

        undoAction.push(new Action(id++, LocalDateTime.now(), false, linenumber, notepad[linenumber - 1]));
        shiftLinesUp(linenumber);
    }

    private void shiftLinesUp(int startingLine) {
        for (int i = startingLine - 1; i < capacity - 1; i++) {
            notepad[i] = notepad[i + 1];
        }
        notepad[capacity - 1] = null;
    }

    public void delete(int startingline, int endingline) {
        if (startingline < 0 || startingline > endingline || endingline > capacity) {
            System.out.println("Unable to delete. Please check the inputs");
            return;
        }
        for (int i = startingline; i <= endingline; i++) {
            delete(i);
        }
    }

    public void copy(int startingline, int endingline) {
        if (startingline < 0 || startingline > endingline | endingline > this.capacity) {
            System.out.println("Unable to copy");
            return;
        }

        String copytext = " ";
        for (int i = startingline; i < endingline; i++) {
            copytext += (notepad[i] + "\n");
        }
        if (!copytext.isEmpty())
            clipboard.add(copytext);
    }

    public void paste(int linenumber) {
        if (this.clipboard.isEmpty()) {
            System.out.println("Nothing to paste");
            return;
        }
        String text = clipboard.peek();
        insertLine(linenumber, text);
    }

    public void undo() {
        if (this.undoAction.isEmpty()) {
            System.out.println("Nothing to undo");
            return;
        }

        Action action = this.undoAction.pop();

        delete(action.getLineNo());
    }

    public void redo() {
        if (this.redoAction.isEmpty()) {
            System.out.println("Nothing to redo");
            return;
        }

        Action action = redoAction.pop();
        insertLine(action.getLineNo(), action.gettext());
    }
}
