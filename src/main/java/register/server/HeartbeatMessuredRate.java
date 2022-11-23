package register.server;

public class HeartbeatMessuredRate {

    private static final HeartbeatMessuredRate instance = new HeartbeatMessuredRate();

    private long latestMinuteTimestamp = System.currentTimeMillis();

    private long latestMinuteHeartbeatRate = 0L;

    public static HeartbeatMessuredRate getInstance() {
        return instance;
    }

    public synchronized void increment() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - latestMinuteTimestamp > 60 * 1000) {
            latestMinuteHeartbeatRate = 0L;
            this.latestMinuteTimestamp = System.currentTimeMillis();
        }
        latestMinuteHeartbeatRate ++;
    }

    public synchronized long get() {
        return latestMinuteHeartbeatRate;
    }
}
