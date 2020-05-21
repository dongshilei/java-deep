package dong.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.jupiter.api.Test;

/**
 * @program: java-deep
 * @description
 * @author: DONGSHILEI
 * @create: 2020/5/12 23:12
 **/
public class CacheTest {

    @Test
    public void test1() {
        Cache<Object, Object> build = CacheBuilder.newBuilder().build();
        build.put("aa","1245");
        Object aa = build.getIfPresent("aa");
        System.out.println(aa);
    }
}
