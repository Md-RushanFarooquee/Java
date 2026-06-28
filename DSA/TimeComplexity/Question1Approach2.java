public class Question1Approach2 {
    public static void main(String[] args) {
        int arr [] = {5,1,3,4,2,4};
        int n = arr.length;

        boolean flag [] = new boolean[n+1];

        for(int i = 0; i<n;i++){
            int element = arr[i];
            if(flag[element] == true) System.out.println(element);
            else flag[element] = true;            
        }
    }
}
