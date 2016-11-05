// 权限比对的一次代码
// 写了三层循环。  业务的处理  和  代码的处理

  @Override
  public Response queryCodeAll(String prsId) {

    // 获取登录民警编号
    String code = SecurityUtils.getShiroUser().getUser().getPolicecode();
    // 根据prisonId 和 民警编号查询 当前民警管辖的监室
    String hql = "from PrsRoomControl a where a.policeId = '" + code
        + "' and a.prsRoomNumber.prsId = '" + prsId + "' and a.relationShip = '1' ";
    List<PrsRoomControl> prsRoomControls = tempAuxiliaryUtils.findBySearch(hql);

    // 通过prsId 找到所有的拘区号
    List<PrsRoomNumber> prsRoomNumberList = prsRoomNumberDAO.findCodeByPrsIdAndStatus(prsId, "0");
    for (int i = 0; i < prsRoomNumberList.size(); i++) {
      PrsRoomNumber prsRoomNumber = prsRoomNumberList.get(i);
      if ("0".equals(prsRoomNumber.getStatus())) {
        // 查询每个拘区里面的拘室
        String hql2 =
            "select NEW PrsRoomNumber(code,character,status,prsId) from PrsRoomNumber a where a.prsId='"
                + prsRoomNumber.getPrsId() + "' and a.status='1' and substring(a.code,0,2) = '"
                + prsRoomNumber.getCode() + "' and a.code!='" + prsRoomNumber.getCode() + "' ";

        List<PrsRoomNumber> prsRoomNumbers = tempAuxiliaryUtils.findBySearch(hql2);
        // 对拘室进行判断，是否属于该民警管辖
        for (int j = 0; j < prsRoomNumbers.size(); j++) {
          String dormCode = prsRoomNumbers.get(j).getCode();

          for (int k = 0; k < prsRoomControls.size(); k++) {
            String usableDormCode = prsRoomControls.get(k).getPrsRoomNumber().getCode();
            // 如果是则添加
            if (dormCode.equals(usableDormCode)) {
              prsRoomNumberList.get(i).setPrsRoomNumberList(prsRoomNumbers);
            }
          }
        }
      }
    }

    // 删除为空的拘区
    for (int i = prsRoomNumberList.size() - 1; i >= 0; i--) {

      PrsRoomNumber prsRoomNumber = prsRoomNumberList.get(i);
      if (prsRoomNumber.getPrsRoomNumberList().isEmpty()) {
        prsRoomNumberList.remove(i);
      }
      if (i == 0) {
        break;
      }
    }
    return new ListResponse<PrsRoomNumber>(prsRoomNumberList);
  }

* 修改以后的写法

  @Override
  public Response queryCodeAll(String prsId) {

    // 获取登录民警编号
    String policeCode = SecurityUtils.getShiroUser().getUser().getPolicecode();
    // 根据prisonId 和 民警编号查询 当前民警管辖的监室
    String hql = "from PrsRoomControl a where a.policeId = '" + policeCode
        + "' and a.prsRoomNumber.prsId = '" + prsId + "' and a.relationShip = '1' ";
    List<PrsRoomControl> prsRoomControls = tempAuxiliaryUtils.findBySearch(hql);

    // 通过prsId 找到所有的拘区号
    List<PrsRoomNumber> prsRoomNumberList = prsRoomNumberDAO.findCodeByPrsIdAndStatus(prsId, "0");

    for (int i = 0; i < prsRoomControls.size(); i++) {
      String dormCode = prsRoomControls.get(i).getPrsRoomNumber().getCode();
      int number = Integer.parseInt(dormCode.substring(0, 2)) - 1;

      prsRoomNumberList.get(number).getPrsRoomNumberList()
          .add(prsRoomControls.get(i).getPrsRoomNumber());
    }

    // 删除为空的拘区
    for (int i = prsRoomNumberList.size() - 1; i >= 0; i--) {
      PrsRoomNumber prsRoomNumber = prsRoomNumberList.get(i);
      if (prsRoomNumber.getPrsRoomNumberList().isEmpty()) {
        prsRoomNumberList.remove(i);
      }
      if (i == 0) {
        break;
      }
    }
    return new ListResponse<PrsRoomNumber>(prsRoomNumberList);
  }

* 另一段自己写的代码

  @Override
  public Response findShiftNumber(String prisonId, String[] dormCodes, Date startTime,
      Date endTime) {
    // 建立返回的空List
    List<Map<String, String>> list = new ArrayList<Map<String, String>>();
    // 获取登录民警编号
    String a = SecurityUtils.getShiroUser().getUser().getPolicecode();
    // 根据prisonId 和 民警编号查询
    String hql3 = "from PrsRoomControl a where a.policeId = '" + a
        + "' and a.prsRoomNumber.prsId = '" + prisonId + "' and a.relationShip = '1' ";
    List<PrsRoomControl> prsRoomControls = tempAuxiliaryUtils.findBySearch(hql3);

    // 遍历传入的居室号
    for (int i = 0; i < dormCodes.length; i++) {
      String dormCode = dormCodes[i];
      // 对每个居室号是否是这个民警管辖的进行判断
      for (int j = 0; j < prsRoomControls.size(); j++) {
        String usableDormCode = prsRoomControls.get(j).getPrsRoomNumber().getCode();
        if (dormCode.equals(usableDormCode)) {
          Integer number = dailyShiftDAO.queryByWeek(prisonId, dormCode, startTime, endTime).size();
          Map<String, String> map = new HashMap<String, String>();
          map.put("number", number.toString());
          map.put("dormCode", dormCode);
          list.add(map);
        }
      }
    }
    return new ListResponse<Map<String, String>>(list);
  }

* hibernate IN 查询 
    String hql = "from PrsRoomControl a where a.policeId = '" + policeCode
        + "' and a.prsRoomNumber.prsId = '" + prsId + "' and a.relationShip in ('1','2') ";

* hibernate select count(*) 查询
    String hql2 =
       "select count(*) from DetentionPsnBasicInfo a where a.personsign='0' and a.prsId='"
       + prsId + "' and a.prsNumber = '" + dormCode + "' ";

* DetentionPsnBasicInfoDAO   
    // 实体类对应的 DAO 层，与他对应的位置和名称
    * service 不再直接执行 hql 语句，将hql 语句都放在dao 层