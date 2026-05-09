package Arrays;

import java.util.Scanner;

public class basics {
    
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        
        // int arr[] = {6,19,7,69,171,5};

        // System.out.println(arr[3]);
        
        // arr[3] = 89;
        // System.out.println(arr[3]);

        // int n = arr.length;
        
        // for(int i = 0; i<n;i++){
        //     System.out.println(arr[i]);
        // }

        int arr[] = new int[7];
        
        for(int i = 0;i<7;i++){
            System.out.print("Enter element "+ (1+i) + " : ");
            arr[i] = sc.nextInt();
        }

        for(int i = 0; i<7;i++){
            System.out.print(arr[i]+" ");
        }

    }
}
