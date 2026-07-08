import java.util.Arrays;

// arr = {5,2,4,4,2,1,1,7}
// duplicate elements present
public class ThirdSmallest {
    public static void main(String[] args) {
        int arr[] = {1,1,1,2};
        Arrays.sort(arr);
        int max[] = new int[arr.length];
        int index = 0;
        max[index++] = arr[0];

        for(int i =1;i<arr.length;i++){
            if(arr[i] != arr[i-1]) max[index++] = arr[i];
        }
        if(index <= 2 ) System.out.println(max[index-1]);
        else System.out.println(max[2]);
    }
}
