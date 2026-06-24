package Arrays;

import static Arrays.printArray.print;

public class sortArray {
    public static void main(String[] args) {
        
        int arr[] ={4,1,7,5,-3,10,2};
        int n = arr.length;

        System.out.print("Before sorting : ");
        print(arr);
        System.out.print("After sorting : ");

        for(int i = 0; i<n - 1; i++){
            for(int j = 0; j<n - 1; j++){

                if(arr[j] > arr[j+1]){
                    int temp = arr[j + 1];
                    arr[j + 1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        print(arr);
    }
}
