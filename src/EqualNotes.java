import java.util.Arrays;

class EqualNotes {
    public static void main(String[] args) {
        String letter = "aahahaahhahahhaaahhaahaah";
        String aha = "aha";
        int[] array = new int[letter.length()];
        int indexaha = 0;
        for (int i = 0; i < letter.length()-2; ) {
            if (letter.substring(i, i + 3).equals(aha)) {
                array[indexaha] = i;
                indexaha++;
                i = i + 2;
            }
            else{i++;}
        }
        System.out.println(Arrays.toString(array));
    }
}