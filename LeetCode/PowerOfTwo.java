public class PowerOfTwo{
    public boolean isPowerOfTwo(int n) {
        int power = 1;
        while (power <= n / 2) {
            power *= 2;
        }

        return power == n;
    }
}