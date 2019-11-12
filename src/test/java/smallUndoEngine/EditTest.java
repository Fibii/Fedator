package smallUndoEngine;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EditTest {

    @Test
    void undoWithOneWord() {
        Edit edit = new Edit();

        String[] words = new String[]{
                "h",
                "he",
                "hel",
                "hell",
                "hello"
        };

        for (String word : words){
            edit.setText(word);
        }

        edit.undo();
        assertEquals(edit.getText(), "");
    }

    @Test
    void undoWithTwoWords(){
        Edit edit = new Edit();

        String[] words = new String[]{
                "h",
                "he",
                "hel",
                "hell",
                "hello",
                "hello ",
                "hello w",
                "hello wo",
                "hello wor",
                "hello worl",
                "hello world"
        };

        for (String word : words){
            edit.setText(word);
        }

        edit.undo();
        assertEquals(edit.getText(), "hello ");

    }

    @Test
    void undoOneWordWithSpaceEdgeCase(){
        Edit edit = new Edit();

        String[] words = new String[]{
                "h",
                "he",
                "hel",
                "hell",
                "hello",
                "hello ",
        };

        for (String word : words){
            edit.setText(word);
        }

        edit.undo();
        assertEquals(edit.getText(), "hello");

    }

    @Test
    void multipleUndo() {
        Edit edit = new Edit();

        String[] words = new String[]{
                "h",
                "he",
                "hel",
                "hell",
                "hello",
                "hello ",
                "hello w",
                "hello wo",
                "hello wor",
                "hello worl",
                "hello world java"
        };

        for (String word : words){
            edit.setText(word);
        }

        edit.undo();
        edit.undo();
        edit.undo();
        edit.undo();
        assertEquals(edit.getText(), "hello");
    }

    @Test
    void redoEmpty() {
        Edit edit = new Edit();
        edit.redo();
        assertEquals(edit.getText(), "");
    }

    @Test
    void redoOneWordWithSpaceEdgeCase() {
        Edit edit = new Edit();

        String[] words = new String[]{
                "h",
                "he",
                "hel",
                "hell",
                "hello",
                "hello ",
        };

        for (String word : words){
            edit.setText(word);
        }

        edit.undo(); //hello
        edit.redo();
        assertEquals(edit.getText(), "hello ");
    }

    @Test
    void redoTwoWords() {
        Edit edit = new Edit();

        String[] words = new String[]{
                "h",
                "he",
                "hel",
                "hell",
                "hello",
                "hello ",
                "hello w",
                "hello wo",
                "hello wor",
                "hello worl",
                "hello world"
        };

        for (String word : words){
            edit.setText(word);
        }

        edit.undo();
        edit.redo();
        assertEquals(edit.getText(), "hello world");
    }

    @Test
    void multipleRedo() {
        Edit edit = new Edit();

        String[] words = new String[]{
                "h",
                "he",
                "hel",
                "hell",
                "hello",
                "hello ",
                "hello w",
                "hello wo",
                "hello wor",
                "hello worl",
                "hello world java"
        };

        for (String word : words){
            edit.setText(word);
        }

        edit.undo();
        edit.undo();
        edit.undo();
        edit.undo();
        edit.redo();
        edit.redo();
        edit.redo();
        edit.redo();
        assertEquals(edit.getText(), "hello world java");
    }
}