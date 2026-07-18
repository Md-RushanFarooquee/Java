public class FirstOccurrence  {
    public static void main(String[] args) {
        int arr[] = {1,1,2,2,2,3,4,5,5,5,6,7,8,11};
        int target = 5;
        int high = arr.length-1;
        int low = 0;
        int index = -1;
        while(low<=high){
            int mid = (low+high)/2;
            if(arr[mid] > target) high = mid - 1;
            else if(arr[mid] < target) low = mid+1;
            else { // arr[mid] == target
                index = mid;
                high = mid - 1;
            }
        }
        System.out.println(index);
    }
}
