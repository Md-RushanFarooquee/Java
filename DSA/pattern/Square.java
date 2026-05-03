import java.util.Scanner;

public class Square {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter length of square : ");
        int n = sc.nextInt();

        for(int i = 1;i<=n;i++){
            if(i == 1 || i == n){
                for(int j = 0; j<n;j++){
                    System.out.print("* ");
                }
            }
            // if(i >1 && i < n)
            else{
                System.out.print("* ");
                for(int k = 0; k<n-2;k++){
                    System.out.print("  ");
                }
                System.out.print("* ");
            }
         System.out.println();
        }
    }
}
