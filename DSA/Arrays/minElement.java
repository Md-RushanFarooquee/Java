package Arrays;

public class minElement {
    public static void main(String[] args) {
        int arr[] = {8,-6,14,-2,23,47,4,3,10};

        int n = arr.length;
        int min = arr[0];
        System.out.print("Minimum element of array is : ");

        for (int i = 1; i < n; i++) {
            if(min > arr[i]) min = arr[i];
        }
        System.out.print(min);
    }
}
