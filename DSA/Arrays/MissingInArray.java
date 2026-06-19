package Arrays;

public class MissingInArray {
    public static void main(String[] args) {
        int arr[] = {8, 2, 4, 5, 3, 7, 1};
        long n = arr.length + 1;
        long actual = 0;
        long expected = n * (n + 1)/2 ;
        
        for(long ele : arr){
            actual += ele;
        }
        int missing = (int)(expected - actual);

        System.out.println(missing);
    }
}
