public class Ballot {
    private String data;

    public Ballot(String data) {
        this.data = "vote for " + data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
