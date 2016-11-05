* Map
	* 初始化 赋值
		Map<Integer, String> map = new HashMap<Integer, String>() {{ 
		        put( 1 , "name" ); 
		        put( 2 , "sex" ); 
			}}; 

		List<String> list = new ArrayList<String>() {{ 
		        add( "first" ); 
		        add( "second" ); 
		        add( "third" ); 		 
			}}; 

	* MultiValueMap	
		// 取字典赋值
		public MockHttpServletRequestBuilder params(MultiValueMap<String, String> params) {
			for (String name : params.keySet()) {
				for (String value : params.get(name)) {
					this.parameters.add(name, value);
				}
			}
			return this;
		}
	* Map<K,V> map = new HashMap<K,V>(); 
		// K为键的类型 V为值的类型、
	* String... values 
		// 什么意思
		public MockHttpServletRequestBuilder param(String name, String... values) {
			addToMultiValueMap(this.parameters, name, values);
			return this;
		}
* MultiValueMap 
	* MultiValueMap 是一个接口，不能直接对他进行实例化，
		// 需要使用一个实现它接口的实现类
		Java中的Map，1个key只能对应1个Value，第二次放入同样的key时会覆盖掉上次的value。 
		要1个key对应多个value，用apach提供的MultiValueMap 
	* MultiValueMap处理重复的键值对	
		public MockHttpServletRequestBuilder params(MultiValueMap<String, String> params) {
		for (String name : params.keySet()) {
			for (String value : params.get(name)) {
				this.parameters.add(name, value);
			}
		}
		return this;
		}
* 把 MAP 存进 List 里面去
	  @Override
	  public List<Map<String, String>> findShiftNumber(String prisonId, String[] dormCodes,
	      Date startTime, Date endTime) {
	    List<Map<String, String>> list = new ArrayList<Map<String, String>>();

	    for (int i = 0; i < dormCodes.length; i++) {
	      String dormCode = dormCodes[i];
	      Integer number = dailyShiftDAO.queryByWeek(prisonId, dormCode, startTime, endTime).size();
	      Map<String, String> map = new HashMap<String, String>();
	      	// Java　里面 map 指向的是他的内存地址
	      	// 如果在外面新建的话，他会只加入同一个。
	      map.put("number", number.toString());
	      map.put("dormCode", dormCode);

	      list.add(map);
	    }
	    return list;
	  }