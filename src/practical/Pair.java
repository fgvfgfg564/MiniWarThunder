package practical;

public class Pair<A, B> {
    // 简单的双值对
    A _key;
    B _value;

    public Pair(A a, B b) {
        _key = a;
        _value = b;
    }

    public A get_key() {
        return _key;
    }

    public B get_value() {
        return _value;
    }
}
