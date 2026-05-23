package Arrays;

public class maxElement {
    public static void main(String[] args) {
        int arr[] = {-6,8,14,-2,23,47,4,3,10};

        int n = arr.length;
        int max = arr[0];
        System.out.print("Maximum element of array is : ");

        for (int i = 1; i < n; i++) {
            if(max < arr[i]) max = arr[i];
        }
        System.out.print(max);
    }
}
