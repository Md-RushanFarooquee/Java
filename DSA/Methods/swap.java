package Methods;

import java.util.Scanner;

public class swap {

    public static void swapNum(int x , int y){

        int temp = x;
        x = y;
        y = temp;
    }
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        System.out.print("a : ");
        int a = sc.nextInt();
        System.out.print("b : ");
        int b = sc.nextInt();

        swapNum(a, b);

        System.out.println("a : "+ a + "\nb : " + b);
    }
}
