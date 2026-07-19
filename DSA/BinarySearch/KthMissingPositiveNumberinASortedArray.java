public class KthMissingPositiveNumberinASortedArray {
    public static void main(String[] args) {
        int arr[] = {2,3,4,7,11};
        int k = 2;
        int lo = 0, hi = arr.length - 1;
        while(lo <= hi){
            int mid = lo + (hi -lo) / 2;
            int correctNO = mid + 1;
            int missing = arr[mid] - correctNO;
            if(k <= missing) hi = mid - 1;
            else lo = mid + 1;
        }
        System.out.println(lo + k);
    }
}
