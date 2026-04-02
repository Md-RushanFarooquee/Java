// Take interger input and print the absolute value of that integer

import java.util.*;

class AbsoluteValue{
    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number : ");
        int x = sc.nextInt();

        if (x < 0){
            System.out.print("Absolute value : "+ -x);
        }
        else{
            System.out.print("Absolute value : "+x);
        }
        sc.close();
    }
}