
import java.util.Scanner;

public class rhombus {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of terms : ");
        int n = sc.nextInt();

        for(int i = 1; i<=n;i++){
        
            for(int j = i; j<n;j++){
                System.out.print("  ");
            }
            for(int k = 1; k<=n;k++){
                System.out.print("* ");
            }
            

            System.out.println();
        }

    }
}
