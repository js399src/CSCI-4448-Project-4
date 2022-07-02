import java.text.NumberFormat;

public interface Format {
    // a utility for printing out pretty $ values
    // https://stackoverflow.com/questions/13791409/java-format-double-value-as-dollar-amount
    static String asDollar(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format(value);
    }
}
