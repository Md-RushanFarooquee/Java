import java.util.Scanner;

public class ConsecutiveSumRiddle {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        for(int i = 0;i<t;i++){
            long n = sc.nextLong();
            if(n%2 != 0){
                long l = (n-1) / 2;
                long r = l + 1;
                System.out.println(l + " "+ r);
            }
            else{
                long l = -(n - 1);
                long r = n;
                System.out.println(l + " "+ r);
            }
        }
    }
}
