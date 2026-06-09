public class TotalWavinessOfNumbersInRangeI {
    public int totalWaviness(int num1, int num2) {
        int sum = 0;
        while(num1 <=num2){
            sum += wave(num1);
            num1++;
        }
        return sum;
    }
    private int wave(int num){
        int waveCount = 0;
        char[] arr = String.valueOf(num).toCharArray();
        if(arr.length <=2) return 0;
        for(int i = 1;i<arr.length - 1;i++){
            if(arr[i] > arr[i+1] && arr[i] > arr[i-1] || arr[i] < arr[i+1] && arr[i] < arr[i-1] ) waveCount++;
        }
        return waveCount;
    }
}
