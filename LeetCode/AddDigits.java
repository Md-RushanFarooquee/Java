public class AddDigits {
    public int addDigits(int num) {
       int sumDigits = 0;
        int n = num;
        while(n != 0){
            int digits = n % 10;
            sumDigits += digits;
            n = n / 10;
        }
        while(sumDigits >= 10){
            n = sumDigits;
            sumDigits = 0;
            while(n != 0){
            int digits = n % 10;
            sumDigits += digits;
            n = n / 10;
        }
        }
        return sumDigits;
    }
}
