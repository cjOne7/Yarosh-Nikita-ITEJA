package parser.lib;

public class StringValue implements IValue {
    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public int asNumber() {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public String toString() {
        return asString();
    }
}
