  @Override
  public Response addDedrugDrugBasicInfo(DedrugDrugBasicInfo dedrugDrugBasicInfo) {
    // 返回的 Response
    // 返回值顶层接口
    Assert.notNull(dedrugDrugBasicInfo);
    dedrugDrugBasicInfoDAO.save(dedrugDrugBasicInfo);
    if (!"".equals(dedrugDrugBasicInfo.getId())) {
      return new SuccessResponse();
        // SuccessResponse
    } else {
      return new FailedResponse("后台保存dedrugDrugBasicInfo失败!");
        // FailedResponse
    }
  }