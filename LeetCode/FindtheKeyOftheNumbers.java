public class FindtheKeyOftheNumbers {
    public int generateKey(int num1, int num2, int num3) {
        String result = "";
        String s1 = String.format("%04d",num1);
        String s2 = String.format("%04d",num2);
        String s3 = String.format("%04d",num3);
        
        for(int i =0; i<4;i++){
            
            int key1 = s1.charAt(i) - '0';
            int key2 = s2.charAt(i) - '0';
            int key3 = s3.charAt(i) - '0';

            int min = Math.min(key1,Math.min(key2, key3));

            result += min;
        }
        return Integer.valueOf(result);
    }
}
