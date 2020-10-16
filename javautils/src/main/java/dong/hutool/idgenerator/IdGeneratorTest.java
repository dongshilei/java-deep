package dong.hutool.idgenerator;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.NetUtil;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author DONGSHILEI
 * @create 2019-10-11 10:56
 * @since 1.0
 */
public class IdGeneratorTest {

    Logger log = LoggerFactory.getLogger(IdGenerator.class);

    private IdGenerator idGenerator;

    @Before
    public void init() {
        idGenerator = new IdGenerator();
    }

    @Test
    public void testIP2Long(){
        long toLong = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        System.out.println("当前机器 toLong: "+ toLong);
    }

    @Test
    public void testBatchId() {
        for (int i = 0; i < 100; i++) {
            String batchId = idGenerator.batchId(1001, 100);
            System.out.println("批次号: " + batchId);
        }
    }
    @Test
    public void testBatchId2() {
        for (int i = 0; i < 100; i++) {
            String batchId = idGenerator.batchId(IdGenerator.ipv4ToLong);
            System.out.println("批次号: " + batchId);
        }
    }

    @Test
    public void testBatchId3() {

        for (int i = 0; i < 100; i++) {
            long time = DateTime.now().getTime();
            System.out.println(time);
        }
    }


    @Test
    public void testSimpleUUID() {
        for (int i = 0; i < 100; i++) {
            String simpleUUID = idGenerator.simpleUUID();
            log.info("simpleUUID: {}", simpleUUID);
            System.out.println("simpleUUID: " + simpleUUID);
        }
    }

    @Test
    public void testRandomUUID() {
        for (int i = 0; i < 100; i++) {
            String randomUUID = idGenerator.randomUUID();
            log.info("randomUUID: {}", randomUUID);
            System.out.println("randomUUID: " + randomUUID);
        }
    }

    @Test
    public void testObjectID() {
        for (int i = 0; i < 100; i++) {
            String objectId = idGenerator.objectId();
            log.info("objectId: {}", objectId);
            System.out.println("objectId: " + objectId);
        }
    }

    @Test
    public void testSnowflakeId() {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                log.info("分布式 ID: {}", idGenerator.snowflakeId());
                System.out.println("分布式 ID: " + idGenerator.snowflakeId());
            });
        }
        executorService.shutdown();
    }

    @Test
    public void testIpv4ToLong() {
        long ipLong = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr());
        System.out.println("转换前IP : " + NetUtil.getLocalhostStr());
        System.out.println("转换后IP : " + ipLong);
        long ip1 = NetUtil.ipv4ToLong("10.67.2.2");
        long ip2 = NetUtil.ipv4ToLong("10.24.190.144");
        long ip3 = NetUtil.ipv4ToLong("10.25.129.115");
        long ip4 = NetUtil.ipv4ToLong("10.66.88.192");
        long ip5 = NetUtil.ipv4ToLong("10.80.86.176");
        long ip6 = NetUtil.ipv4ToLong("10.66.176.175");
        System.out.println("转换后IP : " + ip1);
        System.out.println("转换后IP : " + ip2);
        System.out.println("转换后IP : " + ip3);
        System.out.println("转换后IP : " + ip4);
        System.out.println("转换后IP : " + ip5);
        System.out.println("转换后IP : " + ip6);
        System.out.println("转换后IP : " + NetUtil.ipv4ToLong("255.255.255.255"));
    }
    @Test
    public void test(){
        AtomicInteger atomicInteger = new AtomicInteger(10000000);
        for (int ii = 0 ; ii< 100; ii++) {
            int i = atomicInteger.incrementAndGet();
            if (i>10000003) {
                atomicInteger = new AtomicInteger(10000000);
            }
            System.out.println(i);
        }
    }
}
