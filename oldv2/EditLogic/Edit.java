package oldv2.EditLogic;

import java.util.Stack;

public class Edit implements IEdit {
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();
    private String text;

     Edit(){

     }

    @Override
    public void undo() {
         if (!undoStack.empty()){
             if(text.trim().length() > 0){
                 redoStack.add(text.trim());
             }
             processText();
             if(!undoStack.empty()){
                 text = undoStack.pop();
             }
         } else {
             System.out.println("undo stack is empty");
         }
    }

    @Override
    public void redo() {
        if(!redoStack.empty()){
            text = redoStack.pop();
        } else {
            System.out.println("redo stack is empty");
        }
    }

    /** processes the new added text, ex: "hi there boy" -> "hi there"*/
    private void processText(){
        //remove the last word then add the text to the stack
        if(text.contains(" ")){
            String txt = text.substring(0,text.lastIndexOf(" ")).trim();
            if(!undoStack.contains(txt)){
                undoStack.add(txt);
            }
        } else {
            undoStack.add("");
        }
    }

    //sets the current text to the given parameter
     void setText(String text){
        this.text = text;
        processText();
    }

     String getText(){
        return text;
    }

}
