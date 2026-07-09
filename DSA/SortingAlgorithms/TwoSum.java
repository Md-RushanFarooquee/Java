import java.util.Arrays;

public class TwoSum {
    public static void main(String[] args) {
        int arr[] = {1,4,45,6,10,8};
        Arrays.sort(arr);
        int n = arr.length;
        int target = 10;
        int start = 0;
        int end = n-1;

        while(start<end){
            if(arr[start] + arr[end] == target) {
                System.out.println(arr[start] + " " + arr[end]);
                break;
            }
            else if(arr[start] + arr[end] > target) end--;
            else if(arr[start] + arr[end] < target) start++;
        }
    }
}
