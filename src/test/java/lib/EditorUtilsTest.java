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

        assertEquals(EditorUtils.getSubstringMatchedCount(oneMatch, basicText, false), 0);
        assertEquals(EditorUtils.getSubstringMatchedCount(basicString, basicText, false), 2);
        assertEquals(EditorUtils.getSubstringMatchedCount(operatorString, operatorText, false), 1);
        assertEquals(EditorUtils.getSubstringMatchedCount(noMatch, basicText, false), 0);
        assertEquals(EditorUtils.getSubstringMatchedCount("WORLD", basicText, true), 0);

    }

    @Test
    void getIndexStartsOfSubstring() {

        String goGetter = "go getter go";

        List<Integer> testList = Arrays.asList(0, 3, 10);
        List<Integer> emptyList = new ArrayList<>();

        assertEquals(EditorUtils.getIndexStartsOfSubstring(goGetter, "g", false), testList);
        assertEquals(EditorUtils.getIndexStartsOfSubstring(goGetter, "G", true), emptyList);

        assert(EditorUtils.getIndexStartsOfSubstring(goGetter, "z", false).isEmpty());

    }

    @Test
    void replaceSpecificString() {

        String text = "hello croco, hello croco";

        try {
            assertEquals(EditorUtils.replaceSpecificString(text, "croco", "world", 0, false), "hello world, hello croco");
            assertEquals(EditorUtils.replaceSpecificString(text, "croco", "world", 1, false), "hello croco, hello world");

            Exception e = assertThrows
                    (Exception.class, () -> EditorUtils.replaceSpecificString(text, "Croco", "world", 1, true));
            assertEquals(e.getMessage(), "text doesn't contain 1th world");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}