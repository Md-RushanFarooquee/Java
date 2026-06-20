package Arrays;
// USING TWO POINTER TECHNIQUE

public class Segregate0sAnd1sMethod2 {
    public static void main(String[] args) {
        int arr[] = {1,0,0,1,1,1,0,1,0,1,1,0};
        int n = arr.length;
        int i = 0;
        int j = n-1;

        while( i < j){
            if(arr[i] == 0) i++;
            else if(arr[j] == 1) j--;
            else if (arr[i] == 1 && arr[j] == 0){
                arr[i] = 0;
                arr[j] = 1;
                i++;
                j--;
            }
        }
    }
}
