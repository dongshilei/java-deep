package dong.hutool.idgenerator;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
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
    public void testBatchId() {
        for (int i = 0; i < 100; i++) {
            String batchId = idGenerator.batchId(1001, 100);
            log.info("批次号: {}", batchId);
            System.out.println("批次号: " + batchId);
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

        Snowflake snowflake = IdUtil.createSnowflake(ipLong, 1);
        for (int i = 0 ; i < 20 ; i ++) {
            System.out.println(snowflake.nextId());
        }
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
