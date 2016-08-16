SELECT p.dif_,p.datecreate_row,p.datePlan_Mnfg,p.DateActual_Ship,cust_name,
  COUNT(p.id) OVER(PARTITION BY p.dif_),
  CAST(100.0* COUNT(p.id) OVER(PARTITION BY p.dif_)/COUNT(p.id) OVER() AS NUMERIC(15,2)) ,
   COUNT(p.id) OVER()
 FROM (
SELECT CASE WHEN ABS(DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)) > 2 THEN 'Без отклонений' ELSE 'С отклонением' END AS dif_,
DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)AS t,* FROM dbo.GTK_RPT_LOGIST_ r
WHERE r.site = 'GOTEK' AND r.dateactual_ship BETWEEN '20160701' AND '20160816'
) AS p





SELECT p.dif_,p.datecreate_row,p.datePlan_Mnfg,p.DateActual_Ship,cust_name,
  COUNT(p.id) OVER(PARTITION BY p.dif_,p.cust_name),
  CAST(100.0* COUNT(p.id) OVER( PARTITION BY p.dif_,p.cust_name)/COUNT(p.id) OVER( PARTITION BY p.cust_name) AS NUMERIC(15,2)) ,
   COUNT(p.id) OVER()
 FROM (
SELECT CASE WHEN ABS(DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)) > 2 THEN 'Без отклонений' ELSE 'С отклонением' END AS dif_,
DATEDIFF(DAY,r.datecreate_row,r.datePlan_Mnfg)AS t,* FROM dbo.GTK_RPT_LOGIST_ r
WHERE r.site = 'GOTEK' AND r.dateactual_ship BETWEEN '20160701' AND '20160815'
) AS p

WHERE p.cust_name  = 'АЛЕКСЕЕВСКИЙ МОЛОЧНОКОНСЕРВНЫЙ КОМБИНАТ ЗАО'
