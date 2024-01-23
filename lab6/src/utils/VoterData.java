package utils;

public class VoterData {
    public Credentials credentials;
    public int tokenId;

    public VoterData(Credentials credentials, int tokenId) {
        this.credentials = credentials;
        this.tokenId = tokenId;
    }

    public boolean checkCredentials(Credentials credentials) {
        if (credentials == null)
            return false;
        return credentials.login.equals(this.credentials.login)
                && credentials.password.equals(this.credentials.password);
    }
}
