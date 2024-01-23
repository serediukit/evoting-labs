import java.util.*;

public class RegistrationOffice {
    private ElectionCommission electionCommission;
    private List<Integer> voterIds;
    private List<Token> tokens;
    private List<VoterData> voterData;
    private static int tokenIndex = 0;

    public RegistrationOffice(ElectionCommission electionCommission) {
        this.electionCommission = electionCommission;
        voterIds = new ArrayList<>();
        voterData = new ArrayList<>();
    }

    public void generateId(int amount) {
        Random random = new Random();
        for (int i = 0; i < amount; i++) {
            voterIds.add(random.nextInt(10000));
        }
        tokens = electionCommission.getTokens(voterIds);
    }

    public void register(Voter voter) {
        Random random = new Random();
        String login = String.valueOf(random.nextInt());
        String password = String.valueOf(random.nextInt());
        Credentials credentials = new Credentials(login, password);
        voterData.add(new VoterData(credentials, tokens.get(tokenIndex).voterId));
        voter.setCredentialsAndToken(credentials, tokens.get(tokenIndex));
        tokenIndex++;
    }
}
