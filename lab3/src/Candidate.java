public class Candidate {
    private static int examples = 0;
    private final int id;
    private final String name;
    private int votesCount = 0;

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

    public int getVotesCount() {
        return votesCount;
    }

    public void incrementVotes() {
        votesCount++;
    }
}
