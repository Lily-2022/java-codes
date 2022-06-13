package register.client;

import java.util.HashMap;
import java.util.Map;

public class ClientCachedServiceRegistry {
    private final Daemon daemon;
    private final RegisterClient registerClient;
    private final HttpSender httpSender;

    public ClientCachedServiceRegistry(RegisterClient registerClient, HttpSender httpSender) {
        this.daemon = new Daemon();
        this.registerClient = registerClient;
        this.httpSender = httpSender;
    }

    private Map<String, Map<String, ServiceInstance>> registry = new HashMap<String, Map<String, ServiceInstance>>();

    public void initialize() {
        this.daemon.start();
    }

    public void destroy() {
        this.daemon.interrupt();
    }

    //定时拉取注册表来缓存
    private class Daemon extends Thread {
        @Override
        public void run() {
            while (registerClient.isRunning()) {
                try {
                    registry = httpSender.fetchServiceRegistry();
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Map<String, Map<String, ServiceInstance>> getRegistry() {
        return registry;
    }
}
