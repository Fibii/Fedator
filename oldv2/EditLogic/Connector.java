package oldv2.EditLogic;

/** acts as a connector between the MainController class and the Edit class*/
public class Connector implements IEdit {
    private Edit edit = new Edit();

    public Connector(String text){
        edit.setText(text);
    }

    public Connector(){

    }

    public String getText(){
        return edit.getText();
    }

    public void update(String newText){
        edit.setText(newText);
    }

    public void undo(){
        edit.undo();
    }
    public void redo(){
        edit.redo();
    }

}
