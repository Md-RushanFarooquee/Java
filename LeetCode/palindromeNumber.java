public class palindromeNumber {
    public boolean isPalindrome(int x) {
        if(x < 0){
            return false;
        }
        else{
            int num = x;
            int rev = 0;
            int digit = 0;
            while(num != 0){
                digit = num % 10;
                rev = rev * 10 + digit;
                num = num / 10;
            }
            if (rev == x){
                return true;
            }
            else {
                return false;
            }
    }
    }
}
