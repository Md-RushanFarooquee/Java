import java.util.Scanner;

public class ArrivalOfTheGeneral {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int arr[] = new int[n];
        for(int i = 0; i<n;i++){
            arr[i] = sc.nextInt();
        }
        int mindx = -1;
        int maxdx = -1;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        for(int i = 0;i<n;i++){
            if(arr[i] <= min){
                min = arr[i];
                mindx = i;
            }
            if(arr[i] > max){
                max = arr[i];
                maxdx = i;
            }
        }
        int count;
        if(mindx < maxdx) mindx += 1;
        count = maxdx - 0 + (n-1 - mindx);
        System.out.println(count);
    }
}
