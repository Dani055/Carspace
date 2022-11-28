package fontys.s3.carspacebackend.domain;

import java.time.Instant;

public final class TimeHelper {
    private static boolean debugMode = false;
    private static Instant debugTime = Instant.parse("2022-07-10T15:30:00.00Z");
    private TimeHelper() {
        throw new IllegalStateException("Utility class");
    }
    public static Instant Now(){
        if(debugMode){
            return debugTime;
        }
        return Instant.now();
    }
    public static void Reset(){
        debugTime = Instant.parse("2022-07-10T15:30:00.00Z");
    }
    public static void EnterDebugMode(){
        debugMode = true;
    }
    public static void QuitDebugMode(){
        debugMode = false;
    }
    public static void SetTime(String s){
        debugTime = Instant.parse(s);
    }
}
