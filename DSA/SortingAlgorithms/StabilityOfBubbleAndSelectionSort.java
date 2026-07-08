public class StabilityOfBubbleAndSelectionSort {
    public static void main(String[] args) {
        int bubble[] = {7,3,4,7,8,1};
        //             7(1st) 7(2nd)
        int selection []= {7,3,4,7,8,1};
        int n = bubble.length;

        // BUBBLE SORT

        for(int i =0;i<n;i++){
            int swaps = 0;
            for(int j =0; j<n-1-i;j++){
                if(bubble[j] > bubble[j+1]){         
                    int temp = bubble[j];         
                    bubble[j] = bubble[j+1];
                    bubble[j+1] = temp;
                    swaps++;
                }
            }
            if(swaps == 0) break;
        }

        //SELECTION SORT

        for(int i =0;i<n-1;i++){
            int min = Integer.MAX_VALUE, mindx = -1;
            for(int j =i ;j<n;j++){
                if(selection[j]< min){
                    min = selection[j];
                    mindx = j;
                }
            }
            int temp = selection[i];
            selection[i] = selection[mindx];
            selection[mindx] = temp;
        }
        print(bubble);
        print(selection);
    }
    // in bubble sort relative order is maintained. 7(1) then 7(2) - stable/consistent
    // in selection sort relative order is not maintained. 7(2) then 7(1) or can stay the same - not consistent
    public static void print(int arr[]){
        for(int ele: arr){
            System.out.print(ele+" ");
        }
        System.out.println();
    }
}
