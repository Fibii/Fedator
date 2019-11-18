package smallUndoEngine;

import java.util.ArrayList;
import java.util.List;

public class Edit implements IEdit {
    private List<String> undoStack = new ArrayList<>();
    private List<String> redoStack = new ArrayList<>();
    private String text;

    Edit() {
        text = "";
    }

    /**
     * updates the current text to the previous one stored in the stack if the stack is not empty
     *
     * @return true if the stack is not empty, false otherwise
     */
    @Override
    public void undo() {

        if (undoStack.isEmpty()) {
            System.out.println("undo stack is empty");
            return;
        } else if (undoStack.size() == 1) {
            text = "";
            redoStack.add(undoStack.get(undoStack.size() - 1));
            return;
        } else {
            text = undoStack.remove(undoStack.size() - 2);
            redoStack.add(undoStack.remove(undoStack.size() - 1));
        }

    }

    /**
     * updates the current text to the original one stored in the stack if the stack is not empty
     *
     * @return true if the stack is not empty, false otherwise
     */
    @Override
    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("redo stack is empty");
        } else {
            text = redoStack.remove(redoStack.size() - 1);
        }
    }

    /**
     * updates the current text
     *
     * @param text the value of the new text
     **/
    void setText(String text) {

        if (undoStack.contains(text) || text.isEmpty()) {
            return;
        }

        if (undoStack.isEmpty()) {
            System.out.println("Added");
            undoStack.add(text);
            return;
        }

        if (text.endsWith(" ")) {
            undoStack.add(text);
            return;
        }

        String[] textStringArray;
        if (text.contains(" ")) {
            textStringArray = text.split(" ");
        } else {
            textStringArray = new String[]{text};
        }


        boolean shouldReplace = true;

        String[] stringStackArray = undoStack.get(undoStack.size() - 1).split(" ");
        String lastWordInStack = stringStackArray[stringStackArray.length - 1];
        String lastWordInText = textStringArray[textStringArray.length - 1];

        // case we have new word with space before it, add it directly
        if (stringStackArray.length < textStringArray.length) {
            undoStack.add(text);
            return;
        }


        // if the phrases don't have different of length 1, don't even compare
        if (undoStack.get(undoStack.size() - 1).length() != text.length() - 1) {
            shouldReplace = false;
        }
        if (shouldReplace && lastWordInStack.length() == lastWordInText.length() - 1) {
            for (int j = lastWordInStack.length() - 1; j >= 0; j--) {
                if (lastWordInStack.charAt(j) != lastWordInText.charAt(j)) {
                    shouldReplace = false;
                    break;
                }
            }
        }


        if (!shouldReplace) {
            undoStack.add(text);
        } else {
            undoStack.remove(undoStack.size() - 1);
            undoStack.add(text);
        }

    }


    /**
     * @return text the text to be returned
     */
    String getText() {
        return text;
    }

}
