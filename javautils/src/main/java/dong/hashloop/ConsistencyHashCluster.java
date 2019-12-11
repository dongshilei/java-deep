package dong.hashloop;

import cn.hutool.core.util.NetUtil;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.IntStream;

import static java.util.Objects.hash;

/**
 * @author DONGSHILEI
 * @create 2019-10-11 17:58
 * @since 1.0
 */
public class ConsistencyHashCluster extends Cluster {

    private SortedMap<Long, Node> virNodes = new TreeMap<>();
    private static final int VIR_NODE_COUNT = 512;
    private static final String SPLIT = "#";

    public ConsistencyHashCluster() {
        super();
    }

    /**
     * 添加节点
     * 添加一个物理节点，同步添加512个虚拟节点
     *
     * @param node
     */
    @Override
    public void addNode(Node node) {
        this.nodes.add(node);
        IntStream.range(0, VIR_NODE_COUNT).forEach(index -> {
            long hash = Math.abs(hash(NetUtil.ipv4ToLong(node.getIp()) + SPLIT + index + node.getDomain()));
            virNodes.put(hash, node);
        });
    }

    /**
     * 删除节点
     * 删除一个物理节点，同步删除512个虚拟节点
     * @param node
     */
    @Override
    public void removeNode(Node node) {
        nodes.removeIf(o -> node.getIp().equals(o.getIp()));
        IntStream.range(0, VIR_NODE_COUNT).forEach(index -> {
            long hash = Math.abs(hash(NetUtil.ipv4ToLong(node.getIp()) + SPLIT + index + node.getDomain()));
            virNodes.remove(hash);
        });
    }

    /**
     * 获取节点
     *  根据Key的hash值，映射到环的节点，然后顺时针方向找，找到离自己最近的一个物理节点并返回
     * @param key
     * @return
     */
    @Override
    public Node get(String key) {
        long hash = Math.abs(hash(key));
        SortedMap<Long, Node> subMap = hash >= virNodes.lastKey() ? virNodes.tailMap(0L) : virNodes.tailMap(hash);
        if (subMap.isEmpty()) {
            return null;
        }
        return subMap.get(subMap.firstKey());
    }
}
