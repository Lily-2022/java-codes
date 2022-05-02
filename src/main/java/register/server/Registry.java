package register.server;


import java.util.HashMap;
import java.util.Map;

/**
 * 接受各个服务的请求
 * 是可以对部署和启动的，启动以后是以一个web工程的方式来启动。启动后就监听各个服务发来的请求
 *  注册，心跳，下线
 *
 */
public class Registry {

    private static Registry instance = new Registry();
    private Registry() {}

    /**
     * 核心内存数据结构注册表
     * registry 是以service name为key,然后value是serviceInstanceMap
     *
     * serviceInstanceMap 是以serviceInstanceId为key, value 是一个具体的serviceInstance
     *
     */

    private Map<String, Map<String, ServiceInstance>> registry = new HashMap<String, Map<String, ServiceInstance>>();

    public static Registry getInstance() {
        return instance;
    }

    /**
     * 获取整个注册表
     * @return
     */
    public Map<String, Map<String, ServiceInstance>> getRegistry() {
        return registry;
    }

    //服务注册
    public void register(ServiceInstance serviceInstance) {
        Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceInstance.getServiceName());
        if (serviceInstanceMap == null) {
            serviceInstanceMap = new HashMap<String, ServiceInstance>();
            registry.put(serviceInstance.getServiceName(), serviceInstanceMap);
        }

        serviceInstanceMap.put(serviceInstance.getServiceInstanceId(), serviceInstance);
        System.out.println("instance: " + serviceInstance + " completed register");
        System.out.println("register map: " + registry);
    }

    /**
     * 获取服务实例
     * @param serviceName
     * @param serviceInstanceId
     * @return
     */
    public ServiceInstance getServiceInstance(String serviceName, String serviceInstanceId) {
        Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceName);
        return serviceInstanceMap.get(serviceInstanceId);
    }

    public void remove(String serviceName, String serviceInstanceId) {
        Map<String, ServiceInstance> serviceInstanceMap = registry.get(serviceName);
        serviceInstanceMap.remove(serviceInstanceId);
        System.out.println("going to remove instance: " + serviceInstanceId + " ....");
    }
}
