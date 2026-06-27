//given an array of size n+1 consisting of integers 1 to n.
// one of the elements is duplicate in the array.
// find the duplicate element

public class Question1Approach1 {
    public static void main(String[] args){
        int arr [] = {5,1,3,4,2,4};
        int n = arr.length;
        for(int i = 0;i<n;i++){
            for(int j = i+1;j<n;j++){
                if(arr[i] == arr[j]){
                    System.out.println(arr[i]);
                    return;
                }
            }
        }
    }
}
