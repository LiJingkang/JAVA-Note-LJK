关键字用 小写
列名用  大写
别名用 小写



以前  要么全部大写，要么全部小写

select t.ALIA_NAME, t.PHYC_NAME, t.LOCATE_FIELD, t.TID, f.ALIA_NAME, f.PHYC_NAME,  t.NOTE,f."VALUE"
from table_list t, FIELD_LIST f
where t.TID=f.TID and t.phyc_name='KSSZDZYRYKZJLB'

select * 
from ( 
        select tmp_page.*, rownum row_id 
        from ( 
                SELECT b.RYBH,b.FH,b.JSH,b.SSJD,b.DAH,b.BADW,b.BADWLX,b.XQ,b.xqksrq,
                      b.XQJZRQ,b.CLJG,b.WXDJ,b.GYQX,b.HYRQ,b.SNZT,b.CLSJ,b.ZXRQ,b.SSRQ,
                      b.SPC,b.PJZM,c.SYDW,c.SYR,c.JLRQ,c.DBRQ,c.ZYBH,c.AJLB,c.RSRQ,c.RSYY,
                      c.CSRQ as kssCSRQ,
                      c.CSYY,c.CSQX,c.zxf,c.WFFZJL,c.SFWSBH,c.bz,c.RSFLWSH,c.zldw,d.XM,
                      d.BM,d.XB,d.CSRQ ,d.HYZK,d.MZ,d.GJ,d.WHCD,d.XZZQH,d.XZZXZ,d.HJSZD,d.HJDXZ,d.ZY,
                      d.GZDW,d.PYZT,d.BMPY,d.ZJHM,d.sf,d.sfhcbz,e.GJMJ, 
                      case when c.csrq is not null 
                                then trunc(to_date(c.csrq,'yyyymmddhh24miss')-to_date(c.rsrq,'yyyymmddhh24miss')) 
                                else trunc(sysdate-to_date(c.rsrq,'yyyymmddhh24miss')) 
                                end as numOfDays,
                      case when b.XQJZRQ is null 
                                then ''
                                else (TRUNC(MONTHS_BETWEEN(to_date(b.GYQX,'yyyymmdd'),SYSDATE)/12)|| '年' || 
                                     (MOD(TRUNC(MONTHS_BETWEEN(to_date(b.GYQX,'yyyymmdd'),SYSDATE)),12))|| '月' ||
                                     to_char((ADD_MONTHS(to_date(b.GYQX,'yyyymmdd'),TRUNC(MONTHS_BETWEEN(SYSDATE,to_date(b.GYQX,'yyyymmdd')))))-TRUNC(SYSDATE))|| '天') 
                                end as leftSentence 
                FROM KSSRYJBXXB b 
                LEFT OUTER JOIN KSSRYBDXXB c on b.rybh=c.rybh 
                LEFT OUTER JOIN RYJBXXB d on b.JBXXBH=d.JBXXBH 
                LEFT OUTER JOIN jshb e on e.jsbh='330211111' and b.jsh=e.dm 

                WHERE (b.JSBH='330211111' AND d.HJSZD LIKE '%420000%') 
             ) tmp_page 
        where rownum <= ? 
     ) 
where row_id > ?

SELECT count(0) 
FROM KSSRYJBXXB b 
LEFT OUTER JOIN KSSRYBDXXB c ON b.rybh = c.rybh 
LEFT OUTER JOIN RYJBXXB d ON b.JBXXBH = d.JBXXBH 
LEFT OUTER JOIN jshb e ON e.jsbh = '330211111' AND b.jsh = e.dm 
WHERE (b.JSBH = '330211111' AND d.HJSZD LIKE '%420000%') 




    SELECT("");
        FROM("T_TRADE_LIST tl");
        JOIN("T_MEMBERS tm on tm.MEMBER_ID = tl.MEMBER_ID");
        WHERE("tm.IC_NO = #{cardId} or tm.RYBH=#{number}");
        ORDER_BY("RECNO desc");
      }
    }.toString();
    
    SELECT 

select RECNO,V_RQ,V_TYPE,VALUE,tl.BALANCE,tm.IC_NO
from T_TRADE_LIST tl
join T_MEMBERS tm on tm.MEMBER_ID = tl.MEMBER_ID
where tm.IC_NO = #{cardId} or tm.RYBH=#{number}
order by RECNO desc

select RECNO,V_RQ,V_TYPE,VALUE,tl.BALANCE,tm.IC_NO
from T_TRADE_LIST tl
join T_MEMBERS tm on tm.MEMBER_ID = tl.MEMBER_ID
where tm.IC_NO = 330211111
order by RECNO desc


select tm.MEMBER_ID id, vr.MEMBER_NAME name, vr.BALANCE balance,vr.DEPT_Name dormCode,vr.BYKYYE monthLimit
from V_RYXJYY3 vr 
join T_MEMBERS tm on tm.member_id=vr.member_id 
where tm.rybh = #{number}

SELECT A.Zybh,A.RSYY,A.RSRQ,A.ZXF,B.JSH,B.FH,B.Gyqx,B.Ssrq,B.Ssjd,B.CLJG,B.PJZM,B.SNZT,C.XM,C.Xb,C.Csrq,C.BMPY,C.PYZT,c.SFHCBZ,B.RYBH 
FROM kssrybdxxb A 
LEFT OUTER JOIN kssryjbxxb B on A.Rybh = B.Rybh 
LEFT OUTER JOIN ryjbxxb C on B.Jbxxbh = C.Jbxxbh 
WHERE (A.Bdrybj = '0' AND A.CSLCZT is null 
                      AND B.rybj = '0' 
                      AND B.jsbh ='330211111' 
                      AND b.GYQX = ( Select to_char(SysDate,'yyyyMMdd') 
                                      from dual) 
                      AND a.RYBH in (Select L.RYBH 
                                     from KSSLSXXB L 
                                     left join KSSRYJBXXB K on L.RYBH=K.RYBH 
                                     where K.RYBJ='0' and K.JSBH='330211111')
      ) 
