public class SquaresOfASortedArray {
    public int[] sortedSquares(int[] nums) {
        int i = 0,j = nums.length-1;
        int sqArr []= new int[nums.length];
        int k = sqArr.length -1;
        while(i<=j){
            if(Math.abs(nums[i]) < Math.abs(nums[j])){
                sqArr[k--] = nums[j] * nums[j];
                j--;
            }
            else{
                sqArr[k--] = nums[i] * nums[i];
                i++;
            }
        }
        return sqArr;
    }
}
