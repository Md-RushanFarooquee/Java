import java.util.Scanner;

public class series {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter the number of terms : ");
        int n = sc.nextInt();

        int x = 2;

        for (int i = 1; i <=n;i++){
            System.out.print(x +" ");
            x = x + 3;
        }
    }
}
