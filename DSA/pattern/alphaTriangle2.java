/* A
   B B
   C C C 
   D D D D */

import java.util.Scanner;

public class alphaTriangle2 {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of terms : ");
        int n = sc.nextInt();

        for(int i = 1; i<=n;i++){

            for(int j = 1;j<=i;j++){
                System.out.print(" "+ (char)(i + 64));
            }
            System.out.print("\n");
        }

    }
}