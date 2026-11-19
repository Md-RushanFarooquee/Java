import java.util.Scanner;

public class VasyaTheHipster {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int diffDays = 0;
        while(a > 0 && b > 0){
            diffDays++;
            a--;
            b--;
        }
        int sameDay = a / 2;
        sameDay += b / 2;
        System.out.println(diffDays + " " + sameDay);
    }
}
