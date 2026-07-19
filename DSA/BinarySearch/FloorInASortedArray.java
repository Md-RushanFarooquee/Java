public class FloorInASortedArray {
    public static void main(String[] args) {
        int arr[] = {1, 2, 8, 10, 10, 12, 19};
        int x = 11;
        int low = 0;
        int high = arr.length-1;
        int idx = -1;
        while(low<=high){
            int mid = (low + high) / 2;
            if(arr[mid] > x) high = mid-1;
            else {
                idx = mid;
                low = mid + 1;
            }
        }
        System.out.println(idx);
    }
}
