import java.util.Scanner;

public class RestoringThreeNumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int nums[] = new int[4]; 
        int max = Integer.MIN_VALUE;    
        for(int i = 0;i<4;i++){
            nums[i] = sc.nextInt();
            if(max < nums[i]) max = nums[i];
        }
        for(int num : nums){
            if(num != max) System.out.print(max - num +" ");
        }
    }
}
