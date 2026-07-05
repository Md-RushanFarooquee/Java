// int arr[] = {8,4,1,9,-3,6,5}
// int arr[] = {8,4,1,5,-3,6,9}  // swap largest with last place

public class SelectionSortLargestFirst {
    public static void main(String[] args) {
        int arr[] = {8,4,1,9,-3,6,5};
        int n = arr.length;
        print(arr);
        for(int i = n-1; i>=0;i--){
            int max = Integer.MIN_VALUE, maxdx = -1;
            for(int j=i;j>=0;j--){
                if(arr[j]>max){
                    max = arr[j];
                    maxdx = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[maxdx];
            arr[maxdx] = temp;
        }
        print(arr);
    }
    public static void print(int arr[]){
        for(int ele : arr){
            System.out.print(ele+" ");
        }
        System.out.println();
    }
}
