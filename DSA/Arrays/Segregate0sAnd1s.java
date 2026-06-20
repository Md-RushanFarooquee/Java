package Arrays;

import static Arrays.printArray.print;

public class Segregate0sAnd1s {
    public static void main(String[] args) {
        int arr[] = {0, 0, 1, 1, 0};
        int n = arr.length;
        int numberofZeroes = 0;

        for(int i = 0; i<n;i++){
            if(arr[i] == 0){
                numberofZeroes += 1;
            }
        }

        for( int j = 0; j < numberofZeroes; j++){
            arr[j] = 0;
        }
        for(int k = numberofZeroes; k < n; k++){
            arr[k] = 1;
        }
        print(arr);
    }
}
