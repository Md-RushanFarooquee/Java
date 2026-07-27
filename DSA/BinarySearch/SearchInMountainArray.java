// peak element = arr[mid] > arr[mid+1] && arr[mid] > arr[mid - 1];

public class SearchInMountainArray {
    public static void main(String[] args) {
        int arr [] = {-1,0,1,2,5,6,8,6,3};
        int low = 0;
        int high = arr.length-1;
        int peak;
        while(low<high){
            int mid = (low+high) / 2;
            if(arr[mid] < arr[mid+1]) low = mid + 1;
            else {
                high = mid;
            }
        }
        peak = arr[low];
        System.out.println("INDEX = "+ low);
        System.out.println(peak);
    }
}
