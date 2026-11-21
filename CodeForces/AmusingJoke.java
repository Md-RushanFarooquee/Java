import java.util.Arrays;
import java.util.Scanner;

public class AmusingJoke {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String guest = sc.next();
        String host = sc.next();
        String pile = sc.next();

        char combined[]= (guest + host).toCharArray();
        char piled[] = pile.toCharArray();

        Arrays.sort(combined);
        Arrays.sort(piled);
        if(Arrays.equals(combined,piled)) System.out.println("YES");
        else System.out.println("NO");
    }
}
