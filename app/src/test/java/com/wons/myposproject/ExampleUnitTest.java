package com.wons.myposproject;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);

        int a = 7;

        switch (a) {
            case 1: {
                System.out.println("a");
                break;

            }
            case 2: {
                System.out.println("B");
                break;

            }
        }
        switch (a) {
            case 7: {
                System.out.println("a1");
                break;
            }
            case 2: {
                System.out.println("B");
                break;

            }
        }
        switch (a) {
            case 7: {
                System.out.println("a2");
                break;

            }
            case 2: {
                System.out.println("B");
                break;

            }
        }
    }
}