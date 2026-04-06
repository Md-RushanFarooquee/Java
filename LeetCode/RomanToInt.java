public class RomanToInt {
    public int romanToInt(String s) {
       int Icount = 0,  I = 1;
       int Vcount = 0,  V = 5;
       int Xcount = 0,  X = 10;
       int Lcount = 0,  L = 50; 
       int Ccount = 0,  C = 100;
       int Dcount = 0,  D = 500;
       int Mcount = 0,  M = 1000;
       String num = s;
       int number = 0;
       int length = num.length();

       for(int i = 0; i<length; i++){
        if(num.charAt(i) == 'I'){
            Icount += 1;
        }
        else if (num.charAt(i) == 'V') {
            Vcount += 1;
        }
        else if (num.charAt(i) == 'X') {
            Xcount += 1;
        }
        else if (num.charAt(i) == 'L') {
            Lcount += 1;
        }
        else if (num.charAt(i) == 'C') {
            Ccount += 1;
        }
        else if (num.charAt(i) == 'D') {
            Dcount += 1;
        }
        else{
            Mcount += 1;
        }
       }
        number = (M * Mcount) + (D * Dcount) + (C * Ccount) +
                 (L * Lcount) + (X * Xcount) + (V * Vcount) + (I * Icount);

        if(num.contains("IV")) number -=2;
        if(num.contains("IX")) number -=2;
        if(num.contains("XL")) number -=20;
        if(num.contains("XC")) number -=20;
        if(num.contains("CD")) number -=200;
        if(num.contains("CM")) number -=200;

        return number;
    }
}

