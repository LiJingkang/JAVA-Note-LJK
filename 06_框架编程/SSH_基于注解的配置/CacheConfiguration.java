* 缓存配置中心

@Configuration
@EnableCaching
public class CacheConfiguration {

  /**
   * CacheManager.
   * 
   * @return cacheManager
   */
  @Bean
  public CacheManager cacheManager() {
    SimpleCacheManager cacheManager = new SimpleCacheManager();
    GuavaCache dicts =
        new GuavaCache("dictCache", CacheBuilder.newBuilder().maximumSize(150000).build());

    GuavaCache terminalStatusCache = new GuavaCache("terminalStatusCache",
        CacheBuilder.newBuilder().maximumSize(150).expireAfterAccess(5, TimeUnit.MINUTES).build());

    GuavaCache chatStatusCache = new GuavaCache("chatStatusCache",
        CacheBuilder.newBuilder().maximumSize(100).expireAfterAccess(15, TimeUnit.MINUTES).build());

    GuavaCache terminalIpCache =
        new GuavaCache("terminalIpCache", CacheBuilder.newBuilder().maximumSize(200).build());

    GuavaCache globalConfigCache =
        new GuavaCache("globalConfigCache", CacheBuilder.newBuilder().maximumSize(5).build());
    GuavaCache policeRightCache = new GuavaCache("policeRightCache",
        CacheBuilder.newBuilder().maximumSize(5).expireAfterAccess(10, TimeUnit.MINUTES).build());

    cacheManager.setCaches(Arrays.asList(dicts, terminalStatusCache, chatStatusCache,
        terminalIpCache, globalConfigCache, policeRightCache));
    cacheManager.initializeCaches();
    return cacheManager;
  }

}
