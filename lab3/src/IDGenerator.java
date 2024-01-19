import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class IDGenerator {
    private static final List<BigInteger> history = new ArrayList<>();

    public static BigInteger generateID() {
        BigInteger id;
        do {
            id = new BigInteger(16, new SecureRandom());
        } while (history.contains(id));
        history.add(id);
        return id;
    }
}
