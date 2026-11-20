import java.util.Scanner;
 
public class Marathon {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for(int i = 0;i<t;i++){
            int count = 0;
            int a = sc.nextInt();
            int b = sc.nextInt();
            int c = sc.nextInt();
            int d = sc.nextInt();
            if(a < b) count++;
            if(a < c) count++;
            if(a < d) count++;
            System.out.println(count);
        }
    }
}