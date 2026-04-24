/* TAKING MAXIMUM VALUE OF NUMBER INSTEAD OF NUMBER OF TERMS */


import java.util.Scanner;

public class floydstriangle {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number : ");
        int n = sc.nextInt();

        int x = 1;
        for(int i = 1; x <= n;i++){
            for(int j = 1;j <= i && x <= n;j++){
                System.out.print(x+" ");
                x++;
            }
            System.out.println();
        }

    }
}
