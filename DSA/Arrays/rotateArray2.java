package Arrays;

import static Arrays.printArray.print;

public class rotateArray2 {
    public static void main(String[] args) {
        int arr[] = {4,3,2,6};
        int n = arr.length;
        int d = 1;
        d = d % n;

        reverse(arr,0,d-1);
        reverse(arr,d,n-1);
        reverse(arr,0,n-1);

        print(arr);

    }
    static void reverse(int arr[], int i, int j){
        while(i <j){
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
            i++;
            j--;
        }
    }
}
