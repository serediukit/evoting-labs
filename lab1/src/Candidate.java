class Candidate {
    private final String name;
    private int votesCount;

    public Candidate(String name) {
        this.name = name;
        votesCount = 0;
    }

    public String getName() {
        return name;
    }

    public int getVotesCount() {
        return votesCount;
    }

    public void votesInc() {
        votesCount++;
    }
}