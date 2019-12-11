package dong.hashloop;

import java.util.ArrayList;
import java.util.List;

/**
 * 集群
 * @author DONGSHILEI
 * @create 2019-10-11 17:55
 * @since 1.0
 */
public abstract class Cluster {
    protected List<Node> nodes;

    public Cluster() {
        this.nodes = new ArrayList<>();
    }
    /**
     * 添加节点
     */
    public abstract void addNode(Node node);

    /**
     * 删除节点
     * @param node
     */
    public abstract void removeNode(Node node);

    /**
     * 获取节点
     * @param key
     * @return
     */
    public abstract Node get(String key);
}
