import java.util.ArrayList;

public class UnionOf2SortedArrays {
    public static void main(String[] args) {
        int a [] = {1,2,3,3,4,5};
        int b [] = {4,5,6,7};
        int i = 0;
        int j = 0;
        ArrayList<Integer> union = new ArrayList<>();
        while(i < a.length && j < b.length) {
            if(a[i] == b[j]){
                if( union.isEmpty() || union.get(union.size()-1) != a[i]) union.add(a[i]);
                i++;
                j++;
            }
            else if(a[i] < b[j] ){
                if(union.isEmpty() || union.get(union.size()-1) != a[i] ){ union.add(a[i]);}
                i++;
            }
            else if(a[i] > b[j] ){
                if(union.isEmpty() || union.get(union.size()-1) != b[j]  ){ union.add(b[j]);}
                j++;
            }
        }

        while(i<a.length){
            if(union.isEmpty() || union.get(union.size()-1) != a[i]  ) {
                union.add(a[i]);
            }
            i++;
        }
        while(j<b.length){
            if(union.isEmpty() || union.get(union.size()-1) != b[j]  ) {
                union.add(b[j]);
                
            }
            j++;
        }
        System.out.println(union);
           
    }
}
