import java.util.Scanner;

public class numTriangle2 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of terms : ");
        int n = sc.nextInt();

        for(int i = n; i>0;i--){
            int num = n;

            for(int j = 1;j<=i;j++){
                System.out.print(num+" ");
                num--;
            }
            System.out.print("\n");
        }
    }
}
