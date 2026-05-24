package Arrays;

// Another Method

public class maxElement2 {
    public static void main(String[] args) {
        int arr[] = {-6,8,14,-2,23,47,4,3,10};

        int n = arr.length;

        int max = Integer.MIN_VALUE;   // minimum value of integer in java

        System.out.print("Maximum element of array is : ");

        for (int i = 1; i < n; i++) {
            if(max < arr[i]) max = arr[i];
        }
        System.out.print(max);
    }
}
