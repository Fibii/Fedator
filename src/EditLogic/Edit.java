package EditLogic;

import java.util.Stack;

public class Edit implements IEdit {
    private Stack<String> undoStack = new Stack<>();
    private Stack<String> redoStack = new Stack<>();
    private String text;

     Edit(){

    }

    @Override
    public void undo() {
        processText();
       text = undoStack.pop();
    }

    @Override
    public void redo() {
        text = redoStack.pop();
    }

    /** returns the last word written */
    private String getLastWord(){
        if(text == null)
            throw new IllegalArgumentException("The text is null!!");
        //String str = "   hi     there      boi";
        String[] x = text.split("\\s+");
        String word = x[x.length-1];
        return word;
    }

    /** processes the new added text, ex: "hi there boy" -> "hi there"*/
    private void processText(){
        redoStack.add(text);
        //remove the last word then add the text to the stack
        String txt = text.replace(getLastWord(),"");
        undoStack.add(txt);
    }

    //sets the current text to the given parameter
     void setText(String text){
        this.text = text;
        processText();
    }

     String getText(){
        return text;
    }

    public static void main(String[] args) {


    }
}
