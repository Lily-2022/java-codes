package register.client;


import java.util.UUID;

/**
 * 这个client是一个依赖包，不能独立启动
 * 可以打包发到maven nexus的私服，其他的各个服务都必须依赖他
 * 在服务上被创建和启动，负责跟register-server通信
 */
public class RegisterClient {

    private final String serviceInstanceId;

    private static final Long HEARTBEAT_INTERVAL = 30 * 1000L;

    public static final String SERVICE_NAME = "inventory-service";
    public static final String IP = "192.168.13.101";
    public static final String HOST_NAME = "inventory01";
    public static final int PORT = 8008;

    private final HttpSender httpSender;

    private final HeartbeatWorker heartbeatWorker;

    private ClientCachedServiceRegistry cachedServiceRegistry;

    private volatile boolean isRunning;

    public RegisterClient() {
        this.httpSender = new HttpSender();
        this.serviceInstanceId = UUID.randomUUID().toString().replace("-", "");
        this.heartbeatWorker = new HeartbeatWorker();
        this.isRunning = true;
        this.cachedServiceRegistry = new ClientCachedServiceRegistry(this, httpSender);
    }

    public void start() {
        //一旦启动这个组建后，就做两个事
        // 1. 开启一个线程向register-server去发送请求，注册这个服务
        // 2. 发送心跳
        try {
            RegisterWorker registerWorker = new RegisterWorker();
            registerWorker.start();
            registerWorker.join();//main 会等register结束后再启动心跳线程

            heartbeatWorker.start();
            this.cachedServiceRegistry.initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void shutdown() {
        this.isRunning = false;
        this.heartbeatWorker.interrupt();
        this.cachedServiceRegistry.destroy();
        this.httpSender.unregister(SERVICE_NAME, serviceInstanceId);
    }

    private class HeartbeatWorker extends Thread {
        public void run() {
            HeartbeatRequest heartbeatRequest = new HeartbeatRequest();
            heartbeatRequest.setServiceName(SERVICE_NAME);
            heartbeatRequest.setServiceInstanceId(serviceInstanceId);
            HeartbeatResponse heartbeatResponse = null;
            while (isRunning) {
                try {
                    heartbeatResponse = httpSender.heartbeat(heartbeatRequest);
                    System.out.println("heartbeat result: " + heartbeatResponse.getStatus());
                    Thread.sleep(HEARTBEAT_INTERVAL);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class RegisterWorker extends Thread {

        public void run() {

            RegisterRequest request = new RegisterRequest();
            request.setServiceName(SERVICE_NAME);
            request.setHostName(HOST_NAME);
            request.setIp(IP);
            request.setPort(PORT);
            request.setServiceInstanceId(serviceInstanceId);

            RegisterResponse response = httpSender.register(request);
            System.out.println("Register result: " + response.getStatus());
        }
    }

    public Boolean isRunning() {
        return isRunning;
    }

}
