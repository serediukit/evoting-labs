public class Voter {
    private String name;
    private Credentials credentials;
    private Token token;
    private Program program;

    public Voter(String name) {
        this.name = name;
    }

    public void setCredentialsAndToken(Credentials credentials, Token token) {
        this.credentials = credentials;
        this.token = token;
    }

    public void install() {
        program = new Program();
        program.setCredentials(credentials);
        program.setToken(token);
    }

    public void vote(Candidate candidate) {
        program.vote(candidate);
    }
}
