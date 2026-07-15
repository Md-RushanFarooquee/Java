// works on sorted arrays
// TC = Worst Case: O(log n) 
// TC = Average Case: O(log n)
// TC = Best Case: O(1) // Element found at the middle in the first step

public class BinarySearch {
    public static void main(String[] args) {
        int arr[] = {-76,-4,9,28,47,49,510,615,9911,99999};
        int n = arr.length;
        int target = -5;
        int low = 0;
        int high = n-1;
        boolean flag = false;
        while(low<=high){
            int mid = (low + high) / 2;
            if(arr[mid] > target) high = mid-1;
            else if(arr[mid] < target) low =  mid+1;
            else { // (arr[mid] == target)
              flag = true;
            }
        }
        System.out.println(flag);
    }
}
