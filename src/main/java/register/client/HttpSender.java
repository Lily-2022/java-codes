package register.client;

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


}
