package Arrays;

import java.util.Scanner;

public class negativeElements {
    public static void main(String[] args) {
    
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter array size : ");
        int n = sc.nextInt();

        int arr[] = new int[n];

        System.out.println("Enter the elements of array : ");
        for(int i = 0; i<n;i++){

            System.out.print("Enter element "+ (i+1)+ " : ");
            arr[i]= sc.nextInt();
        }


        System.out.println("Negative elements of array are : ");
        for(int i = 0;i <n;i++){
            if(arr[i]<0){
                System.out.print(arr[i]+ " ");
            }
        }
    }
}
