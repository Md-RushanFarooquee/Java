import java.util.Scanner;

public class oddeven{

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number: ");
        int x = sc.nextInt();
        
        if (x % 2 == 0 ){
            System.out.println("It is Even");
        }
        else{
            System.out.println("It is odd");
        }
        sc.close();
    }

}