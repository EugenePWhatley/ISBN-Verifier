import java.util.List;
import java.util.stream.Collectors;

public class IsbnVerifier {
    public static final int EXPECTED_LENGTH = 10;
    private static int multiple;

    private static boolean isNumber(String digit) {
        try {
            Integer.parseInt(digit);
            return true;
        } catch (Exception ignore) {
            return false;
        }
    }

    private static boolean isNotHyphen(String digit) {
        return !digit.equals("-");
    }

    private static String updateCheckDigit(String digit) {
        return digit.replace("X", "10");
    }

    private static Integer formula(Integer number) {
        return number * multiple--;
    }

    private static Boolean verification(Integer sum) {
        return sum % 11 == 0;
    }

    public boolean isValid(String input) {
        List<Integer> isbn = List.of(input.split(""))
                .stream()
                .filter(IsbnVerifier::isNotHyphen)
                .map(IsbnVerifier::updateCheckDigit)
                .takeWhile(IsbnVerifier::isNumber)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        if (!isExpectedLength(isbn) || isCheckDigitLast(isbn)) return false;
        multiple = isbn.size();

        return isbn.stream()
                .map(IsbnVerifier::formula)
                .reduce(Integer::sum)
                .map(IsbnVerifier::verification)
                .orElse(false);
    }

    private boolean isCheckDigitLast(List<Integer> isbn) {
        return isbn.contains(10) && isbn.indexOf(10) != isbn.size() - 1;
    }

    private boolean isExpectedLength(List<Integer> isbn) {
        return isbn.size() == EXPECTED_LENGTH;
    }
}
