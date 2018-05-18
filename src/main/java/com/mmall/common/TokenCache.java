package com.mmall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author liaocx  on 2017/11/27.
 */
@Slf4j
public class TokenCache {

    public static final String TOKEN_PREFIX = "token_";

    /**
     * 新建了名为localCache的一个缓存对象，maximumSize定义了缓存的容量大小，当缓存数量!即将!到达容量上线时，则会进行缓存回收，回收最近没有使用
     * 或总体上很少使用的缓存项。需要注意的是在接近这个容量上限时就会发生，所以在定义这个值的时候需要视情况适量地增大一点。
     * 通过expireAfterWrite这个方法定义了缓存的过期时间，写入2h之后过期。
     * expireAfterAccess(long, TimeUnit)  这个方法是根据某个键值对最后一次访问之后多少时间后移除
     * expireAfterWrite(long, TimeUnit)  这个方法是根据某个键值对被创建或值被替换后多少时间移除
     * 在build方法里，传入了一个CacheLoader对象，重写了其中的load方法。默认的数据加载实现，当获取的缓存值不存在或已过期时，则会调用此load方法，进行缓存值的计算
     */
    private static LoadingCache<String,String> localCache = CacheBuilder.newBuilder().initialCapacity(1000)
            .maximumSize(10000).expireAfterWrite( 2, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return null;
                }
            });

    public static void setKey(String key, String value) {
        localCache.put(key, value);
    }

    public static String getKey(String key) {
        try {
            return localCache.get(key);
        } catch (Exception e) {
            log.error("localCache get error", e);
        }
        return null;
    }

    public static void remove(String key) {
        try {
            localCache.invalidate(key);
        } catch (Exception e) {
            log.error("localCache remove error", e);
        }
    }
}
