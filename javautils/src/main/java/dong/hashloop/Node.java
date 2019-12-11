package dong.hashloop;

import java.util.HashMap;
import java.util.Map;

/**
 * 节点
 * @author DONGSHILEI
 * @create 2019-10-11 17:52
 * @since 1.0
 */
public class Node {

    //域名
    private String domain;
    //IP地址
    private String ip;
    //节点存储数据
    private Map<String, Object> data;

    public <T> void put(String key, T value) {
        data.put(key, value);
    }

    public void remove(String key) {
        data.remove(key);
    }

    public <T> T get(String key) {
        return (T) data.get(key);
    }

    public Node() {
        this.data = new HashMap<>();
    }

    public Node(String domain, String ip) {
        this.domain = domain;
        this.ip = ip;
        this.data = new HashMap<>();
    }

    public Node(String domain, String ip, Map<String, Object> data) {
        this.domain = domain;
        this.ip = ip;
        this.data = data;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
