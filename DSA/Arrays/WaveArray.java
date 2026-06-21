package Arrays;

import static Arrays.printArray.print;

public class WaveArray {
    public static void main(String[] args) {
        int arr[] = {2, 4, 7, 8, 9, 10};
        // ans = {4, 2, 8, 7, 10, 9}
        int n = arr.length;
        n = n / 2;
        int index1 = 0;
        int index2 = 1;
        for(int i = 0; i<n ; i++){
            int temp = arr[index1];
            arr[index1] = arr[index2];
            arr[index2] = temp;
            index1 += 2;
            index2 += 2;
        }
    print(arr);
    }

}
