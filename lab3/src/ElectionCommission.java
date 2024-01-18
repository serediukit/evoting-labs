import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ElectionCommission {

    private List<BigInteger> registrationList;

    public ElectionCommission(ArrayList<BigInteger> registrationList) {
        this.registrationList = new ArrayList<>(registrationList);
    }

    public void processVotes() {
        for (Map.Entry<String, String> entry : votes.entrySet()) {
            String registrationNumber = entry.getKey();
            String vote = entry.getValue();


            if (registrationList.contains(registrationNumber)) {
                registrationList.put(registrationNumber, vote);
            }
        }
    }

    public Map<String, String> getElectionResults() {
        return registrationList;
    }
}
