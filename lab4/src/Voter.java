import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Voter {
    private static int examples = 0;
    private int id;
    private String name;
    private KeyPair keyPair;
    private ElGamal elGamal;
    private Ballot ballot;
    private static List<String> randomStrings = new ArrayList<>();
    private String message;
    private static List<String> messages = new ArrayList<>();;
    private String encodedMessage;
    private static List<byte[]> encodedMessages = new ArrayList<>();
    private List<byte[]> encodedBytes = new ArrayList<>();
    private static List<Ballot> ballots = new ArrayList<>();

    public Voter(String name) {
        this.name = name;
        id = examples;
        examples++;
        generateKeys();
        elGamal = new ElGamal();
    }

    private void generateKeys() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(1024);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            System.out.println("Failed to generate keys:\n" + e.getMessage());
        }
    }

    public int getId() {
        return id;
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public void createBallot(int candidateId) {
        ballot = new Ballot(id, candidateId);
    }

    public void encryptBallot(List<Voter> voters) {
        String ballotString = ballot.getData() + getRandomString(4);
        message = ballotString;
        ballots.add(ballot);
        byte[] firstStageOfEncrypting = encryptFirstStage(ballotString);
        byte[] secondStageOfEncrypting = encryptSecondStage(new String(firstStageOfEncrypting, StandardCharsets.UTF_8));
        encodedMessage = new String(secondStageOfEncrypting, StandardCharsets.UTF_8);
        encodedMessages.add(secondStageOfEncrypting);
    }

    private String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
            sb.append(Util.getRandomChar());
        return sb.toString();
    }

    private byte[] encryptFirstStage(String data) {
        byte[] res = data.getBytes(StandardCharsets.UTF_8);
        List<PublicKey> keys = PublicKeys.getPublicKeys();
        for (int i = 0; i < keys.size(); i++) {
            res = RSA.encrypt(new String(res, StandardCharsets.UTF_8), keys.get(keys.size() - i - 1));
            encodedBytes.add(res);
        }
        return res;
    }

    private byte[] encryptSecondStage(String data) {
        byte[] res = data.getBytes(StandardCharsets.UTF_8);
        List<PublicKey> keys = PublicKeys.getPublicKeys();
        for (int i = 0; i < keys.size(); i++)
            res = addStringAndEncrypt(new String(res, StandardCharsets.UTF_8), keys.get(keys.size() - i - 1));
        return res;
    }

    private byte[] addStringAndEncrypt(String data, PublicKey publicKey) {
        String randomString = getRandomString(4);
        randomStrings.add(randomString);
        byte[] randomStringBytes = randomString.getBytes(StandardCharsets.UTF_8);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] res = new byte[dataBytes.length + randomStringBytes.length];
        System.arraycopy(dataBytes, 0, res, 0, dataBytes.length);
        System.arraycopy(randomStringBytes, 0, res, dataBytes.length, randomStringBytes.length);
        return RSA.encrypt(new String(res, StandardCharsets.UTF_8), publicKey);
    }

    public void sendBallotTo(Voter voter) {
        messages.add(message);
    }

    public void decryptBallots() {
        for (int i = 0; i < encodedMessages.size(); i++) {
            String decryptedMessage = RSA.decrypt(encodedMessages.get(i), keyPair.getPrivate());
            randomStrings.remove(0);
            String removed = decryptedMessage.substring(0, decryptedMessage.length() - 4);
            byte[] encrypted = RSA.encrypt(removed, keyPair.getPublic());
            encodedMessages.remove(i);
            encodedMessages.add(i, encrypted);
        }
    }

    public void shuffleBallots() {
        Collections.shuffle(ballots);
//        Collections.shuffle(encodedMessages);
    }

    public void sign() {
        long[] signature = ElGamal.sign(ballot.getData());
//        long[] signature = ElGamal.sign(encodedMessages.get(id));
    }
}
