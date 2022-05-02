package register.client;


import java.util.UUID;

/**
 * 这个client是一个依赖包，不能独立启动
 * 可以打包发到maven nexus的私服，其他的各个服务都必须依赖他
 * 在服务上被创建和启动，负责跟register-server通信
 *
 */
public class RegisterClient {

    private final String serviceInstanceId;

    public RegisterClient () {
        this.serviceInstanceId = UUID.randomUUID().toString().replace("-", "");
    }

    public void start() {
        //一旦启动这个组建后，就做两个事
        // 1. 开启一个线程向register-server去发送请求，注册这个服务
        // 2. 发送心跳

        new RegisterClientWorker(serviceInstanceId).start();
    }

    private class RegisterClientWorker extends Thread {

        public static final String SERVICE_NAME = "inventory-service";
        public static final String IP = "192.168.13.101";
        public static final String HOST_NAME = "inventory01";
        public static final int PORT = 8008;

        private final HttpSender httpSender;

        private Boolean finishedRegister;

        private String serviceInstanceId;

        public RegisterClientWorker(String serviceInstanceId) {
            this.httpSender = new HttpSender();
            this.finishedRegister = false;
            this.serviceInstanceId = serviceInstanceId;
        }

        public void run() {

            if (!finishedRegister) {
                RegisterRequest request = new RegisterRequest();
                request.setServiceName(SERVICE_NAME);
                request.setHostName(HOST_NAME);
                request.setIp(IP);
                request.setPort(PORT);
                request.setServiceInstanceId(serviceInstanceId);

                RegisterResponse response = httpSender.register(request);
                System.out.println("Register result: " + response.getStatus());
                if (RegisterResponse.SUCCESS.equals(response.getStatus())) {
                    this.finishedRegister = true;
                } else {
                    return;
                }
            }
            if (finishedRegister) {
                HeartbeatRequest heartbeatRequest = new HeartbeatRequest();
                heartbeatRequest.setServiceName(SERVICE_NAME);
                heartbeatRequest.setServiceInstanceId(serviceInstanceId);
                HeartbeatResponse heartbeatResponse = null;
                while (true) {
                    try {
                        heartbeatResponse = httpSender.heartbeat(heartbeatRequest);
                        System.out.println("heartbeat result: " + heartbeatResponse.getStatus());
                        Thread.sleep(30 * 1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

}
