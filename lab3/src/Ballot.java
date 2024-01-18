public class Ballot {
    private String data;

    public Ballot(Voter voter, int vote) {
        data = String.valueOf(vote);
    }

    public Ballot(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
