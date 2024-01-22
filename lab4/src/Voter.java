import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Voter {
    private static int examples = 0;
    private int id;
    private String name;
    private KeyPair keyPair;
    private ElGamal elGamal;
    private Ballot ballot;
    private static List<String> randomStrings = new ArrayList<>();
    private static List<String> messages = new ArrayList<>();
    private static List<byte[]> encodedMessages = new ArrayList<>();
    private List<byte[]> encodedBytes = new ArrayList<>();
    private static List<Ballot> ballots = new ArrayList<>();
    private List<Ballot> resBallots = new ArrayList<>();
    private static List<BigInteger[]> signatures = new ArrayList<>();

    public Voter(String name) {
        this.name = name;
        id = examples;
        examples++;
        generateKeys();
        elGamal = new ElGamal();
        signatures.addAll(Collections.nCopies(4, new BigInteger[2]));
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

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public void createBallot(int candidateId) {
        ballot = new Ballot(id, candidateId);
    }

    public void encryptBallot(List<Voter> voters) {
        String randomString = getRandomString();
        randomStrings.add(randomString);
        String ballotString = ballot.getData() + randomString;
        messages.add(ballotString);
        ballots.add(ballot);
        byte[] firstStageOfEncrypting = encryptFirstStage(ballotString);
        byte[] secondStageOfEncrypting = encryptSecondStage(new String(firstStageOfEncrypting, StandardCharsets.UTF_8));
        encodedMessages.add(secondStageOfEncrypting);
    }

    private String getRandomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++)
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
        String randomString = getRandomString();
        randomStrings.add(randomString);
        byte[] randomStringBytes = randomString.getBytes(StandardCharsets.UTF_8);
        byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
        byte[] res = new byte[dataBytes.length + randomStringBytes.length];
        System.arraycopy(dataBytes, 0, res, 0, dataBytes.length);
        System.arraycopy(randomStringBytes, 0, res, dataBytes.length, randomStringBytes.length);
        return RSA.encrypt(new String(res, StandardCharsets.UTF_8), publicKey);
    }

    public void sendBallotTo(Voter voter) {
        if (messages.size() > 4)
            System.out.println("Message sending error");
    }

    public void decryptBallots() {
        for (int i = 0; i < encodedMessages.size(); i++) {
            String decryptedMessage = RSA.decrypt(encodedMessages.get(i), keyPair.getPrivate());
            String removed = decryptedMessage.substring(0, decryptedMessage.length() - 4);
            byte[] encrypted = RSA.encrypt(removed, keyPair.getPublic());
            encodedMessages.set(i, encrypted);
            if (randomStrings.size() > examples + 1)
                randomStrings.remove(randomStrings.size() - 1);
        }
    }

    public void decryptBallotsWithOutDeleting() {
        for (int i = 0; i < encodedMessages.size(); i++) {
            String decryptedMessage = RSA.decrypt(encodedMessages.get(i), keyPair.getPrivate());
            byte[] encrypted = RSA.encrypt(decryptedMessage, keyPair.getPublic());
            encodedMessages.set(i, encrypted);
        }
    }

    public void shuffleBallots() {
        Collections.shuffle(encodedMessages);
    }

    public void sign() {
        for (int i = 0; i < encodedMessages.size(); i++) {
            BigInteger[] signature = elGamal.sign(new String(encodedMessages.get(i), StandardCharsets.UTF_8));
            signatures.set(i, signature);
        }
    }

    public void sendBallots(Voter voter) {
        voter.setResBallots(ballots);
    }

    private void setResBallots(List<Ballot> tempBallots) {
        this.resBallots = new ArrayList<>(tempBallots);
    }

    public void verifySign() {
        for (Ballot ballot : ballots) {
            if (!elGamal.verifySign(ballot.getData(), ballot.getSignatures())) {
                System.out.println("Ballot " + ballot.getData() + " is not verified!");
            }
            ballot.removeSign();
        }
    }

    public void deleteFirstRandomString() {
        randomStrings.clear();
        for (int i = 0; i < encodedMessages.size(); i++) {
            String msg = messages.get(i).substring(0, messages.get(i).length() - 4);
            messages.set(i, msg);
        }
    }

    public static String getResult(List<Candidate> candidates) {
        for (String message : messages) {
            int vote = Integer.parseInt(message.substring(message.length() - 1));
            for (Candidate candidate : candidates) {
                if (candidate.getId() == vote) {
                    candidate.incrementVotes();
                }
            }
        }
        StringBuilder result = new StringBuilder();
        for (Candidate candidate : candidates) {
            result.append(candidate.getName()).append(": ").append(candidate.getVotesCount()).append("\n");
        }
        return result.toString();
    }
}
