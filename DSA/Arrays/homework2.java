package Arrays;
import static Arrays.printArray.print;
import java.util.Scanner;

// multiply odd index by 2 and add 10 to even indexed elements

public class homework2 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter size of array : ");
        int n = sc.nextInt();

        int arr[] = new int[n];
        System.out.println("Enter elements of array : ");
        for(int i = 0; i < n; i++){
            arr[i] = sc.nextInt();
        }
        for(int i = 0; i < n; i++){
            if(i % 2 == 0){
                arr[i] = arr[i] + 10;
            }
            else{
                arr[i] = arr[i] * 2;
            }
        }
        print(arr);
    }
}
