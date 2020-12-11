package ua.koss.task3;

import java.math.BigInteger;

public class Main {

    public static void main(String[] args) {
        variant1();
        variant2();
        variant3();
        variant4();
    }

    static void variant1(){

        BigInteger startNumber = BigInteger.ONE;
        for (int i = 2; i <= 100; i++) {
            startNumber = startNumber.multiply(BigInteger.valueOf(i));
        }

        int sum = startNumber
                .toString() //number value to string
                .chars() //string to char[]
                .map(Character::getNumericValue) // convert all values to int[]
                .sum(); //get sum of all values

        System.out.println(sum);

    }

    static void variant2(){

        BigInteger startNumber = BigInteger.ONE;
        for (int i = 2; i <= 100; i++) {
            startNumber = startNumber.multiply(BigInteger.valueOf(i));
        }

        String s = startNumber.toString();

        int sum = 0;
        for (int i = 1; i < 10; i++) {
            sum += (count(s,String.valueOf(i)) * i);
        }

        System.out.println(sum);
    }

    public static int count(String str, String target) {
        return (str.length() - str.replace(target, "").length()) / target.length();
    }

    //almost the same with v1, but without stream()
    static void variant3(){
        BigInteger startNumber = BigInteger.ONE;

        for (int i = 2; i <= 100; i++) {
            startNumber = startNumber.multiply(BigInteger.valueOf(i));
        }

        String s = startNumber.toString();

        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += Integer.parseInt(s.charAt(i) + ""); // cast all string's symbols to integer
        }

        System.out.println(sum);
    }

    static void variant4(){
        BigInteger startNumber = BigInteger.ONE;
        for (int i = 2; i <= 100; i++) {
            startNumber = startNumber.multiply(BigInteger.valueOf(i));
        }

        char[] charArray = startNumber.toString().toCharArray();
        int sum = 0;

        for (Character c:
                charArray) {
            sum += Character.getNumericValue(c);
        }
        System.out.println(sum);
    }
}
