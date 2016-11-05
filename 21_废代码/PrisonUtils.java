* 
    package com.newings.util;

    import java.util.List;

    import javax.annotation.PostConstruct;

    import org.apache.commons.lang3.StringUtils;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.cache.Cache.ValueWrapper;
    import org.springframework.cache.CacheManager;
    import org.springframework.cache.guava.GuavaCache;

    import com.newings.entity.model.Prison;
    import com.newings.service.PrisonService;

    // 加上以后会在启动的时候自动加载, 和 DictUtils 一样
    // 去掉以后在调用的时候加载和初始化
    // @Service
    public class PrisonUtil {

      @Autowired
      private PrisonService prisonService;
      private static CacheManager cacheManager;
      private static GuavaCache prisonCache;

      @PostConstruct
      public static GuavaCache initPrisonCache() {
        prisonCache = (GuavaCache) cacheManager.getCache("prisonCache");
        return prisonCache;
      }

      @PostConstruct
      public void init() {
        if (prisonCache == null) {
          prisonCache = initPrisonCache();
        }
        // 将字典信息放入缓存
        List<Prison> prisons = prisonService.findAll();
        prisonCache.getNativeCache().cleanUp();
        for (Prison prison : prisons) {
          String key = prison.getId();
          prisonCache.put(key, prison.getName());
        }
      }

      public static String getPrisonValue(String prisonId) {
        if (prisonCache == null) {
          prisonCache = initPrisonCache();
        }
        if (StringUtils.isBlank(prisonId)) {
          return "";
        }
        if (!StringUtils.isBlank(prisonId)) {
          ValueWrapper valueWrapper = prisonCache.get(prisonId);
          if (null != valueWrapper) {
            return (String) valueWrapper.get();
          }
        }
        return "";
      }

      @Autowired
      public void setCacheManager(CacheManager cacheManager) {
        PrisonUtil.cacheManager = cacheManager;
      }
    }
