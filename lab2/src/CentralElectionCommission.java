import java.security.*;
import java.util.*;

import CantVoteException.*;


class CentralElectionCommission {
    private Integer candidatesCount = 0;
    private Integer votersCount = 0;
    private KeyPair keys;
    private final Map<Integer, Candidate> candidates;
    private final Map<Integer, Voter> voters;
    private final ArrayList<Boolean> isVoterChecked;
    private final ArrayList<Ballot> ballots;

    public CentralElectionCommission(KeyPair keyPair) {
        candidates = new HashMap<>();
        voters = new HashMap<>();
        isVoterChecked = new ArrayList<>();
        ballots = new ArrayList<>();
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
        for (Ballot ballot : ballots) {
            String decryptedData = ballot.getDecryptedData(keys.getPrivate());
            String[] data = decryptedData.split(" ");
            int voterId = Integer.parseInt(data[0]);
            int vote = Integer.parseInt(data[1]);
            candidates.get(vote).votesInc();
            voters.get(voterId).makeCounted();
        }

        printVotingResults();
    }

    public void sendBallot(Voter voter, Ballot ballot) {
        try {
            if (ballot != null) {
                if (!ballot.isSigned())
                    throw new BallotIsNotSignedException();
                String decryptedData = ballot.getDecryptedData(keys.getPrivate());
                String[] data = decryptedData.split(" ");
                int voterId = Integer.parseInt(data[0]);
                int vote = Integer.parseInt(data[1]);
                if (voter.getId() != voterId)
                    throw new OtherVoterException(voter.getName());
                if (checkBallotData(voter, vote)) {
                    ballots.add(ballot);
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

    public ArrayList<Ballot> getSignedBallot(ArrayList<ArrayList<Ballot>> ballotsList, PrivateKey key) {
        Random r = new Random();
        int randIndex = r.nextInt(ballotsList.size());
        try {
            if (ballotsList.isEmpty())
                throw new ExamplesDoesNotExistException();
            int voterId = -1;
            int prevVoterId = -1;
            for (int i = 0; i < ballotsList.size(); i++) {
                if (i != randIndex) {
                    for (int j = 0; j < ballotsList.get(i).size(); j++) {
                        String decryptedData = ballotsList.get(i).get(j).getDecryptedData(key);
                        String[] data = decryptedData.split(" ");
                        voterId = Integer.parseInt(data[0]);
                        if (voterId != prevVoterId && prevVoterId != -1)
                            throw new IncorrectBallotsList();
                        if (Integer.parseInt(data[1]) != j)
                            throw new BallotIsNotValidException(decryptedData);
                        if (isVoterChecked.get(voterId))
                            throw new VoterAlreadyHasSignedBallots();
                        prevVoterId = voterId;
                    }
                }
            }
            ArrayList<Ballot> signedBallots = new ArrayList<>();
            for (int i = 0; i < ballotsList.get(randIndex).size(); i++) {
                Ballot signedBallot = ballotsList.get(randIndex).get(i);
                signedBallot.makeSigned();
                signedBallots.add(signedBallot);
            }
            isVoterChecked.set(voterId, true);
            return signedBallots;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public int getCandidatesCount() {
        return candidatesCount;
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