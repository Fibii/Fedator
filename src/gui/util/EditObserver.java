package gui.util;

import java.util.Stack;

/** store the text in a stack for the purpose of undoing/redoing edits
 * to the text, will store the input in undo stack, when the program
 * calls the undo method, pop the lastest string from the undo stack, write
 * it in the textArea and add it to the redo stack*/
public class EditObserver {
    private Stack<String> undo;
    private Stack<String> redo;

    public EditObserver(){
        undo = new Stack<>();
        redo = new Stack<>();
    }

    public void textChanged(String s){
       // System.out.println(s);
        if(!s.equals("") || !s.equals("\n"))
            undo.push(s);
    }

    public String undo(){
        if(!undo.empty()){
            undo.pop();
            String tmp = undo.pop();
            redo.push(tmp);
            System.out.println("tmp: " + tmp);
            return tmp;
        }
        return "";
    }
}
