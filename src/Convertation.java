
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Convertation {

    public static final String alphanum = "0123456789abcdefghijklmnopqrstuvwxyz";

    private final char[] def;

    private final int sourceBase;

    private final int targetBase;
    public Convertation(int sourceBase, int targetBase) {
        this.def = alphanum.substring(0, targetBase).toCharArray();
        this.sourceBase = sourceBase;
        this.targetBase = targetBase;
    }

    private String toDecimal(String source_number) {

        String[] k = source_number.split("\\."); // [5, 6]
        int t = k[0].length() - 1;
        StringBuilder sb = new StringBuilder(source_number);
        sb.deleteCharAt(sb.indexOf("."));
        String intAndFractional = sb.toString(); // "12345a12345"

        BigDecimal sum = BigDecimal.ZERO;

        for (int i = 0; i < source_number.length() - 1; i++) {
            //переводим символ в десятиричную систему
            int decimalSymbol = Integer.parseInt(String.valueOf(intAndFractional.charAt(i)), sourceBase);
            // умножаем на базу в степени
            String j = String.valueOf(decimalSymbol * Math.pow(sourceBase, t));
            sum = sum.add(new BigDecimal(j));
            t--;
        }

        return sum.setScale(5, RoundingMode.HALF_DOWN).toString();

    }
    public String getTarget(String sourceNumber) {

        if (sourceNumber.matches("\\w+\\.\\w+")) {

            BigDecimal temp1 = new BigDecimal(toDecimal(sourceNumber));
            BigDecimal intPart = temp1.setScale(0, RoundingMode.FLOOR);
            BigDecimal fractalPart = temp1.subtract(intPart);

            String integerRebased = getIntRebased(intPart);
            String fractionalRebased = getFractalRebased(fractalPart);

            return integerRebased + fractionalRebased;

        } else {

            BigInteger temp1 = new BigInteger(sourceNumber, sourceBase);

            return getIntRebased(new BigDecimal(temp1));
        }

    }
    private String getIntRebased(BigDecimal intPart) {

        BigInteger temp = new BigInteger(intPart.toString());   // перевод в decimal base
        if (targetBase == 10) return String.valueOf(intPart);
        List<Character> target_number = new ArrayList<>();

        do {
            BigInteger[] dnr = temp.divideAndRemainder(BigInteger.valueOf(targetBase));
            target_number.add(0, def[dnr[1].intValue()]);
            temp = dnr[0];
        } while (temp.intValue() != 0);

        return  target_number.stream()
                .map(Object::toString)
                .collect(Collectors.joining());

    }
    private String getFractalRebased(BigDecimal fractalPart) {

        BigDecimal temp = fractalPart;
        StringBuilder sb = new StringBuilder(".");
        int counter = 5;

        do {
            temp = temp.multiply(new BigDecimal(targetBase));
            int integerPart = temp.intValue();
            sb.append(def[integerPart]);
            temp = temp.subtract(new BigDecimal(integerPart));
            counter--;
        } while (counter != 0);

        return sb.toString();
    }

}
