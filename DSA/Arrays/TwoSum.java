package Arrays;

public class TwoSum {
    public static void main(String[] args) {
        int arr[] = {1,5,8,-3};
        int target = 2; 
        System.out.println(twoSum(arr,target));
    }

    public static boolean twoSum(int arr[], int target){
        int n = arr.length;
        for(int i = 0; i< n;i++){
            for(int j = i+1; j< n; j++){
                if (arr[i] + arr[j] == target){
                    return true;
                }
            }
        }
        return false;
    }
    
}  