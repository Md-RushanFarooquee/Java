public class PerfectNumber {
    public static void main(String[] args) {
        int num = 1;
        int sum = 1;
        if(num<=1) System.out.println(false);
        for(int i = 2; i<=Math.sqrt(num); i++){
            if(num % i == 0){
                sum += i;
                int pair = num / i;
                    if (pair != i && pair != num) {
                        sum += pair;
                    }
            }
        }
        System.out.println(sum == num);
    }
}
