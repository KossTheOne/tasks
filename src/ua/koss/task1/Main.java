package ua.koss.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    public static void addParen(ArrayList list, int leftRem, int rightRem, char[] str, int index) {
        if (leftRem < 0 || rightRem < leftRem) return; // Wrong state

        if (leftRem == 0 && rightRem == 0) { /* no more Rem */
            list.add(String.copyValueOf(str));
        } else {
            str[index] = '('; /* Add left Rem, if at least one left. */
            addParen(list, leftRem - 1, rightRem, str, index + 1);

            str[index] = ')'; /* Add right Rem, if expression is correct */
            addParen(list, leftRem, rightRem - 1, str, index + 1);
        }
    }

    public static ArrayList generateParens(int count) {
        char[] str = new char[count*2];
        ArrayList list = new ArrayList();
        addParen(list, count, count, str, 0);
        return list;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int totalRems = Integer.parseInt(reader.readLine());
        ArrayList list = generateParens(totalRems);
        //list.forEach(System.out::println); //print all possible variants
        System.out.println(list.size());
    }
}

// https://github.com/KossTheOne/tasks.git
