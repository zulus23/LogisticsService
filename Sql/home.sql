
SELECT p.dif_,p.datecreate_row,p.datePlan_Mnfg,p.DateActual_Ship,cust_name,
  COUNT(p.id) OVER(PARTITION BY p.cust_name,p.dat_,p.dif_),
  CAST(100.0* COUNT(p.id) OVER( PARTITION BY p.cust_name,p.dat_,p.dif_)/COUNT(p.id) OVER( PARTITION BY p.dat_,p.cust_num) AS NUMERIC(15,2)) AS procent ,
   COUNT(p.id) OVER(PARTITION BY p.dat_,p.cust_name),
   p.dat_
 FROM (
SELECT CASE WHEN ABS(DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)) > 2 THEN 'Без отклонений' ELSE 'С отклонением' END AS dif_,
DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)AS t,
CAST(CONVERT(CHAR(6),dateactual_ship,112)+'01'AS DATE) AS dat_,* FROM dbo.GTK_RPT_LOGIST r
WHERE r.site = 'GOTEK' AND r.DatePlan_Ship BETWEEN '20160601' AND '20160815' --AND r.Cust_name = '"ГИГИЕНА-СЕРВИС МЕД" ООО'
) AS p


SELECT DISTINCT dif_,DateActual_Ship,Cust_name,procent FROM(
(SELECT p.dif_,p.datecreate_row,p.dat_ AS DateActual_Ship,'' AS cust_name,
  COUNT(p.id) OVER(PARTITION BY p.cust_name,p.dat_,p.dif_) AS p1,
  CAST(100.0* COUNT(p.id) OVER( PARTITION BY p.cust_name,p.dat_,p.dif_)/COUNT(p.id) OVER( PARTITION BY p.dat_,p.cust_num) AS NUMERIC(15,2)) AS procent ,
   COUNT(p.id) OVER(PARTITION BY p.dat_,p.cust_name) AS p2,
   p.dat_
 FROM (
SELECT CASE WHEN ABS(DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)) > 2 THEN 'Без отклонений' ELSE 'С отклонением' END AS dif_,
DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)AS t,
CAST(CONVERT(CHAR(6),dateactual_ship,112)+'01'AS DATE) AS dat_,* FROM dbo.GTK_RPT_LOGIST r
WHERE r.site = 'GOTEK' AND r.DatePlan_Ship BETWEEN '20160601' AND '20160816' --AND r.Cust_name = '"ГИГИЕНА-СЕРВИС МЕД" ООО'
) AS p)
) AS allRecord


SELECT p.dif_,p.datecreate_row,p.dat_ AS DateActual_Ship, cust_name,
  COUNT(p.id) OVER(PARTITION BY p.cust_name,p.dat_,p.dif_) AS p1,
  CAST(100.0* COUNT(p.id) OVER( PARTITION BY p.cust_name,p.dat_,p.dif_)/COUNT(p.id) OVER( PARTITION BY p.dat_,p.cust_num) AS NUMERIC(15,2)) AS procent ,
   COUNT(p.id) OVER(PARTITION BY p.dat_,p.cust_name) AS p2,
   p.dat_
 FROM (
SELECT CASE WHEN ABS(DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)) > 2 THEN 'Без отклонений' ELSE 'С отклонением' END AS dif_,
DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)AS t,
CAST(CONVERT(CHAR(6),dateactual_ship,112)+'01'AS DATE) AS dat_,* FROM dbo.GTK_RPT_LOGIST r
WHERE r.site = 'GOTEK' AND r.DatePlan_Ship BETWEEN '20160601' AND '20160816' AND r.Cust_name like '%БЕЛАЯ ПТИЦА-БЕЛГОРОД ООО%'
) AS p