package register.server;

import java.util.UUID;

public class RegisterServerControllerTest {

    public static void main(String[] args) throws Exception{
        RegisterServerController serverController = new RegisterServerController();

        String serviceInstanceId = UUID.randomUUID().toString().replace("-", "");

        RegisterRequest request = new RegisterRequest();
        request.setHostName("test01");
        request.setIp("192.168.10.102");
        request.setPort(8009);
        request.setServiceInstanceId(serviceInstanceId);
        request.setServiceName("test-service");

        serverController.register(request);

        //模拟发送心跳进行续约
        HeartbeatRequest heartbeatRequest = new HeartbeatRequest();
        heartbeatRequest.setHostName("test01");
        heartbeatRequest.setIp("192.168.10.102");
        heartbeatRequest.setPort(8009);
        heartbeatRequest.setServiceInstanceId(serviceInstanceId);
        heartbeatRequest.setServiceName("test-service");

        serverController.heartbeat(heartbeatRequest);

        //开启一个后台线程，检查微服务的存活状态

        ServiceAliveMonitor serviceAliveMonitor = new ServiceAliveMonitor();
        serviceAliveMonitor.start();


        while (true) {
            Thread.sleep(30 * 1000);
        }
    }

}