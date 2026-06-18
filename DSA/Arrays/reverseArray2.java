package Arrays;

public class reverseArray2 {
    public static void main(String[] args) {
        int arr[] = {3,19,56,9,83,18,24,85,14};
        int n = arr.length;

        for(int i = 0; i<n/2; i++){
            int temp = arr[i];
            arr[i] = arr[n-1-i];
            arr[n-1-i] = temp;
        }
        for(int ele : arr){
            System.out.print(ele + " ");
        }
    }
}
