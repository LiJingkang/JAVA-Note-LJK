@EnableWebMvc
@ComponentScan(basePackages = {"com.newings.controller"})
@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {

  private ResourceLoader resourceLoader = new FileSystemResourceLoader();

  @Value("${jc.config.password}")
  private String password;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**")
        .excludePathPatterns("/js/**").excludePathPatterns("/css/**").excludePathPatterns("/img/**")
        .excludePathPatterns("/fonts/**");
    registry.addInterceptor(new ConfigPageInterceptor(password)).addPathPatterns("/**/page/**");
  }


  /**
  * 静态资源过滤
  */
  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/js/**").addResourceLocations("/WEB-INF/velocity/js/")
        .setCachePeriod(31556926);
    registry.addResourceHandler("/css/**").addResourceLocations("/WEB-INF/velocity/css/")
        .setCachePeriod(31556926);
    registry.addResourceHandler("/img/**").addResourceLocations("/WEB-INF/velocity/img/")
        .setCachePeriod(31556926);
    registry.addResourceHandler("/fonts/**").addResourceLocations("/WEB-INF/velocity/fonts/")
        .setCachePeriod(31556926);
  }

  /**
   * 将对于静态资源的请求转发到vServlet 容器的默认处理静态资源的servlet
   * 因为将spring的拦截模式设置为"/"时会对静态资源进行拦截
   */
  @Override
  public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
    configurer.enable();
  }

  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    MarshallingHttpMessageConverter marshallingHttpMessageConverter =
        new MarshallingHttpMessageConverter();
    marshallingHttpMessageConverter.setMarshaller(xStreamMarshaller());
    marshallingHttpMessageConverter.setUnmarshaller(xStreamMarshaller());

    converters.add(marshallingHttpMessageConverter);
    converters.add(new MappingJackson2HttpMessageConverter());
    converters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
    converters.add(new ResourceHttpMessageConverter());
    converters.add(new ByteArrayHttpMessageConverter());
  }

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
  }

  /**
   * xstream配置.
   * 
   * @return XStreamMarshaller
   */
  @Bean
  public XStreamMarshaller xStreamMarshaller() {
    XStreamMarshaller marshaller = new XStreamMarshaller();
    marshaller.setAutodetectAnnotations(true);
    Map<String, String> aliases = new HashMap<>();
    aliases.put("Page", "org.springframework.data.domain.PageImpl");
    aliases.put("Page", "com.github.pagehelper.Page");
    aliases.put("pageable", "org.springframework.data.domain.PageRequest");

    marshaller.setAliases(aliases);
    return marshaller;
  }

  /**
   * multipartResolver.
   * 
   * @return multipartResolver
   */
  @Bean(name = "multipartResolver")
  public CommonsMultipartResolver multipartResolver() {
    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    multipartResolver.setDefaultEncoding("UTF-8");
    return multipartResolver;
  }

  /**
   * vm配置.
   * 
   * @return VelocityViewResolver
   */
  @Bean
  public VelocityViewResolver velocityViewResolver() {
    VelocityViewResolver velocityViewResolver = new VelocityViewResolver();
    velocityViewResolver.setCache(true);
    velocityViewResolver.setPrefix("/");
    velocityViewResolver.setSuffix(".vm");
    velocityViewResolver.setExposeSpringMacroHelpers(true);
    velocityViewResolver.setExposeRequestAttributes(true);
    velocityViewResolver.setRequestContextAttribute("request");
    velocityViewResolver.setToolboxConfigLocation("/WEB-INF/toolbox.xml");
    velocityViewResolver.setContentType("text/html;charset=UTF-8");

    return velocityViewResolver;
  }

  /**
   * vm配置.
   * 
   * @return VelocityConfigurer
   */
  @Bean(name = "velocityConfig")
  public VelocityConfigurer velocityConfigurer() throws IOException {
    VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
    velocityConfigurer.setResourceLoaderPath("/WEB-INF/velocity");
    Properties properties = new Properties();
    properties.put("input.encoding", "UTF-8");
    properties.put("output.encoding", "UTF-8");
    properties.put("contentType", "text/html;charset=UTF-8");
    velocityConfigurer.setVelocityProperties(properties);

    velocityConfigurer.setConfigLocation(getResource("classpath:velocity.properties"));
    return velocityConfigurer;
  }

  private Resource getResource(final String location) throws IOException {
    return resourceLoader.getResource(location);
  }
}
