import java.util.Arrays;

public class DestroyingAsteroids {
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        long sum = mass;
        Arrays.sort(asteroids);
        for(int i = 0;i<asteroids.length;i++){
            if(sum < asteroids[i]) return false;
            sum += asteroids[i];
        }
        return true;
    }
}
