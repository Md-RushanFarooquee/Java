// Binary search in descending order array
public class BinarySearchinDescendingOrderArray {
    public static void main(String[] args) {
    int arr[] = {99999,9911,615,510,49,47,28,9,-4,-76};
    int low = 0;
    int high = arr.length-1;
    int target = 49;
    int index = -1;
    while(low<=high){
        int mid = (low+high) / 2;
        if(arr[mid] > target) low = mid+1;
        else if(arr[mid] < target) high = mid - 1;
        else{
            index = mid;
            break;
        }
    }
    System.out.println(index);
    }
}
