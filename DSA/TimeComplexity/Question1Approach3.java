public class Question1Approach3 {
    public static void main(String[] args) {
        int arr [] = {5,1,3,4,2,4};
        int n = arr.length;
        int sumArr = 0;

        for(int i =0; i<n;i++){
            sumArr += arr[i];
        }
        int sumofN = (n*(n-1) )/ 2;

        int target = sumArr - sumofN;
        System.out.println(target);
    }
}
