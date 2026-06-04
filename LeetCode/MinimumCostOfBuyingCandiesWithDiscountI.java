import java.util.Arrays;

public class MinimumCostOfBuyingCandiesWithDiscountI {
    public int minimumCost(int[] cost) {
        int sum = 0;
        int count = 0;
        Arrays.sort(cost);
        for(int i = cost.length-1;i>=0;i--){
            if(count == 2){
                count = 0;
                continue;
            }
            sum += cost[i];
            count++;
        }
        return sum;
    }
}
