/*       11
      11 10 11
    11 10 9 10 11
  11 10 9 8 9 10 11*/


import java.util.Scanner;

public class patternclass {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of terms : ");
        int n = sc.nextInt();
        System.out.print(" ");

        for (int i = 1; i<=n; i++){

            int x = 11;

            for(int j = 1; j <= n - i; j++){
                System.out.print("  ");
            }
            for(int k = 1; k < i;k++){
                System.out.print(x+ " ");
                x = x - 1;
            }
            
            for(int l = 1;l <= i; l++){
                System.out.print(x+ " ");
                x = x + 1;
            }
            
            System.out.print("\n");
        }

    }    
}
