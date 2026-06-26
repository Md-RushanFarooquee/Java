package Arrays;

import static Arrays.printArray.print;

public class Sort0s1sAnd2s {
    public static void main(String[] args) {
        int arr[] = {0, 1, 2, 0, 1, 2};
        int n = arr.length;
        int zeroes = 0, ones = 0,twos=0;

        for(int i = 0; i<n; i++){
            if(arr[i] == 0){zeroes++;}
            else if(arr[i] == 1){ones++;}
            else{twos++;}
        }
        for(int i = 0; i<n; i++){
            if(i <zeroes){arr[i] = 0;}
            else if(i >= zeroes && i <zeroes + ones){arr[i] = 1;}
            else{arr[i] = 2;}
        }
        print(arr);
    }
}
