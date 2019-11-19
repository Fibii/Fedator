package lib;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EditorUtilsTest {

    @Test
    void getSubstringMatchedCount() {

        String noMatch = "";

        String oneMatch = "to";

        String basicString = "hello world";
        String basicText = "hello world, hello world again";

        String operatorString = "c++";
        String operatorText = "c++ is a \n programming language";

        assertEquals(EditorUtils.getSubstringMatchedCount(oneMatch, basicText), 0);
        assertEquals(EditorUtils.getSubstringMatchedCount(basicString, basicText), 2);
        assertEquals(EditorUtils.getSubstringMatchedCount(operatorString, operatorText), 1);
        assertEquals(EditorUtils.getSubstringMatchedCount(noMatch, basicText), 0);
    }

    @Test
    void getIndexStartsOfSubstring() {

        String goGetter = "go getter go";

        List<Integer> testList = Arrays.asList(0, 3, 10);

        assertEquals(EditorUtils.getIndexStartsOfSubstring(goGetter, "g"), testList);
        assert(EditorUtils.getIndexStartsOfSubstring(goGetter, "z").isEmpty());

    }

    @Test
    void replaceSpecificString() {

        String text = "hello croco, hello croco";

        try {
            assertEquals(EditorUtils.replaceSpecificString(text, "croco", "world", 0), "hello world, hello croco");
            assertEquals(EditorUtils.replaceSpecificString(text, "croco", "world", 1), "hello croco, hello world");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}