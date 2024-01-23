import utils.Credentials;
import utils.VoterData;

import java.util.*;

public class RegistrationOffice {
    private ElectionCommission electionCommission;
    private List<Integer> voterIds;
    private List<Token> tokens;
    private Map<Voter, VoterData> voterData;
    private static int tokenIndex = 0;

    public RegistrationOffice(ElectionCommission electionCommission) {
        this.electionCommission = electionCommission;
        voterIds = new ArrayList<>();
        voterData = new HashMap<>();
    }

    public void generateId(int amount) {
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            voterIds.add(random.nextInt(10000));
        }
        tokens = electionCommission.getTokens(voterIds);
    }

    public void register(Voter voter) {
        if (!voter.canVote) {
            System.out.printf("The voter %s can't vote\n", voter.getName());
            return;
        }
        if (voterData.containsKey(voter)) {
            System.out.printf("The voter %s has already registered\n", voter.getName());
            return;
        }
        Random random = new Random();
        String login = String.valueOf(random.nextInt());
        String password = String.valueOf(random.nextInt());
        Credentials credentials = new Credentials(login, password);
        voterData.put(voter, new VoterData(credentials, tokens.get(tokenIndex).voterId));
        voter.setCredentialsAndToken(credentials, tokens.get(tokenIndex));
        tokenIndex++;
    }

    public boolean checkCredentials(Credentials credentials) {
        for (VoterData vd : voterData.values()) {
            if (vd.checkCredentials(credentials)) {
                return true;
            }
        }
        return false;
    }
}
