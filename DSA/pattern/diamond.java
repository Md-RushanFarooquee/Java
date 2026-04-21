
import java.util.Scanner;

public class diamond {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter n : ");
        int n = sc.nextInt();

        int nsp = n-1, nst = 1;   //nsp = number of spaces, nst = number of stars
        for(int i = 1;i<= n; i++){

            for(int j = 1; j<=nsp; j++){
                System.out.print("  ");
            }
            for(int k = 1; k<=nst;k++){
                System.out.print("* ");
            }
            nsp--;
            nst += 2;
            System.out.println();
        }
        nsp = 1;
        nst = 2*n - 3;
        for(int i = 1;i< n; i++){

            for(int j = 1; j<=nsp; j++){
                System.out.print("  ");
            }
            for(int k = 1; k<=nst;k++){
                System.out.print("* ");
            }
            nsp++;
            nst -= 2;
            System.out.println();
        }
    }
}
