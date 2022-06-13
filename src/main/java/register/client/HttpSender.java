package register.client;

import java.util.HashMap;
import java.util.Map;

public class HttpSender {

    public RegisterResponse register(RegisterRequest request) {
        //服务名称，IP地址，端口号
        System.out.println("instance [" + request + "] send register request...");


        RegisterResponse response = new RegisterResponse();

        response.setStatus(RegisterResponse.SUCCESS);

        return response;


    }

    public HeartbeatResponse heartbeat(HeartbeatRequest request) {
        //服务名称，IP地址，端口号

        System.out.println("instance [" + request.getServiceInstanceId() + "] send heartbeat...");
        HeartbeatResponse response = new HeartbeatResponse();

        response.setStatus(RegisterResponse.SUCCESS);

        return response;
    }

    public Map<String, Map<String, ServiceInstance>> fetchServiceRegistry() {
        Map<String, Map<String, ServiceInstance>> registry = new HashMap<String, Map<String, ServiceInstance>>();
        ServiceInstance serviceInstance = new ServiceInstance();
        serviceInstance.setServiceName("FINANCE-SERVICE");
        serviceInstance.setIp("192.168.10.120");
        serviceInstance.setHostName("finance-service-01");
        serviceInstance.setPort(9080);
        serviceInstance.setServiceInstanceId("FINANCE-SERVICE-192.168.10.120:9080");

        Map<String, ServiceInstance> serviceInstanceMap = new HashMap<String, ServiceInstance>();

        serviceInstanceMap.put("FINANCE-SERVICE-192.168.10.120:9080", serviceInstance);

        registry.put("FINANCE-SERVICE", serviceInstanceMap);
        System.out.println("get registry list: " + registry);
        return registry;


    }


}
