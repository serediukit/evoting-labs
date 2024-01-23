public class Candidate {
    private static int examples = 0;
    private int id;
    private String name;
    private int votes = 0;

    public Candidate(String name) {
        this.name = name;
        id = examples;
        examples++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void incrementVotes() {
        votes++;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return name;
    }
}
