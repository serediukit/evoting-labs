import java.security.*;
import java.util.*;

import CantVoteException.*;

import javax.crypto.*;

class CentralElectionCommission {
    private Integer candidatesCount = 0;
    private Integer votersCount = 0;
    private KeyPair keys;
    private final Map<Integer, Candidate> candidates;
    private final Map<Integer, Voter> voters;

    public CentralElectionCommission(KeyPair keyPair) {
        candidates = new HashMap<>();
        voters = new HashMap<>();
        this.keys = keyPair;
    }

    public void addCandidate(Candidate candidate) {
        candidates.put(candidatesCount, candidate);
        candidatesCount++;
    }

    public void addVoter(Voter voter) {
        voters.put(votersCount, voter);
        voter.setId(votersCount);
        votersCount++;
    }

    public void conductElection() {
        // Election phase: Voters cast their votes
        for (Voter voter : voters.values()) {
            if (voter.getDecryptedGammaVote() != -1) {
                System.out.println("Voter: " + voter.getName() + " is counting...");
                String vote = "Vote for " + voter.getDecryptedGammaVote(); // Assume all votes are for the first candidate
                String encryptedVote = encryptVote(vote, voter.getKeyPair().getPublic());
                voter.setEncryptedVote(encryptedVote);
            }
        }

        // Election phase: Central Election Commission counts and announces results
        int[] candidateVotes = new int[candidatesCount];
        for (int i = 0; i < candidatesCount; i++)
            candidateVotes[i] = 0;
        for (Voter voter : voters.values()) {
            String decryptedVote = decryptVote(voter.getEncryptedVote(), voter.getKeyPair().getPrivate());
            if (decryptedVote != null) {
                String[] decryptedArray = decryptedVote.split(" ");
                int index = Integer.parseInt(decryptedArray[2]);
                candidateVotes[index]++;
                voter.makeCounted();
            } else if (voter.getDecryptedGammaVote() == -1 && voter.canVote()) {
                System.out.println("The voter " + voter.getName() + " has not voted");
            } else {
                System.out.println("Invalid vote detected for voter: " + voter.getName());
            }
        }

        System.out.println("\n\nElection Results:");
        for (int i = 0; i < candidatesCount; i++) {
            System.out.println("Candidate: " + candidates.get(i).getName() + " -> votes: " + candidateVotes[i]);
        }
    }

    public void makeVote(Voter voter, int vote) {
        try {
            if (!voters.containsValue(voter)) {
                throw new VoterDoesNotExist(voter.getName());
            }
            if (!candidates.containsKey(vote)) {
                throw new CandidateDoesNotExist(candidates.get(vote).getName());
            }
            if (!voter.canVote()) {
                throw new CantVoteException(voter.getName());
            }
            if (!voters.get(voter.getId()).equals(voter)) {
                throw new OtherVoterException(voter.getName());
            }
            voter.makeVote(vote);
            candidates.get(vote).votesInc();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String encryptVote(String vote, PublicKey publicKey) {
        return Encryptor.encrypt(vote, publicKey);
    }

    private String decryptVote(String encryptedVote, PrivateKey privateKey) {
        return Encryptor.decrypt(encryptedVote, privateKey);
    }

    public ArrayList<Ballot> getSignedBallot(ArrayList<ArrayList<Ballot>> voterExamples) {
        Random r = new Random();
        int randIndex =  r.nextInt(10);
        for (int i = 0; i < voterExamples.size(); i++) {
            if (i != randIndex) {

            }
        }
    }

    public void printVotingStatus() {
        System.out.println("+------------------+--------------+");
        System.out.println("|        CANDIDATES STATUS        |");
        System.out.println("+------------------+--------------+");
        System.out.println("|    CANDIDATES    |     VOTES    |");
        System.out.println("+------------------+--------------+");
        for (Candidate candidate : candidates.values()) {
            System.out.printf("| %16s | %12d |\n", candidate.getName(), candidate.getVotesCount());
        }
        System.out.println("+------------------+--------------+");
        System.out.println("|          VOTERS STATUS          |");
        System.out.println("+------------------+--------------+");
        System.out.println("|      VOTERS      |    hasVOTE   |");
        System.out.println("+------------------+--------------+");
        for (Voter voter : voters.values()) {
            System.out.printf("| %16s | %12b |\n", voter.getName(), voter.hasVoted());
        }
        System.out.println("+------------------+--------------+");
    }
}