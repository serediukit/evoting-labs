import CantVoteException.*;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationOffice {
    private final ElectionCommission electionCommission;
    private final Map<Voter, BigInteger> registrationList;

    public RegistrationOffice(ElectionCommission electionCommission) {
        this.electionCommission = electionCommission;
        registrationList = new HashMap<>();
    }

    public BigInteger registerVoter(Voter voter) {
        try {
            if (registrationList.containsKey(voter)) {
                throw new VoterHasAlreadyRegisteredException(voter.getName());
            }
            if (!voter.canVote()) {
                throw new CantVoteException(voter.getName());
            }
            BigInteger registrationNumber = generateRegistrationNumber();
            registrationList.put(voter, registrationNumber);
            electionCommission.sendRegistrationList(registrationList.values().stream().toList());
            return registrationNumber;
        } catch (VoterHasAlreadyRegisteredException | CantVoteException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public List<BigInteger> getRegistrationList() {
        return registrationList.values().stream().toList();
    }

    private BigInteger generateRegistrationNumber() {
        SecureRandom random = new SecureRandom();
        BigInteger res;
        do {
            res = new BigInteger(64, random);
        } while (registrationList.containsValue(res));
        return res;
    }
}
