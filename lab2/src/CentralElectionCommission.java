import java.security.*;
import java.util.*;

import CantVoteException.*;


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
            if (voter.getVote() != -1) {
//                System.out.println("Voter: " + voter.getName() + " is counting...");
                String vote = "Vote for " + voter.getVote(); // Assume all votes are for the first candidate
                String encryptedVote = Encryptor.encrypt(vote, voter.getKeyPair().getPublic());
                voter.setEncryptedVote(encryptedVote);
            }
        }

        // Election phase: Central Election Commission counts and announces results
        int[] candidateVotes = new int[candidatesCount];
        for (int i = 0; i < candidatesCount; i++)
            candidateVotes[i] = 0;
        for (Voter voter : voters.values()) {
            String decryptedVote = Encryptor.decrypt(voter.getEncryptedVote(), voter.getKeyPair().getPrivate());
            System.out.println(voter.getName() + "'s vote - " + decryptedVote);
            if (decryptedVote != null) {
                String[] decryptedArray = decryptedVote.split(" ");
                int index = Integer.parseInt(decryptedArray[2]);
                candidateVotes[index]++;
                voter.makeCounted();
            } else if (voter.getVote() == -1 && voter.canVote()) {
//                System.out.println("The voter " + voter.getName() + " has not voted\n");
            } else {
//                System.out.println("Invalid vote detected for voter: " + voter.getName() + "\n");
            }
        }

        System.out.println("+------------------+--------------+");
        System.out.println("|        ELECTION  RESULTS        |");
        System.out.println("+------------------+--------------+");
        System.out.println("|    CANDIDATES    |     VOTES    |");
        System.out.println("+------------------+--------------+");
        for (Candidate candidate : candidates.values()) {
            System.out.printf("| %16s | %12d |\n", candidate.getName(), candidate.getVotesCount());
        }
        System.out.println("+------------------+--------------+");
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
            if (!voter.hasSignedBallots()) {
                throw new SignedBallotsDoNotExistsException(voter.getName());
            }
            voter.makeVote(vote);
            candidates.get(vote).votesInc();
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
        }
    }

    public ArrayList<Ballot> getSignedBallot(Voter voter) {
        ArrayList<ArrayList<Ballot>> voterExamples = voter.getBallotsExamples();
        Random r = new Random();
        int randIndex =  r.nextInt(voterExamples.size());
        try {
            if (voterExamples.isEmpty())
                throw new ExamplesDoesNotExistException(voter.getName());
            for (int i = 0; i < voterExamples.size(); i++) {
                if (i != randIndex) {
                    for (int j = 0; j < voterExamples.get(i).size(); j++) {
                        String decryptedData = voterExamples.get(i).get(j).getDecryptedData(voter.getKeyPair().getPrivate());
                        String[] data = decryptedData.split(" ");
                        if (Integer.parseInt(data[0]) != voter.getId() || Integer.parseInt(data[1]) != j) {
                            throw new BallotIsNotValidException(decryptedData);
                        }
                    }
                }
            }
            ArrayList<Ballot> signedBallots = new ArrayList<>();
            for (int i = 0; i < voterExamples.get(randIndex).size(); i++) {
                Ballot signedBallot = voterExamples.get(randIndex).get(i);
                signedBallot.makeSigned();
                signedBallots.add(signedBallot);
            }
            return signedBallots;
        } catch (BallotIsNotValidException | ExamplesDoesNotExistException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int getCandidatesCount() {
        return candidatesCount;
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