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
        if (credentials == null) {
            System.out.printf("The voter %s is not registered\n", name);
            return;
        }
        application = new Application(credentials, token);
    }

    public void vote(Candidate candidate) {
        if (credentials == null) {
            System.out.printf("The voter %s is not registered\n", name);
            return;
        }
        if (application == null) {
            System.out.printf("The voter %s is not authorized\n", name);
            return;
        }
        application.vote(candidate);
    }

    public String getName() {
        return name;
    }
}
