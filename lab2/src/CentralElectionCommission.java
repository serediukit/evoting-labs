import java.math.BigInteger;
import java.security.*;
import java.util.*;

import CantVoteException.*;


class CentralElectionCommission {
    private Integer candidatesCount = 0;
    private Integer votersCount = 0;
    private final KeyPair keys;
    private final Map<Integer, Candidate> candidates;
    private final Map<Integer, Voter> voters;
    private final ArrayList<Boolean> isVoterChecked;
    private final Map<Integer, Ballot> ballots;

    public CentralElectionCommission(KeyPair keyPair) {
        candidates = new TreeMap<>();
        voters = new TreeMap<>();
        isVoterChecked = new ArrayList<>();
        ballots = new TreeMap<>();
        this.keys = keyPair;
    }

    public void addCandidate(Candidate candidate) {
        candidates.put(candidatesCount, candidate);
        candidatesCount++;
    }

    public void addVoter(Voter voter) {
        voters.put(votersCount, voter);
        isVoterChecked.add(false);
        voter.setId(votersCount);
        votersCount++;
    }

    public void conductElection() {
        for (Integer voterId : ballots.keySet()) {
            String signedData = ballots.get(voterId).getData();
            BigInteger s = Encryptor.getS(new BigInteger(signedData), voters.get(voterId).getR(), keys.getPublic());
            BigInteger m = Encryptor.getM(s, keys.getPublic());
            int vote = m.mod(BigInteger.TEN).intValue();
//            if (voterId == 3)
//                candidates.get(0).votesInc();
//            else
            candidates.get(vote).votesInc();
            voters.get(voterId).makeCounted();
//            System.out.println(voters.get(voterId).getName() + "'s vote - " + vote);
        }

        printVotingResults();
    }

    public void sendBallot(Voter voter, Ballot ballot) {
        try {
            if (ballot != null) {
                Ballot decryptedBallot = new Ballot(Encryptor.decrypt(ballot.getData(), keys.getPrivate()));
                String signedData = decryptedBallot.getData();
                BigInteger s = Encryptor.getS(new BigInteger(signedData), voter.getR(), keys.getPublic());
                BigInteger m = Encryptor.getM(s, keys.getPublic());
                int voterId = m.divide(BigInteger.TEN).intValue();
                int vote = m.mod(BigInteger.TEN).intValue();
                if (!voters.containsKey(voterId))
                    throw new Exception("Incorrect ballot");
                if (voter.getId() != voterId)
                    throw new OtherVoterException(voter.getName());
                if (!isVoterChecked.get(voterId))
                    throw new SignedBallotsDoNotExistsException(voter.getName());
                if (checkBallotData(voter, vote)) {
                    ballots.put(voterId, decryptedBallot);
                    voter.vote();
                }
            } else {
                throw new SignedBallotsDoNotExistsException("null");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkBallotData(Voter voter, int vote) {
        try {
            if (!voters.containsValue(voter))
                throw new VoterDoesNotExist(voter.getName());
            if (!candidates.containsKey(vote))
                throw new CandidateDoesNotExist(candidates.get(vote).getName());
            if (!voter.canVote())
                throw new CantVoteException(voter.getName());
            if (!voters.get(voter.getId()).equals(voter))
                throw new OtherVoterException(voter.getName());
            if (!voter.hasSignedBallots())
                throw new SignedBallotsDoNotExistsException(voter.getName());
            if (voter.hasVoted())
                throw new VoterHasAlreadyVotedException(voter.getName());
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage() + "\n");
        }
        return false;
    }

    public ArrayList<Ballot> getSignedBallot(ArrayList<ArrayList<Ballot>> ballotsList, BigInteger r) {
        Random rnd = new Random();
        int randIndex = rnd.nextInt(ballotsList.size());
        try {
            if (ballotsList.isEmpty())
                throw new ExamplesDoesNotExistException();
            int voterId = -1;
            int prevVoterId = -1;
            for (int i = 0; i < ballotsList.size(); i++) {
                if (i != randIndex) {
                    for (int j = 0; j < ballotsList.get(i).size(); j++) {
                        String ballotData = ballotsList.get(i).get(j).getData();
                        BigInteger s_ = Encryptor.getS_(new BigInteger(ballotData), keys.getPrivate());
                        BigInteger s = Encryptor.getS(s_, r, keys.getPublic());
                        BigInteger m = Encryptor.getM(s, keys.getPublic());
                        voterId = m.divide(BigInteger.TEN).intValue();
                        int vote = m.mod(BigInteger.TEN).intValue();
                        if (voterId != prevVoterId && prevVoterId != -1)
                            throw new IncorrectBallotsList();
                        if (vote != j)
                            throw new BallotIsNotValidException(voters.get(voterId).getName());
                        if (isVoterChecked.get(voterId))
                            throw new VoterAlreadyHasSignedBallots();
                        prevVoterId = voterId;
                    }
                }
            }
            ArrayList<Ballot> signedBallots = new ArrayList<>();
            for (int i = 0; i < ballotsList.get(randIndex).size(); i++) {
                Ballot signedBallot = ballotsList.get(randIndex).get(i);
                String signedData = signedBallot.getData();
                BigInteger s_ = Encryptor.getS_(new BigInteger(signedData), keys.getPrivate());
                signedBallot.setData(String.valueOf(s_));
                signedBallots.add(signedBallot);
            }
            isVoterChecked.set(voterId, true);
            return signedBallots;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Ballot getBallotFor(Voter voter) {
        if (ballots.containsKey(voter.getId()))
            return ballots.get(voter.getId());
        else
            return new Ballot("There is not ballot for the voter " + voter.getName());
    }

    public void printVotingResults() {
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