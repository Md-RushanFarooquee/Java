import java.util.Scanner;

public class AntoAndDanik {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String s = sc.next();
        int countA = 0;
        int countD = 0;
        for(int i=0; i<s.length();i++){
            if(s.charAt(i) == 'A') countA++;
            else countD++;
        }
        if(countA > countD) System.out.println("Anton");
        else if (countA < countD) System.out.println("Danik");
        else System.out.println("Friendship");
    }
}
