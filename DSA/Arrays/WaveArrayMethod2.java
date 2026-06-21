package Arrays;

import static Arrays.printArray.print;

public class WaveArrayMethod2 {
    public static void main(String[] args) {
        int arr[] = {2, 4, 7, 8, 9, 10};
        // ans = {4, 2, 8, 7, 10, 9}
        int n = arr.length;
        for(int i = 0; i< n-1; i+=2){
            int temp = arr[i];
            arr[i] = arr[i+1];
            arr[i+1] = temp;
        }
        print(arr);
    }
}
