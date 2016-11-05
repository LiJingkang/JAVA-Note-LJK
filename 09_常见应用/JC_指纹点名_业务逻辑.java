    
    * 先查询指纹
- http://192.168.0.131:8180/jc/prisoner/findByFingerprint?fingerprint=[参数太长自动隐藏]&prisonId=330211111&dormCode=0202&swipeType=shift
    * 得到查询结果
- 指纹匹配结果:FingerprintMatcher [id=330211111201606090005, fingercode=12, simliarity=0.7575]
- model params:number=330211111201606090005;prisonId=330211111;
    * findByNumber 来获得 PrisonPeopleInfo
- ==>  Preparing: SELECT jbxxb.RYBH,bdxxb.RYGLLB,jbxxb.JSH,ryxx.MZ,jbxxb.SNZT,jbxxb.JSBH,ryxx.XM,
                            jbxxb.FH,ryxx.XB,jbxxb.DZWDNM,ryxx.ZJHM 
                    FROM KSSRYJBXXB jbxxb 
                    LEFT OUTER JOIN KSSRYBDXXB bdxxb on jbxxb.RYBH=bdxxb.RYBH 
                    LEFT OUTER JOIN RYJBXXB ryxx on jbxxb.JBXXBH=ryxx.JBXXBH 
                        WHERE (jbxxb.JSBH=? AND jbxxb.RYBH=? AND jbxxb.rybj='0' AND (bdxxb.BDRYBJ='0' or bdxxb.BDRYBJ is null)) 
- ==> Parameters: 330211111(String), 330211111201606090005(String)
- <==      Total: 1
    * 保存进去
    // 只要查询指纹，就记录下来。不论他去做什么。
- ==>  Preparing: INSERT INTO JSSKJLB (ZYBH, SJC, RYBH, DZWDNM, JSBH, SKSJ, JSH, ZPDZ, SKLX) 
                    VALUES (?, '20160702042607', ?, ?, ?, '20160702042607', ?, ?, ?) 
- ==> Parameters: 3302111115776d1df821ea80832239b76(String), 330211111201606090005(String), null, 330211111(String), 
                    0202(String), null, prisonerSignIn(String)
- <==    Updates: 1

- 匹配成功，返回在押人员给终端
    * 然后手动保存进去
- http://192.168.0.131:8180/jc/swipe/shift?prisonId=330211111&dormCode=0202&cardId=&number=330211111201606090005&imgUrl=
- ==>  Preparing: INSERT INTO JSSKJLB (ZYBH, SJC, RYBH, DZWDNM, JSBH, SKSJ, JSH, ZPDZ, SKLX) 
                    VALUES (?, '20160702042607', ?, ?, ?, '20160702042607', ?, ?, ?) 
- ==> Parameters: 3302111115776d1df821ea80832239b77(String), 330211111201606090005(String), (String), 
                    330211111(String), 0202(String), (String), prisonerShift(String)
- <==    Updates: 1