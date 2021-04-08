package parser.lib.datatypes;

/**
 * Interface with two methods <tt>asDouble</tt> and <tt>asString</tt> that represents datatypes in programming language
 *
 * @see StringValue
 * @see DoubleValue
 */
public interface IValue {
    /**
     * @return value as double
     */
    double asDouble();

    /**
     * @return value as string
     */
    String asString();
}
