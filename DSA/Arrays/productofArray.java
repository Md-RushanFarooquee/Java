public class productofArray {
    public static void main(String[] args) {
        
        int arr[] = {3,7,4,8};

        int product = 1;
        for(int i = 0; i < arr.length; i++){
            product *= arr[i];
        }
        System.out.println(product);
    }
}
