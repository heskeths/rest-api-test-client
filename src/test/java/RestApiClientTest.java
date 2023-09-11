import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestApiClientTest {

    List<Long> durationList = new ArrayList<>();
    @BeforeEach
    void setUp() {
        for (long l = 1; l <= 100; l++) {
            durationList.add(l);
        }
    }

    @Test
    void countDurationLevelTest() {
        assertEquals(40, RestApiClient.countDurationLevel(durationList, 61));
    }

    @Test
    void percentageDurationLevelTest() {
        assertEquals(40d, RestApiClient.countDurationLevel(durationList, 61));
    }

    @Test
    void percentileTest() {
        assertEquals(90, RestApiClient.percentile(durationList, 90));
    }
}