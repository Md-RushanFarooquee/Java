// 2 4 8 16......

import java.util.*;

public class series2 {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        int x = 1;
        System.out.print("Enter a number : ");
        int n = sc.nextInt();

        for (int i = 1; i<=n; i++){
            System.out.print(x+" ");
            x = x * 2;

        }
    }
}
