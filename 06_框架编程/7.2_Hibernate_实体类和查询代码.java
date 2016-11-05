
* 简单查询通过jpa可以直接完成，不需要写代码
* 复杂查询
	* 
		public interface DictionaryDAO
		    extends JpaRepository<Dictionary, Long>, JpaSpecificationExecutor<Dictionary> {

		  @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true"), 
		  	@QueryHint(name = "org.hibernate.cacheRegion", value = "com.newings.jc.entity.main.Dictionary")})
		  Page<Dictionary> findByParentNameAndType(String name, DictionaryType dictionaryType,
		      Pageable pageable);

		  @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true"), @QueryHint(
		      name = "org.hibernate.cacheRegion", value = "com.newings.jc.entity.main.Dictionary")})
		  @Query("FROM Dictionary d WHERE d.parent.name=?1 AND d.name like ?2 AND d.type='ITEM' "
		      + "ORDER BY d.priority ASC,to_number(d.value) ASC ")
		  Page<Dictionary> findItembyName(String name, String namee, Pageable pageable);

		  @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true"), @QueryHint(
		      name = "org.hibernate.cacheRegion", value = "com.newings.jc.entity.main.Dictionary")})
		  @Query("FROM Dictionary d WHERE d.parent.name=?1 AND d.type='ITEM' ORDER BY d.priority ASC,to_number(d.value) ASC ")
		  Page<Dictionary> findItem(String name, Pageable pageable);

		  /**
		   * 查询数据字典（公有）
		   */
		  @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true"), @QueryHint(
		      name = "org.hibernate.cacheRegion", value = "com.newings.jc.entity.main.Dictionary")})
		  @Query("FROM Dictionary d WHERE d.parent.name=?1 AND d.type='ITEM' ORDER BY d.priority ASC,to_number(d.value) ASC")
		  List<Dictionary> findAllItems(String themeName);

		  /**
		   * 有条件查询数据字典
		   */
		  @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true"), @QueryHint(
		      name = "org.hibernate.cacheRegion", value = "com.newings.jc.entity.main.Dictionary")})
		  @Query("FROM Dictionary d WHERE d.parent.name=?1 AND d.type='ITEM' AND d.filterExp=?2 "
		      + "ORDER BY d.priority ASC,to_number(d.value) ASC")
		  List<Dictionary> findAllItems(String themeName, String filterExp);

		  @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true"), @QueryHint(
		      name = "org.hibernate.cacheRegion", value = "com.newings.jc.entity.main.Dictionary")})
		  @Query(" FROM Dictionary A WHERE A.value = ?2   AND A.parent.name = ?1")
		  Dictionary getNameByThemeAndValue(String theme, String val);

		  @QueryHints(value = {@QueryHint(name = "org.hibernate.cacheable", value = "true"), @QueryHint(
		      name = "org.hibernate.cacheRegion", value = "com.newings.jc.entity.main.Dictionary")})
		  @Query(" FROM Dictionary A WHERE A.value = ?2 AND A.filterExp=?3  AND A.parent.name = ?1")
		  Dictionary getNameByThemeAndValue(String theme, String val, String filterExp);

		  @Query("from Dictionary A where A.type ='THEME' and A.name = ?1 and filterExp =?2 ")
		  List<Dictionary> getDicByName(String name, String filterExp);


		  @Query("from Dictionary A where A.type ='ITEM' and A.name = ?1 and A.parent.id = ?2")
		  List<Dictionary> getDicByNameAndPid(String name, Long id);
		}

	* 其他例子
		  @Query("FROM Organization o WHERE o.id = ?1 ")
		  List<Organization> organizationQuery(String pid);