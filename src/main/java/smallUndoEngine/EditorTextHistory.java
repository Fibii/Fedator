package smallUndoEngine;

/** acts as a connector between the MainController class and the Edit class*/
public class EditorTextHistory implements IEdit {
    private Edit edit = new Edit();

    public EditorTextHistory(String text){
        edit.setText(text);
    }

    public EditorTextHistory(){

    }

    public String getText(){
        return edit.getText();
    }

    public void update(String newText){
        edit.setText(newText);
    }

    public boolean undo(){
        return edit.undo();
    }
    public boolean redo(){
        return edit.redo();
    }

}
