public class MinimumCostOfBuyingCandiesWithDiscountII {
    public int minimumCost(int[] cost) {
        int sum = 0;
        int count = 0;
        for(int i = 0;i<cost.length - 1;i++){
            int max = cost[i];
            int maxdx = i;
                for(int j = i + 1;j<cost.length;j++){
                    if(cost[j] > max) {
                    max = cost[j];
                    maxdx = j;
                    }
                }
            int temp = cost[i];
            cost[i] = cost[maxdx];
            cost[maxdx] = temp;
        }
        for(int i = 0;i<cost.length;i++){
            if(count == 2){
                count = 0;
                continue;
            }
          sum+= cost[i];
          count++;
        
        }
        return sum;
    }
}
