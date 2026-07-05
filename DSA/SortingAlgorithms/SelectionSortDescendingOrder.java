public class SelectionSortDescendingOrder {
    public static void main(String[] args) {
        int arr[] = {4, 1, 3, 9, 7};
        int n = arr.length;
        print(arr);
        for(int i=0;i<n-1;i++){
            int max = Integer.MIN_VALUE,maxdx = -1;
            for(int j=i;j<n;j++){
                if(arr[j] > max){
                    max = arr[j];
                    maxdx = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[maxdx];
            arr[maxdx] = temp;
        }
        print(arr);
    }
    public static void print(int arr[]){
        for(int ele : arr){
            System.out.print(ele+" ");
        }
        System.out.println();
    }
}
