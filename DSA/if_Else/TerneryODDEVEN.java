// series = 2 5 8 11......
import java.util.Scanner;

class TerneryODDEVEN{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Number : ");
        int x = sc.nextInt();

        System.out.print((x % 2 == 0) ? "is Even" : "is Odd");
        sc.close();
    }

}