// INSERTION SORT
// TC = AVG CASE / WORST CASE : O(n^2)
// BEST CASE = O(n) // array is already sorted  //can also use swap like in bubble sort to break loop early

public class InsertionSort {
    public static void main(String[] args) {
        int arr[] = {4,1,7,3,9,2,0,8};
        for(int i = 0; i<arr.length;i++){
            int j = i;
            while(j>0 && arr[j] < arr[j-1]){
                int temp = arr[j];
                arr[j] = arr[j-1];
                arr[j-1] = temp;
                j--;
            } 
            // if(swaps == 0) break;           
        }
        for(int ele: arr) System.out.print(ele+" ");
    }
}
