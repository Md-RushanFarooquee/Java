public class FirstAndLastOccurence {
    public static void main(String[] args) {
        int arr[] = {1,1,2,2,2,3,4,5,5,5,6,7,8,11};
        int target = 5;

        int firstIndex = findFirst(arr, target);
        int lastIndex = findLast(arr, target);

        System.out.println("First Index = "+firstIndex +"\nLast Index = "+lastIndex);
        
    }
    public static int findFirst(int arr[],int target) {
        int high = arr.length-1;
        int low = 0;
        int index = -1;
        while(low<=high){
            int mid = (low+high)/2;
            if(arr[mid] > target) high = mid - 1;
            else if(arr[mid] < target) low = mid+1;
            else { // arr[mid] == target
                index = mid;
                high = mid-1;
            }
        }
        return index;
    }
    public static int findLast(int arr[],int target) {
        int high = arr.length-1;
        int low = 0;
        int index = -1;
        while(low<=high){
            int mid = (low+high)/2;
            if(arr[mid] > target) high = mid - 1;
            else if(arr[mid] < target) low = mid+1;
            else { // arr[mid] == target
                index = mid;
                low = mid+1;
            }
        }
        return index;
    }
}
