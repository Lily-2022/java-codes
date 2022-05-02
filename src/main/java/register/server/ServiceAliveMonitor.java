package register.server;

import java.util.Map;

/**
 * 微服务存活状态监控组件
 */

public class ServiceAliveMonitor {

    private static final Long CHECK_ALIVE_INTERVAL = 60 * 1000L;
    private Daemon daemon = new Daemon();

    public void start() {
        daemon.start();
    }

    private class Daemon extends Thread {

        private Registry registry = Registry.getInstance();

        public void run() {
            Map<String, Map<String, ServiceInstance>> registryMap = null;
            while (true) {
                try {
                    registryMap = registry.getRegistry();
                    for (String serviceName : registryMap.keySet()) {
                        Map<String, ServiceInstance> serviceInstanceMap = registryMap.get(serviceName);

                        for (ServiceInstance serviceInstance : serviceInstanceMap.values()) {
                            //说明服务不存活来，需要从registry map里摘除
                            if (!serviceInstance.isAlive()) {
                                registry.remove(serviceName, serviceInstance.getServiceInstanceId());
                            }
                        }
                    }

                    Thread.sleep(CHECK_ALIVE_INTERVAL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
