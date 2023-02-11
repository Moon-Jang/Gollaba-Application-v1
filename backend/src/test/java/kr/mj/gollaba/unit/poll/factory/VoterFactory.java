package kr.mj.gollaba.unit.poll.factory;

import kr.mj.gollaba.poll.entity.Option;
import kr.mj.gollaba.poll.entity.Voter;
import kr.mj.gollaba.poll.type.PollingResponseType;
import kr.mj.gollaba.user.entity.User;

public class VoterFactory {

    private static final Long TEST_ID = 1L;
    public static final Long TEST_OPTION_ID = 1L;
    public static final Long TEST_USER_ID = 1L;
    public static final String TEST_VOTER_NAME = "익명1";
    public static final String TEST_IP_ADDRESS = "127.0.0.1";

    public static Voter create(Option option, User user) {
        Voter voter = Voter.builder()
                .voterName(TEST_VOTER_NAME)
                .ipAddress(TEST_IP_ADDRESS)
                .build();

        if (option != null) {
            voter.vote(option);
        }

        if (user != null) {
            voter.registerUser(user);
        }

        return voter;
    }

    public static Voter createWithId(Option option, User user) {
        Voter voter = Voter.builder()
                .id(TEST_ID)
                .voterName(TEST_VOTER_NAME)
                .ipAddress(TEST_IP_ADDRESS)
                .build();

        voter.vote(option);

        if (user != null) {
            voter.registerUser(user);
        }

        return voter;
    }
}
