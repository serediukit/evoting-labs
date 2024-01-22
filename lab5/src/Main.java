import java.util.List;

public class Main {
    public static void main(String[] args) {
        int[] votes = new int[] { 1, 0, 2, 3, 2, 1, 2, 0, 2, 3 };

        List<Candidate> candidates = DataFactory.getCandidates(4);
        List<Voter> voters = DataFactory.getVoters(10);
        CentralElectionCommission centralElectionCommission = new CentralElectionCommission();
        centralElectionCommission.setCandidates(candidates);
        centralElectionCommission.setVoters(voters);
        ElectionCommission electionCommissionA = new ElectionCommission();
        ElectionCommission electionCommissionB = new ElectionCommission();

//        voters.get(1).canVote = false;

        for (int i = 0; i < votes.length; i++) {
            voters.get(i).createBallots(candidates.get(votes[i]).getId());
            voters.get(i).encryptBallots(centralElectionCommission.getPublicKey());
            voters.get(i).createVoteMessages();
            voters.get(i).signMessages();
            VoteMessage[] voteMessages = voters.get(i).getVoteMessages();
            electionCommissionA.sendVoteMessage(voteMessages[0]);
            electionCommissionB.sendVoteMessage(voteMessages[1]);

//            if (i == 2) {
//                voters.get(i).createBallots(candidates.get(0).getId());
//                voters.get(i).encryptBallots(centralElectionCommission.getPublicKey());
//                voters.get(i).createVoteMessages();
//                voters.get(i).signMessages();
//                voteMessages = voters.get(i).getVoteMessages();
//                electionCommissionA.sendVoteMessage(voteMessages[0]);
//                electionCommissionB.sendVoteMessage(voteMessages[1]);
//            }
        }

        centralElectionCommission.sendVotesListFromElectionCommissions(
                electionCommissionA.getVotes(),
                electionCommissionB.getVotes()
        );

        centralElectionCommission.printResult();
    }
}