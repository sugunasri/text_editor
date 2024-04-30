package notepad;

import java.time.LocalDateTime;  
// import java.time.format.DateTimeFormatter;  
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
        if (startingline < 0 || startingline >= endingline || endingline > this.capacity) {
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
        if (findNextAvailableLine() == -1) {
            delete(capacity);
        }
        shiftLinesDown(linenumber);
        notepad[linenumber - 1] = text;
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
        return -1;
    }

    private void shiftLinesDown(int startingLine) {
        for (int i = capacity - 1; i >= startingLine - 1; i--) {
            if (i < capacity - 1) {
                notepad[i + 1] = notepad[i];
            }
        }
    }

    public void delete(int linenumber) {
        if (linenumber < 1 || linenumber > capacity) {
            System.out.println("Invalid line number");
            return;
        }
        if (notepad[linenumber - 1] == null || notepad[linenumber - 1].isEmpty()) {
            System.out.println("Nothing to delete at line " + linenumber);
            return;
        }
        undoAction.push(new Action(id++, LocalDateTime.now(), false, linenumber, notepad[linenumber - 1]));
        shiftLinesUp(linenumber);

        notepad[capacity - 1] = null;
    }

    private void shiftLinesUp(int startingLine) {
        for (int i = startingLine; i < capacity - 1; i++) {
            notepad[i - 1] = notepad[i];
        }
    }

    public void delete(int startingline, int endingline) {
        if (startingline < 1 || startingline > endingline || endingline > capacity) {
            System.out.println("Unable to delete. Please check the inputs");
            return;
        }
        for (int i = startingline - 1; i < endingline; i++) {
            delete(startingline);
        }
    }

    public void copy(int startingline, int endingline) {
        if (startingline < 0 || startingline > endingline || endingline > this.capacity) {
            System.out.println("Unable to copy");
            return;
        }
    
        StringBuilder copytext = new StringBuilder();
        for (int i = startingline; i < endingline; i++) {
            if (notepad[i] != null && !notepad[i].isEmpty()) {
                copytext.append(notepad[i]).append("\n");
            }
        }
    
        if (clipboard.size() >= 6) { //considering clipboard size is 6
            clipboard.poll(); // Remove the oldest entry (first stored data)
        }
    
        clipboard.add(copytext.toString());
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

        if (action.isAddition()) {
            delete(action.getLineNo());
        } else {
            insertLine(action.getLineNo(), action.gettext());
        }
    }

    public void redo() {
        if (this.redoAction.isEmpty()) {
            System.out.println("Nothing to redo");
            return;
        }
    
        Action action = redoAction.pop();
    
        if (action.isAddition()) {
            insertLine(action.getLineNo(), action.gettext());
        } else {
            delete(action.getLineNo());
        }
    
        undoAction.push(action);
    }
    
}