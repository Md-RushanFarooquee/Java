
import java.util.Scanner;

public class alphaTriangle3 {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a number :  ");
        int n = sc.nextInt();

        for(int i = 1; i <=n; i++){
            
            for(int j = i; j< n;j++){
                System.out.print("  ");
            }
            for(int k = 1; k <= i ; k++){
                System.out.printf("%c ",(i+64));
            }
            System.out.println();
        }
    }
}
