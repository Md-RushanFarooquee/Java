public class SingleAmongDoublesinASortedArray {
    public static void main(String[] args) {
        int arr[] = {1,1,2,2,3,3,4,50,50,65,65};
        // if (arr.length == 1) return arr[0];
        int lo = 0, hi = arr.length-1, num = arr[0];
        while(lo<= hi){
            int mid = lo + (hi - lo) / 2;
            if(arr[mid] != arr[mid-1] && arr[mid] != arr[mid+1]) {num = arr[mid]; break;}
            int f = mid, s = mid;
            if(arr[mid-1] == arr[mid]) f = mid -1;
            // else =  arr[mid] == arr[mid+1];
            else s = mid + 1;
            int leftCount = f - lo;
            int rightCount = hi - s;
            if(leftCount % 2 == 0) lo = s + 1;
            else hi = f - 1;
            
        }
        
        System.out.println(num);
    }
}
