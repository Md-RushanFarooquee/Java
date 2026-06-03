// Multiply odd indexed element by 2 and 10 to even indexed element

package Arrays;

public class homework1 {
    public static void main(String[] args) {
        int arr[] = {10,20,30,40,50};
        int n = arr.length;

        for(int i = 0;i<n;i++){
            if(i % 2 == 0) arr[i] += 10;
            else arr[i] *= 2;
        }

        print(arr);
    }
    
    public static void print(int arr[]){
        for(int i =0;i<arr.length;i++){
            System.out.print(arr[i]+" ");
        }
        System.out.println();
    }
}
