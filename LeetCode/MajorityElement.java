public class MajorityElement {
    public int majorityElement(int[] nums) {
        int count = 0;
        int num = 0;
        for(int ele : nums){
            if(count == 0) num = ele;
            if(num == ele) count++;
            else count--;
        }
        return num;
    }
}
