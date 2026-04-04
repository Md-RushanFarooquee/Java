import java.util.*;
public class prime {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int n = sc.nextInt();
        boolean flag = true;

        for(int i = 2;i<=Math.sqrt(n);i++){

            if (n % i == 0) {
                flag = false;
                break;
            }
        }
        if(n==1) System.out.println("Neither prime nor Composite");
        else if (flag ==false) System.out.println("Not prime");
        else System.out.println("is Prime");
    }
    
}
