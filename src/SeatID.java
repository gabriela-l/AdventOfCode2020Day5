import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
/*
https://adventofcode.com/2020/day/5
 */
public class SeatID {
    /* countNumber method counts the column number or the row number of a seat.
        It uses recursion, it's going through the code's String
        and applies itself to each character with changing parameters */
    public static int countNumber(String code, int i, int max, int min) {
        int range = max - min + 1;
        // after the last character, print out the answer (at this point, max==min)
        if (i == code.length()) {
            return max;
        }
        // F or L means lower half of region
        if ((code.charAt(i) == 'F')||(code.charAt(i) == 'L')) {
            // lower the maximum, min stays the same:
            max = max - (range/2);
        // B or R means upper half of region
        } else if ((code.charAt(i) == 'B')|(code.charAt(i) == 'R')) {
            // set the min higher, max stays the same:
            min = min + (range/2);
        }
        // use this function again for the next character:
        i++;
        return countNumber(code, i, max, min);
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("src/seatCodes.txt")));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                // Front or back
                String seatCol = line.substring(0, 7);
                // Left or right
                String seatRow = line.substring(7, 10);
                int seatColNumber = countNumber(seatCol, 0, 127, 0);
                int seatRowNumber = countNumber(seatRow, 0, 7, 0);
                // Seat ID formula:
                int id = (seatColNumber * 8) + seatRowNumber;
                numbers.add(id);
            }
        } finally {
            br.close();
        }

        // first star:
        System.out.println(Collections.max(numbers));

        // second star:
        // I'm looking for a missing number, number that fits in between two consecutive numbers i and i+2 in a sorted list of IDs
        Collections.sort(numbers);
        for (int i = 0; i < numbers.size()-1; i++) {
            if (numbers.get(i)+2 == numbers.get(i+1)) {
                // print my missing ID:
                System.out.println(numbers.get(i)+1);
            }
        }
    }
}
