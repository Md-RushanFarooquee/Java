import java.util.Scanner;

public class compareTo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String a = sc.next();
        String b = sc.next();
        System.out.println(compare(a, b));
        sc.close();
    }
    public static int compare(String a, String b){
        int min = Math.min(a.length(),b.length());
        for(int i =0;i<min;i++){
            if(a.charAt(i) != b.charAt(i)) return a.charAt(i) - b.charAt(i);
        }
        return a.length() - b.length();
    }
}
