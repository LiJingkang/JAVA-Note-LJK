* 通过 传入的 Pageable 来手动拼接 SQL 的分页查询。
* 拼接 SQL 分页查询的工具类
    * 对照 SQL  
    public String creatPageSql(StringBuilder meetingSqlBuilder, Pageable pageable) {
        StringBuilder sb = new StringBuilder();
        sb.append("select * from ( select tmp_page.*, rownum row_id from (");
        sb.append(meetingSqlBuilder);
        sb.append(") tmp_page where rownum <= ")
            .append((pageable.getPageNumber() + 1) * pageable.getPageSize())
            .append(") where row_id > ").append(pageable.getPageNumber() * pageable.getPageSize());
        String sql = sb.toString();
        logger.debug(sql);
        return sql;
    }
        * 参考的例子
                  if (count > 0) {
                  StringBuilder sb = new StringBuilder();
                  sb.append("select * from ( select tmp_page.*, rownum row_id from (");
                  sb.append("select yy.ZYBH, yy.RYBH, yy.YYR, yy.YYLX, YYLXCode, YYNR, YYNRCode, YYSJ, STATE");
                  sb.append(meetingSqlBuilder);
                  sb.append(" order by yy.YYSJ desc");
                  sb.append(") tmp_page where rownum <= ")
                      .append((pageable.getPageNumber() + 1) * pageable.getPageSize())
                      .append(") where row_id > ").append(pageable.getPageNumber() * pageable.getPageSize());
                  }

* 拼接 SQL 查询个数
    StringBuilder countSql = new StringBuilder();
    countSql.append("select count(*) ").append("FROM (").append(sb).append(")");
    long count = jdbcTemplate.queryForObject(countSql.toString(), Long.class);

* 使用的例子


————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* Spring Date 例子
    StringBuilder sb = new StringBuilder();
    sb.append("select room.id dormId, room.monitor_id dormCode, room.cname dormName ");
    sb.append("from room_info room ");

    // 新建一个 保存查询数据的 List 
    List<Map<String, Object>> list = template.queryForList(sb.toString());

    // 新建一个 保存返回对象的 List
    List<Dorm> agents = new ArrayList<Dorm>(list.size());

    // 这个对象主要是 将查询数据的 List 挨个转换为 保存对象的 List
    for (Map<String, Object> map : list) {
      // 取出内容
      String dormId = (String) map.get("dormId");
      String dormCode = (String) map.get("dormCode");
      String dormName = (String) map.get("dormName");

      // 存入
      Dorm dorm = new Dorm();
      dorm.setId(dormId);
      dorm.setCode(dormCode);
      dorm.setName(dormName);
      // 添加进去
      agents.add(dorm); 
    }
    // 返回数据
    return agents;
  }
————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————————
* 添加时间戳
    添加时间戳
      // 时间戳 SJC
      private Date timeStamp;

      // 查询代码
          if (startTime != null) {
          String startTimeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(startTime);
          sb.append("and SJC >= '").append(startTimeStr).append("' ");
        }
        if (endTime != null) {
          String endTimeStr = new SimpleDateFormat("yyyyMMddHHmmss").format(endTime);
          sb.append("and SJC <= '").append(endTimeStr).append("' ");
        }

      // 返回代码
          SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

          String timeStampStr = (String) map.get("SJC");
          try {
            Date time = dateFormat.parse(timeStampStr);
            breakRule.setTimeStamp(time);
          } catch (Exception e) {
            e.printStackTrace();
          }



  

