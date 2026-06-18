package Arrays;

import static Arrays.printArray.print;

public class rotateArray {
    public static void main(String[] args) {
        //int arr[] = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20};
        int arr[] = {7, 3, 9, 1};
        int n = arr.length;
        int d = 9;

        while(d > n){
            d = d - n;
        }

        int i = 0;
        int j = d-1;
        
        while(i < j){
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp; 
            i++;
            j--;
        }

        i = d;
        j = n -1;

        while(i < j){
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
        i = 0;
        j = n-1;
        while(i < j){
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
        print(arr);
    }
}
