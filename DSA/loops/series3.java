// take n as input from the user and print the following 
// let n  = 10, series =   1 10 2 9 3 8 4 7 5 6 

import java.util.Scanner;

public class series3{
    public static void main(String[] args) {
     
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of terms : ");
        int n = sc.nextInt();
        int x = 1;

        for(int i = n;i>=1;i--){
            System.out.print(x + " ");
            System.out.print(i + "  ");
            x = x + 1;
        }
    }
}