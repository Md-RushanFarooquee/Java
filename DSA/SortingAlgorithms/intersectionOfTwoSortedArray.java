import java.util.ArrayList;

public class intersectionOfTwoSortedArray {
    public static void main(String[] args) {
        int arr1[] = {1, 2, 3, 4};
        int arr2[] = {2, 4,4, 6, 7, 8};
        int i = 0;
        int j = 0;
        ArrayList<Integer> intersection = new ArrayList<>();

        while(i < arr1.length && j < arr2.length ){

            if(arr1[i] == arr2[j] && intersection.isEmpty()){
                 intersection.add(arr1[i]);
                 i++;
                 j++;
            }
            else if(arr1[i] == arr2[j] && intersection.get(intersection.size()-1) != arr1[i]){
                 intersection.add(arr1[i]);
                 i++;
                 j++;
            }
            else if(arr1[i] < arr2[j]) i++;
            else j++;
        }
        System.out.println(intersection);
    }
}
