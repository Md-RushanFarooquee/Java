package Methods;

import java.util.Scanner;

public class maxOfTwo {

    public static int Maxoftwo(int a, int b){

        if(a > b) return a;
        else return  b;
    }



    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a & b: ");
        int a = sc.nextInt();
        int b = sc.nextInt();

        System.out.print("Max = "+ Maxoftwo(a, b));
    }
}
