import java.util.Arrays;

public class Key {
    public final byte[] value;

    public Key(byte[] value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Arrays.toString(value);
    }
}
