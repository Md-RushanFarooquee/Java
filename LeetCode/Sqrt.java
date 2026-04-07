public class Sqrt {
    public int mySqrt(int x) {
        int root = 0;

        if(x == 1 || x == 2){
            root = 1;
            return root;
        }
        for(int i = 1;i<x;i++){
            if(i == (x / i)){
                root = i;
                break;
            }
            if(i  > x / i){
                i = i -1;
                root = i;
                break;
            }
        }
        return root;
    }
}
