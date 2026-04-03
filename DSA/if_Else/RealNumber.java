// to take real number and check if it is an integer or not
import java.util.*;

public class RealNumber {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Number : ");
        double n = sc.nextDouble();

        int x = (int)n;

        if (n - x > 0) {
            System.out.print("Not an integer");
        }
        else {
            System.out.print("Is an integer");
        }
        sc.close();
    }
}
