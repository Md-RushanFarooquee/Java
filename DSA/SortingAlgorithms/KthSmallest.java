public class KthSmallest {
    public static void main(String[] args) {
        int arr[] = {7,10,4,3,20,15};
        int n = arr.length;
        int k = 3;
        for(int i =0;i<k;i++){
            int min = Integer.MAX_VALUE, mindx = -1;
            for(int j = i;j<n;j++){
                if(arr[j] < min){
                    min = arr[j];
                    mindx = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[mindx];
            arr[mindx] = temp;
        }
        System.out.println(arr[k-1]);
    }
}
