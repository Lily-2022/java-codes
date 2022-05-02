package register.server;


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
    private Lease lease;

    //判断服务实例存活时间
    private static final Long NOT_ALIVE_PERIOD = 90 * 1000L;

    public ServiceInstance() {
        this.lease = new Lease();
    }

    public void renew() {
        this.lease.renew();
    }

    public boolean isAlive() {
        return this.lease.isAlive();
    }

    private class Lease {

        //最近一次心跳时间
        private Long latestHeartbeatTime = System.currentTimeMillis();

        //续约
        public void renew() {
            this.latestHeartbeatTime = System.currentTimeMillis();
            System.out.println("instance [" + serviceInstanceId + "] going to renew " + latestHeartbeatTime);
        }

        public boolean isAlive() {
            Long currentTime = System.currentTimeMillis();
            if (currentTime - latestHeartbeatTime > NOT_ALIVE_PERIOD) {
                System.out.println("instance [" + serviceInstanceId + "] not live any more");
                return false;
            }
            System.out.println("instance [" + serviceInstanceId + "] keep live.");
            return true;
        }
    }


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

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    @Override
    public String toString() {
        return "ServiceInstance{" +
                "serviceName='" + serviceName + '\'' +
                ", ip='" + ip + '\'' +
                ", hostName='" + hostName + '\'' +
                ", port=" + port +
                ", serviceInstanceId='" + serviceInstanceId + '\'' +
                ", lease='" + lease + '\'' +
                '}';
    }
}
