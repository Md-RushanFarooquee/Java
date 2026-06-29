// Bubble Sorting : 

public class BasicSorting2 {
    public static void print(int arr[]){
        for(int ele : arr){
            System.out.print(ele +" ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int arr[] = {5,-2,6,7,2,0,7,2};
        int n = arr.length;
        print(arr);
        for(int i = 0; i<n-1;i++){
            for(int j = 0;j<n -1 - i;j++){     // j < n-1 - i = less time takes as after each pass
                if(arr[j] > arr[j+1]){         // last element is sorted, so next pass can run 1 less time 
                    int temp = arr[j];         // than previous one
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
        print(arr);
    }
}
