package fontys.s3.carspacebackend.domain;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


class TimeHelperTest {
    @BeforeEach()
    void freezeTime(){
        TimeHelper.EnterDebugMode();
    }

    @Test
    void testFreezeAndGetTime(){
        Instant time = TimeHelper.Now();
        Instant expectedTime = Instant.parse("2022-07-10T15:30:00.00Z");
        assertEquals(expectedTime, time);
    }

    @Test
    void testFreezeAndGetTimes(){
        Instant expectedTime = Instant.parse("2022-09-10T15:30:00.00Z");
        TimeHelper.SetTime("2022-09-10T15:30:00.00Z");
        Instant time = TimeHelper.Now();
        assertEquals(expectedTime, time);
        //reset back to default
        TimeHelper.Reset();
    }

    @AfterEach
    public void resetTime(){
        TimeHelper.QuitDebugMode();
    }

}
