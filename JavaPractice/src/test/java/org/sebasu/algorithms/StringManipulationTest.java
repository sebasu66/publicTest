package org.sebasu.algorithms;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class StringManipulationTest {

    @Test
    public void isPalindrome() {
    }

    @Test
    public void isPermutation() {
    }

    @Test
    public void replaceSpaces() {
    }

    @Test
    public void noBsBeforeAs() {
        String str1 = ""; //true
        String str2 = "bb"; //true
        String str3 = "bba"; //false
        String str4 = "aaaaa"; //true
        String str5 = "aaaaaaaaaaaaaaaaaaabaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"; //false
        String str6 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbbbbbbbbbbbb"; //true

        System.out.println("test1: noBsBeforeAs() for input: " + str1 );
        assertTrue(StringManipulation.noBsBeforeAs(str1));
        System.out.println("test2: noBsBeforeAs() for input: " + str2 );
        assertTrue(StringManipulation.noBsBeforeAs(str2));
        System.out.println("test3: noBsBeforeAs() for input: " + str3 );
        assertFalse(StringManipulation.noBsBeforeAs(str3));
        System.out.println("test4: noBsBeforeAs() for input: " + str4 );
        assertTrue(StringManipulation.noBsBeforeAs(str4));
        System.out.println("test5: noBsBeforeAs() for input: " + str5 );
        assertFalse(StringManipulation.noBsBeforeAs(str5));
        System.out.println("test6: noBsBeforeAs() for input: " + str6 );
        assertTrue(StringManipulation.noBsBeforeAs(str6));

    }
}