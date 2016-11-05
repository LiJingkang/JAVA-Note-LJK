* 实现 RowMapper 的接口。
    * 在方法内部再定义一个方法 。
    * 内部类
————————————————————————————————————————————————————————————————————————————————————————————
  private class PrisonerRowMapper implements RowMapper<Prisoner> {
    @Override
    // 在方法内部定义一个内部类。 在需要的时候调用它。
    public Prisoner mapRow(ResultSet rs, int rowNum) throws SQLException {
      Prisoner prisoner = new Prisoner();
      prisoner.setId(rs.getString("RYBH"));
      prisoner.setNo(rs.getString("FH"));
      prisoner.setName(rs.getString("XM"));
      prisoner.setIdentityCard(rs.getString("ZJHM"));
      prisoner.setCardId(Converters.convert2String(rs.getString("DZWDNM")));
      Dorm dorm = new Dorm();
      dorm.setCode(rs.getString("JSH"));
      prisoner.setDorm(dorm);
      return prisoner;
    } }
————————————————————————————————————————————————————————————————————————————————————————————
* 这是一句代码 ，在这句代码里面  query 传入的第二个参数 是一个 内部类。 
    * query
        @Override
        public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
            return query(sql, new RowMapperResultSetExtractor<T>(rowMapper)); }
        * 传进来的是 
            RowMapper<T> rowMapper // 之后进行调用
    * 使用
        meetings = template.query(sql, new RowMapper<Meeting>() {
        @Override
        public Meeting mapRow(ResultSet rs, int rowNum) throws SQLException {
          Meeting meeting = new Meeting();
          meeting.setId(rs.getString("ZYBH"));
          Prisoner prisoner = new Prisoner();
          prisoner.setId(rs.getString("RYBH"));
          prisoner.setName(rs.getString("YYR"));
          meeting.setPrisoner(prisoner);
          meeting.setTypeName(rs.getString("YYLX"));
          meeting.setType(rs.getString("YYLXCode"));
          meeting.setDiseaseName(rs.getString("YYNR"));
          meeting.setDisease(rs.getString("YYNRCode"));
          meeting.setStatus(rs.getString("STATE"));
          String dateStr = rs.getString("YYSJ");
          try {
            meeting.setDate(DateUtils.parseDate(dateStr, "yyyyMMddHHmmss"));
          } catch (ParseException ex) {
            ex.printStackTrace(); }
          return meeting; } });
    * 使用
        // 第二个参数
        // 新建一个 Object[] 数组。对象数组。里面放了 两个 字符串
        // 这个字符串数组最终调用了  newArgPreparedStatementSetter(args)
        // 也不知道最后赶了个啥。
        List<Prisoner> prisoners =
        template.query(sql, new Object[] {dormCode, DateFormatUtils.format(monday, "yyyyMMdd")},
            // 传入 sql, new Object[], 一个新建的 RowMapper
            new RowMapper<Prisoner>() {
              @Override
              public Prisoner mapRow(ResultSet rs, int rowNum) throws SQLException {
                Prisoner prisoner = new Prisoner();
                prisoner.setId(rs.getString("RYBH"));
                prisoner.setName(rs.getString("XM"));
                prisoner.setBedNo(rs.getString("WZ"));
                prisoner.setCardId(Converters.convert2String(rs.getString("DZWDNM")));
                return prisoner; } });
    * 使用
        // new RowMapper<T>() 是一个接口 内部定义了 
        //   T mapRow(ResultSet rs, int rowNum) throws SQLException 方法
        //   
        meetings = template.query(sql, new RowMapper<Meeting>() { // 实际上是传入了 一个 sql 和一个 RowMapper
        @Override
        // 对应的方法。对 ResultSet 和 rowNum 进行处理操作 
        public Meeting mapRow(ResultSet rs, int rowNum) throws SQLException {
          Meeting meeting = new Meeting();
          meeting.setId(rs.getString("ZYBH"));
          Prisoner prisoner = new Prisoner();
          prisoner.setId(rs.getString("RYBH"));
          prisoner.setName(rs.getString("YYR"));
          meeting.setPrisoner(prisoner);
          meeting.setTypeName(rs.getString("YYLX"));
          meeting.setType(rs.getString("YYLXCode"));
          meeting.setDiseaseName(rs.getString("YYNR"));
          meeting.setDisease(rs.getString("YYNRCode"));
          meeting.setStatus(rs.getString("STATE"));
          String dateStr = rs.getString("YYSJ");
          try {
            meeting.setDate(DateUtils.parseDate(dateStr, "yyyyMMddHHmmss"));
          } catch (ParseException ex) {
            ex.printStackTrace();
          }
          return meeting;
        }
      });        