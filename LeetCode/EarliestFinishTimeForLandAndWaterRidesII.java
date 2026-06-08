public class EarliestFinishTimeForLandAndWaterRidesII {
    public int earliestFinishTime(int[] landStartTime, int[] landDuration, int[] waterStartTime, int[] waterDuration) {
        int minLand = Integer.MAX_VALUE;
        for(int i = 0;i<landDuration.length;i++){
            minLand = Math.min(minLand,landStartTime[i] + landDuration[i]);
        }
        int minWater = Integer.MAX_VALUE;
        for(int i = 0;i<waterDuration.length;i++){
            minWater = Math.min(minWater,waterStartTime[i] + waterDuration[i]);
        }
        int minTime = Integer.MAX_VALUE;
        for(int i = 0;i<waterDuration.length;i++){
            minTime = Math.min(minTime,Math.max(minLand,waterStartTime[i]) + waterDuration[i]);
        }
        for(int i = 0;i<landDuration.length;i++){
            minTime = Math.min(minTime,Math.max(minWater,landStartTime[i]) + landDuration[i]);
        }
        return minTime;
    }
}
