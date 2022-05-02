package register.server;

public class RegisterServerController {

    private Registry registry = Registry.getInstance();

    public RegisterResponse register(RegisterRequest request) {
        RegisterResponse response = new RegisterResponse();
        try{
            ServiceInstance serviceInstance = new ServiceInstance();
            serviceInstance.setHostName(request.getHostName());
            serviceInstance.setIp(request.getIp());
            serviceInstance.setPort(request.getPort());
            serviceInstance.setServiceInstanceId(request.getServiceInstanceId());
            serviceInstance.setServiceName(request.getServiceName());

            registry.register(serviceInstance);

            response.setStatus(RegisterResponse.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(RegisterResponse.FAILURE);
        }

        return response;
    }

    public HeartbeatResponse heartbeat (HeartbeatRequest request) {
        HeartbeatResponse response = new HeartbeatResponse();
        try {
            ServiceInstance serviceInstance = registry.getServiceInstance(request.getServiceName(), request.getServiceInstanceId());
            serviceInstance.renew();

            response.setStatus(HeartbeatResponse.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HeartbeatResponse.FAILURE);
        }
        return response;
    }

}
