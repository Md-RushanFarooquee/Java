import java.util.Scanner;

public class BeautifulYear {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int y = sc.nextInt();
        System.out.println(checkYear(y));
        }
    public static int checkYear(int y) {
        while(true){
        y++;

        int a = y % 10;
        int b = (y / 10) % 10;
        int c = (y / 100) % 10;
        int d = (y / 1000) % 10;
         if (a != b && a != c && a != d &&
                b != c && b != d &&
                c != d) {
                return y;
            }
        }
    }
}
