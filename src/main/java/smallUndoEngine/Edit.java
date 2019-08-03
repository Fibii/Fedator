package smallUndoEngine;

import java.util.Stack;

public class Edit implements IEdit {
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();
    private String text;

    Edit() {

    }

    /**
     * updates the current text to the previous one stored in the stack if the stack is not empty
     * @return true if the stack is not empty, false otherwise
     */
    @Override
    public boolean undo() {
        if (!undoStack.empty()) {
            if (text.trim().length() > 0) {
                redoStack.add(text.trim());
            }
            processText();
            if (!undoStack.empty()) {
                text = undoStack.pop();
            }
            return true;
        } else {
            System.out.println("undo stack is empty");
            return false;
        }
    }

    /**
     * updates the current text to the original one stored in the stack if the stack is not empty
     * @return true if the stack is not empty, false otherwise
     */
    @Override
    public boolean redo() {
        if (!redoStack.empty()) {
            text = redoStack.pop();
            return true;
        } else {
            System.out.println("redo stack is empty");
            return false;
        }
    }

    /**
     * processes the new added text, ex: "hi there boy" -> "hi there"
     */
    private void processText() {
        //remove the last word then add the text to the stack
        if (text.contains(" ")) {
            String txt = text.substring(0, text.lastIndexOf(" ")).trim();
            if (!undoStack.contains(txt)) {
                undoStack.add(txt);
            }
        } else {
            undoStack.add("");
        }
    }

    /**
     * updates the current text
     *
     * @param text the value of the new text
     **/
    void setText(String text) {
        this.text = text;
        processText();
    }

    /**
     * @return text the text to be returned
     */
    String getText() {
        return text;
    }

}
