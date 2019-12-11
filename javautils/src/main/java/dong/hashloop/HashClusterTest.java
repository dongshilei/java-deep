package dong.hashloop;

import org.junit.Test;

import java.util.stream.IntStream;

/**
 * https://blog.csdn.net/WANGYAN9110/article/details/70185652#commentBox
 *
 * @author DONGSHILEI
 * @create 2019-10-11 18:19
 * @since 1.0
 */
public class HashClusterTest {

    private static final int DATA_COUNT = 500000;
    private static final String PRE_KEY = "key_";

    @Test
    public void consistencyTest() {
        Cluster cluster = new ConsistencyHashCluster();
        cluster.addNode(new Node("c1.yywang.info", "192.168.0.1"));
        cluster.addNode(new Node("c2.yywang.info", "192.168.0.2"));
        cluster.addNode(new Node("c3.yywang.info", "192.168.0.3"));
        cluster.addNode(new Node("c4.yywang.info", "192.168.0.4"));

        IntStream.range(0, DATA_COUNT).forEach(index -> {
            Node node = cluster.get(PRE_KEY + index);
            node.put(PRE_KEY + index, "Test Data");
        });
        //数据分布
        distributed(cluster);
        //缓存命中率
        hitRate(cluster);

        // 增加一个节点
        cluster.addNode(new Node("c5.yywang.info", "192.168.0.5"));
        //数据分布
        distributed(cluster);
        //缓存命中率
        hitRate(cluster);

        // 删除一个节点
        cluster.removeNode(new Node("c1.yywang.info", "192.168.0.1"));
        //数据分布
        distributed(cluster);
        //缓存命中率
        hitRate(cluster);
        // 删除一个节点
        cluster.removeNode(new Node("c3.yywang.info", "192.168.0.3"));
        //数据分布
        distributed(cluster);
        //缓存命中率
        hitRate(cluster);
    }

    private void hitRate(Cluster cluster) {
        //缓存命中率
        long hitCount = IntStream.range(0, DATA_COUNT)
                .filter(index -> cluster.get(PRE_KEY + index).get(PRE_KEY + index) != null)
                .count();
        System.out.println("缓存命中率：" + hitCount * 1f / DATA_COUNT);
    }

    private void distributed(Cluster cluster) {
        System.out.println("数据分布情况：");
        cluster.nodes.forEach(n->{
            System.out.println("IP:"+n.getIp()+",数据量:"+n.getData().size());
        });
    }
}
