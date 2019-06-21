package edu.gatech.seclass;

public class MyCustomString implements MyCustomStringInterface {
    private String string;

    @Override
    public String getString() {
        if (string != null){
            return string;
        }

        else {
            return null;
        }
    }

    @Override
    public void setString(String string) {
        this.string = string;

    }

    @Override
    public int countDuplicates() {
        int count = 0;
        for (int i = 0; i < this.string.length()-1; i++){
            if (this.string.charAt(i) == this.string.charAt(i+1)){
                count ++;
            }
        }
        return count;
    }

    @Override
    public String addDigits(int n, boolean positive) {
        if (this.string == null){
            throw new NullPointerException();
        }
        if (n <0 || n>9){
            throw new IllegalArgumentException();
        }
        String new_string = "";

        if (positive) {
            for (int i = 0; i < this.string.length(); i++) {
                if (Character.isDigit(this.string.charAt(i))) {
                    int int_val = Character.getNumericValue(this.string.charAt(i));
                    int_val = (int_val + n) % 10;
                    new_string += int_val;
                }
                else {
                    new_string += this.string.charAt(i);
                }
            }
        } else {
            for (int i = 0; i < this.string.length(); i++) {
                if (Character.isDigit(this.string.charAt(i))) {
                    int int_val = Character.getNumericValue(this.string.charAt(i));
                    int_val = ((int_val - n) % 10 + 10)%10;
                    new_string += int_val;
                } else {
                    new_string += this.string.charAt(i);
                }
            }
        }
        return new_string;
    }

    @Override
    public void flipLetttersInSubstring(int startPosition, int endPosition) {
        if (this.string == null){
            throw new NullPointerException();
        }
        if (endPosition > this.string.length() || startPosition <= 0){
            throw new MyIndexOutOfBoundsException();
        }
        if (startPosition > endPosition){
            throw new IllegalArgumentException();
        }
        String substr  = this.string.substring(startPosition -1, endPosition);
        int i1 = 0, i2 = substr.length()-1;
        //source for below code: https://stackoverflow.com/questions/5692472/reverse-characters-in-java-while-keeping-some-in-place
        while (i1<i2){
            while (!Character.isLetter(substr.charAt(i1)) && i1<i2){
                i1++;
            }
            while (!Character.isLetter(substr.charAt(i2)) && i1<i2) {
                i2--;
            }
            if (i1<i2){
                char[] a=substr.toCharArray();
                Character temp = a[i1];
                a[i1] = a[i2];
                a[i2] = temp;
                substr = new String(a);
            }
            i1++; i2--;
        }
        this.string =  this.string.substring(0, startPosition-1) + substr + this.string.substring(endPosition);

    }
}
