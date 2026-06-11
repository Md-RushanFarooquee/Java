package Arrays;

import java.util.Scanner;

public class searchinArray {
    public static void main(String[] args) {
        int arr[] ={4,1,7,5,-3,10,2};
        int n = arr.length;
        int found = -1;

        System.out.print("Array is : ");
        System.out.print("[ ");
        print(arr);
        System.out.print("]");
        System.out.println();

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter target : ");
        int target = sc.nextInt();

        for(int i = 0; i<n; i++){
            if(arr[i] == target) {
                found = i;
                    break;
                }
        }

        if(found != -1) System.out.print("Element is present in array at index "+found);
        else System.out.print("Element is not present in array");

    }

    public static void print(int arr[]) {
        for(int i = 0; i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
    }
}
