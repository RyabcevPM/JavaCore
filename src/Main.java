import base.sort.Sorter;

public class Main {


    static void printFibonacci(int a, int b, int count) {
        if (count == 1) return;
        if (a == b) System.out.print(a);
        System.out.print(" " + b);
        printFibonacci(b, a + b, count - 1);
    }

    static boolean CheckF1(long val) {
        long temp = val;
        long palindrome = 0;
        while (temp > 0) {
            palindrome *= 10;
            palindrome += temp % 10;
            temp /= 10;
        }
        return palindrome == val;
    }

    public static String notString(String str) {
        if (str.trim().matches("^not(.*)")) return str;
        else return "not " + str;
    }


    public static void main(String[] args) {
//        Example collectionExample = new Example(100_000);
//        collectionExample.run();

//        testBinarySearch(10_000_000);
//        Searcher.testBinarySearch(1_000_000);
//        System.out.println(notString("not dddbet"));
        Sorter.testAllSort(1000_000);
//        Sorter.testAllSort(10);



        //printFibonacci(1, 1, 8);
    }

}
