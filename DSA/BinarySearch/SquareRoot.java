// T.C = O(√x);
public class SquareRoot {
    public static void main(String[] args) {
        int n = 15;
        int root = 0;
        for(int i =1; i<=n;i++){
            if(i * i > n)break;
            root = i;
        }
        System.out.println(root);       
    }
}
