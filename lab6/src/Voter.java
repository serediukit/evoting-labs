import utils.Credentials;

public class Voter {
    private String name;
    private Credentials credentials;
    private Token token;
    private Application application;

    public boolean canVote = true;

    public Voter(String name) {
        this.name = name;
    }

    public void setCredentialsAndToken(Credentials credentials, Token token) {
        this.credentials = credentials;
        this.token = token;
    }

    public void install() {
        application = new Application(credentials, token);
    }

    public void vote(Candidate candidate) {
        application.vote(candidate);
    }
}
