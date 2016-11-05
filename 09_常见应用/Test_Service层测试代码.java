* assert方法
        // 用来检测返回值是否满足我们的测试要求
        Assert.hasLength(name, "'name' must not be empty");
        Assert.notEmpty(values, "'values' must not be empty");
——————————————
* ssert.notenull()
    该方法无法判断返回的List是否为空。
    * 我们自己来进行判断
        @Test
        public void testFindByDormCode() {
          String prisonId = "330211111";
          String dormCode = "0204";
          List<PrisonPeopleInfo> prisonPeopleInfoList =
              prisonerService.findByDormCode(prisonId, dormCode);
          if (prisonPeopleInfoList.isEmpty()) {
            Assert.fail("Query result is empty");
          } else {
            System.out.println("list size = " + prisonPeopleInfoList.size());
          }
          // Assert.assertNotNull(prisonPeopleInfoList);
        }
————————————————————————————————————————————————————————————————————————————————————————
@WebAppConfiguration
public class PrisonerServiceTest extends BaseServiceTest {
  @Autowired
  private PrisonerService prisonerService;

  @Test
  public void test() {
    System.out.println("hello lorien");
  }     

  @Test
  public void testFindByCardId() {    
    String cardId = "000000000924B870";
    PrisonPeopleInfo prisoner =
        prisonerService.findMediumByCardId(cardId, "0204", "330211111", true);
    Assert.assertNotNull(prisoner);
    System.out.println(prisoner.getNumber());
    System.out.println(prisoner.getSexValue());
    System.out.println(prisoner.getName());

  }

  @Test
  public void testFindPhotoByNumber() {
    byte[] photo = prisonerService.findPhotoByNumber("", "330100111201206280001");
    Assert.assertNotNull(photo);
    System.out.println(photo.length);
  }

  @Test
  public void testFindAllPhotoByNumber() {
    List<byte[]> lb = prisonerService.findAllPhotoByNumber("330100111201206280001");
    Assert.assertNotNull(lb);
  }

  @Test
  public void testFindByDormCode() {
    prisonerService.findByDormCode("330183111", null);
  }
}
————————————————————————————————————————————————————————————————————————————————————————
* 返回的是List 数组
    Assert 用的 org.junit.Assert
    String   = "";
    Assert.assertNotNull(prisonList);
    System.out.println("list size = " + prisonList.size());
* 构造时间
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.TIME_FORMAT, Locale.CHINESE);
    Date endTime = simpleDateFormat.parse("20160101010101");
* 返回的是Map
    @org.junit.Test
    public void testSend() {
      SystemNotice systemNotice = new SystemNotice();
      systemNotice.setPrisonId("330681111");
      systemNotice.setOrder("shift_start");

      Map<String, Object> param = new HashMap<String, Object>();
      param.put("aheadTime", 5);// 提前5分钟提醒换班
      param.put("intervalTime", 15);// 时间间隔，每15分钟刷一次卡
      systemNotice.setParam(param);
      producer.sendMessage(systemNotice);
    }
* 测分页
    @Test
    public void testFindOrderIdByCardId() {
    Pageable pageable = new PageRequest(0, 10);
    // Page<String> page = dao.findOrderIdByCardId("0000000061C41531", new RowBounds(1, 20));
    List<String> page = dao.findOrderIds("", "0000000061C41531");

    assertThat(page, anything());
    for (String orderId : page) {
      System.out.println(orderId);
    }
    System.out.println(page.toString());
    }
* 测试保存
    @org.junit.Test
    public void testSaveInjuryRegister() throws IOException {
        PrisonerQo pq = new PrisonerQo();
        pq.setPrisonId("330100111");

        InjuryRegister injuryRegister = new InjuryRegister();
        injuryRegister.setNumber("330100111200405253508");
        injuryRegister.setDemo("备注无");
        injuryRegisterService.saveInjuryRegister(null, injuryRegister);
        // 先保存

        List<InjuryRegister> list = injuryRegisterService.findInjuryRegister(pq);
        list = injuryRegisterService.findInjuryRegister(pq);
        int count = list.size();
        Assert.assertEquals(count + 1, list.size());
        // 再查找

        injuryRegisterService.deleteInjuryRegister(injuryRegister.getWorkId());
        pq.setName("吴");
        pq.setComeStartDate("20020505");
        pq.setComeEndDate("20160505");
        list = injuryRegisterService.findInjuryRegister(pq);
        Assert.assertEquals(count, list.size());
    }
    
* 测试保存
    @org.junit.Test
    public void testSaveTalk() {

        Talk talk = new Talk();
        talk.setRybh("330100111201307020053");
        talkService.saveTalk(talk);

        List<Talk> talkList = talkService.findTalkByNumber("330100111201307020053");
        talkList = talkService.findTalkByNumber("330100111201307020053");
        int size = talkList.size();
        Assert.assertEquals(size + 1, talkList.size());

        talk.setZybh(null);
        talkService.saveTalk(talk);
        talkList = talkService.findTalkByNumber("330100111201307020053");
        Assert.assertEquals(size + 2, talkList.size());

        talk.setRybh("330100111201307020053");
        talk.setRypp("稳定");
        talkService.saveTalk(talk);
        talkList = talkService.findTalkByNumber("330100111201307020053");
        Assert.assertEquals(size + 2, talkList.size());

        talkService.deleteTalk(talk.getZybh());
        talkList = talkService.findTalkByNumber("330100111201307020053");
        Assert.assertEquals(size + 1, talkList.size());
    }
