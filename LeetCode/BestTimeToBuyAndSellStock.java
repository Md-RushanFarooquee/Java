public class BestTimeToBuyAndSellStock {
    public int maxProfit(int[] prices) {
        int min = prices[0];
        int maxProfit  = 0;
        for(int price : prices){
            if(min > price) min = price;
            else maxProfit = Math.max(maxProfit,price - min);
        }
        return maxProfit;
    }
}
