import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

class Candidate {
    private String name;

    public Candidate(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Voter {
    private String name;
    private KeyPair keyPair;
    private String[] blindedVotes;
    private String[] signedBlindedVotes;

    public Voter(String name, KeyPair keyPair) {
        this.name = name;
        this.keyPair = keyPair;
        this.blindedVotes = new String[10];
        this.signedBlindedVotes = new String[10];
    }

    public String getName() {
        return name;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    public String[] getBlindedVotes() {
        return blindedVotes;
    }

    public String[] getSignedBlindedVotes() {
        return signedBlindedVotes;
    }

    public void blindVotes(Candidate[] candidates) {
        for (int i = 0; i < candidates.length; i++) {
            String vote = "Vote for " + candidates[i].getName();
            String blindedVote = blindMessage(vote);
            blindedVotes[i] = blindedVote;
        }
    }

    public void signBlindedVotes() {
        for (int i = 0; i < blindedVotes.length; i++) {
            String signedBlindedVote = signMessage(blindedVotes[i], keyPair.getPrivate());
            signedBlindedVotes[i] = signedBlindedVote;
        }
    }

    private String blindMessage(String message) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPublic());

            byte[] encryptedBytes = cipher.doFinal(message.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }


    private String signMessage(String message, PrivateKey privateKey) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(message.getBytes());
            byte[] signedBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signedBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }
}

class CentralElectionCommission {
    private Candidate[] candidates;
    private Voter[] voters;
    private Map<String, Boolean> signedVoteRecords;
    private Map<String, String> voterPublicKeyMap;

    public CentralElectionCommission(Candidate[] candidates, Voter[] voters) {
        this.candidates = candidates;
        this.voters = voters;
        this.signedVoteRecords = new HashMap<>();
    }

    public void conductElection() {
        // Election phase: Voters cast their blinded and signed votes
        for (Voter voter : voters) {
            voter.blindVotes(candidates);
            voter.signBlindedVotes();
            for (int i = 0; i < voter.getSignedBlindedVotes().length; i++) {
                // Simulate sending signed blinded votes to the CEC
                receiveSignedBlindedVote(voter.getName(), voter.getSignedBlindedVotes()[i]);
            }
        }

        // Election phase: CEC verifies and collects votes
        for (Voter voter : voters) {
            for (int i = 0; i < voter.getSignedBlindedVotes().length; i++) {
                String signedBlindedVote = voter.getSignedBlindedVotes()[i];
                if (!verifyBlindSignature(voter.getName(), signedBlindedVote)) {
                    System.out.println("Invalid blind signature detected for voter: " + voter.getName());
                    // Handle the violation as needed
                } else {
                    // CEC collects valid votes
                    collectVote(voter.getName(), signedBlindedVote);
                }
            }
        }

        // Election phase: CEC reveals the votes and announces results
        revealVotes();
    }

    private void receiveSignedBlindedVote(String voterName, String signedBlindedVote) {
        // Simulate receiving signed blinded votes from voters
        signedVoteRecords.put(voterName + signedBlindedVote, true);
        voterPublicKeyMap.put(voterName, voters[getVoterIndex(voterName)].getKeyPair().getPublic().toString());
    }

    private boolean verifyBlindSignature(String voterName, String signedBlindedVote) {
        // Перевірка коректності сліпого підпису
        try {
            String publicKeyString = voterPublicKeyMap.get(voterName);
            PublicKey voterPublicKey = getPublicKeyFromString(publicKeyString);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(voterPublicKey);
            signature.update(signedBlindedVote.getBytes());
            byte[] signedBytes = Base64.getDecoder().decode(signedBlindedVote);
            return signature.verify(signedBytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void collectVote(String voterName, String signedBlindedVote) {
        // Simulate collecting valid votes
        System.out.println("CEC collects valid vote from voter: " + voterName);
    }

    private void revealVotes() {
        // Election phase: CEC reveals votes and announces results
        for (Voter voter : voters) {
            for (int i = 0; i < voter.getBlindedVotes().length; i++) {
                String blindedVote = voter.getBlindedVotes()[i];
                String signedBlindedVote = voter.getSignedBlindedVotes()[i];
                String revealedVote = revealVote(blindedVote, signedBlindedVote, candidates);
                System.out.println("Voter: " + voter.getName() + ", Revealed Vote: " + revealedVote);
            }
        }
    }

    private String revealVote(String blindedVote, String signedBlindedVote, Candidate[] candidates) {
        // Розкриття голосу виборця після отримання його підписаного сліпого бюлетеня
        try {
            // Розкриваємо сліпий підпис для отримання голосу
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, voters[getVoterIndex(getVoterNameFromSignature(signedBlindedVote))].getPrivateKey());
            byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(blindedVote));
            String revealedVote = new String(decryptedBytes);

            // Перевіряємо підпис сліпого бюлетеня
            if (verifyBlindSignature(getVoterNameFromSignature(signedBlindedVote), signedBlindedVote)) {
                return revealedVote;
            } else {
                // Обробка невірного підпису
                System.out.println("Invalid blind signature detected during vote reveal.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private PublicKey getPublicKeyFromString(String publicKeyString) throws Exception {
        // Повертає публічний ключ із рядка
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString));
        return keyFactory.generatePublic(keySpecX509);
    }

    private int getVoterIndex(String voterName) {
        // Знаходить індекс виборця за іменем
        for (int i = 0; i < voters.length; i++) {
            if (voters[i].getName().equals(voterName)) {
                return i;
            }
        }
        return -1;
    }

    private String getVoterNameFromSignature(String signedBlindedVote) {
        // Отримати ім'я виборця з підписаного сліпого бюлетеня
        // Ця реалізація припускає, що ім'я виборця додається до підписаного бюлетеня,
        // наприклад, через роздільник ":". Вам може знадобитися використовувати інший спосіб, в залежності від вашого формату.
        String[] parts = signedBlindedVote.split(":");
        if (parts.length > 0) {
            return parts[0];
        } else {
            // Обробка випадку, коли ім'я виборця відсутнє в підписі
            System.out.println("Error: Voter name not found in signature.");
            return null;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            // Generate key pairs for voters
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);

            // Create candidates
            Candidate candidate1 = new Candidate("Candidate A");
            Candidate candidate2 = new Candidate("Candidate B");

            // Create voters
            Voter voter1 = new Voter("Voter 1", keyPairGenerator.generateKeyPair());
            Voter voter2 = new Voter("Voter 2", keyPairGenerator.generateKeyPair());
            Voter voter3 = new Voter("Voter 3", keyPairGenerator.generateKeyPair());
            Voter voter4 = new Voter("Voter 4", keyPairGenerator.generateKeyPair());

            // Create and configure Central Election Commission
            CentralElectionCommission CEC = new CentralElectionCommission(
                    new Candidate[]{candidate1, candidate2},
                    new Voter[]{voter1, voter2, voter3, voter4}
            );

            // Conduct the election with blind signatures
            CEC.conductElection();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
