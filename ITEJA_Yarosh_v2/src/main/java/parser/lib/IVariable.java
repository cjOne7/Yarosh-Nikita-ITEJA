package parser.lib;

public interface IVariable {
    IValue ZERO = new NumberValue(0);
    IValue EMPTY = new StringValue("");

    static boolean isKeyExists(String key) {
        return false;
    }

    static IValue getValueByKey(String key) {
        return null;
    }

    static void put(String key, IValue value){

    }
}
