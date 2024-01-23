public class Voter {
    private String name;
    private Credentials credentials;
    private Token token;

    public Voter(String name) {
        this.name = name;
    }

    public void setCredentialsAndToken(Credentials credentials, Token token) {
        this.credentials = credentials;
        this.token = token;
    }
}
