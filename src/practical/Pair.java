package practical;

public class Pair<A, B> {
    // 简单的双值对
    public A x;
    public B y;

    public Pair(A a, B b) {
        x = a;
        y = b;
    }

    public A get_key() {
        return x;
    }

    public B get_value() {
        return y;
    }
}
