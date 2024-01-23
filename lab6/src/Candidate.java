public class Candidate {
    private static int examples = 0;
    private int id;
    private String name;

    public Candidate(String name) {
        this.name = name;
        id = examples;
        examples++;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}
