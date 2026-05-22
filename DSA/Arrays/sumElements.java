package Arrays;

import java.util.Scanner;

public class sumElements {
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter size of array : ");
        int n = sc.nextInt();

        int arr[] = new int[n];

        System.out.println("Enter elements of array : ");
        for(int i = 0; i<n;i++){

            System.out.printf("Enter element %d : ", (i+1));
            arr[i] = sc.nextInt();
        }

        int sum = 0;
        for(int i = 0; i<n;i++){
            sum = sum + arr[i];
        }
        System.out.print("Sum of ");
        for(int i = 0; i<n;i++){
            System.out.print(arr[i]+ " ");
        }
        System.out.print("is : "+ sum);
    }
}
