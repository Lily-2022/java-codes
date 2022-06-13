package register.client;


/**
 * 代表一个服务实例
 * 包含服务名称，IP地址，hostname， 端口号，实例ID
 * 以及契约
 */
public class ServiceInstance {

    private String serviceName;
    private String ip;
    private String hostName;
    private int port;
    private String serviceInstanceId;

    //判断服务实例存活时间
    private static final Long NOT_ALIVE_PERIOD = 90 * 1000L;

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServiceInstanceId() {
        return serviceInstanceId;
    }

    public void setServiceInstanceId(String serviceInstanceId) {
        this.serviceInstanceId = serviceInstanceId;
    }

    @Override
    public String toString() {
        return "ServiceInstance{" +
                "serviceName='" + serviceName + '\'' +
                ", ip='" + ip + '\'' +
                ", hostName='" + hostName + '\'' +
                ", port=" + port +
                ", serviceInstanceId='" + serviceInstanceId + '\'' +
                '}';
    }
}
