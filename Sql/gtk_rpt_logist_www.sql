USE [GTK_GROUP_REPORT]
GO
/****** Object:  StoredProcedure [dbo].[gtk_rpt_logist_www]    Script Date: 09/02/2016 08:44:52 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[gtk_rpt_logist_www]
	-- Add the parameters for the stored procedure here
	(@v_startdate datetime,
	 @v_enddate  datetime,
	 @v_site VARCHAR(10),/*,
	 @switch_mode int,
	 -- 1 режим 1
	 -- 2 режим 2*/
	 @v_type_rep int
	 
    --  2- точность открытия заказов
	--  3- точность планирования заказов продажами
	--  4- точность постановки заказов в производство
	--  5- точность выхода заказов на склад
	--  6- точность отгрузки заказов
	--  7- точность доставки заказов
	
	)
	
AS

	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

IF(OBJECT_ID('tempdb..#gtk_rpt_logist') is not NULL)
begin
  drop table #gtk_rpt_logist
end
 CREATE TABLE #gtk_rpt_logist (
    Site NVARCHAR(15) NULL,
	Co_Num NVARCHAR(15) NULL,
	Co_Line INT NULL,
	Cust_num NVARCHAR(15) NULL,
	Cust_Seq INT NULL,
	Cust_name NVARCHAR(70) NULL,
	FrontSlsmanName NVARCHAR(50) NULL,
	SlsmanName NVARCHAR(50) NULL,
	ShipCodeDescr NVARCHAR(12) NULL,
	ref_num NVARCHAR(20) NULL,
	ref_line_suf INT NULL,
	DatePlan_Mnfg DATETIME NULL,
	DatePlan_Whse DATETIME NULL,
	DatePlan_Ship DATETIME NULL,
	DateActual_Ship DATETIME NULL,
	DateCreate_Row DATETIME NULL,
	FactProdReqDate DATETIME NULL,
	FactOnWhseDate DATETIME NULL,
	FactDeliveryDate DATETIME NULL,
	ActualDeliveryDate DATETIME NULL,
	PlanDeliveryDate DATETIME NULL,
	PlanProdReqDate DATETIME NULL,
	Item NVARCHAR(15) NULL,
	Item_Desc NVARCHAR(60) NULL,
	Remark_Row NVARCHAR(50) NULL,
	StatusRow NVARCHAR(100) NULL,
	id BIGINT IDENTITY(1,1) NOT NULL PRIMARY KEY,  
	DatePlan_Ship_M DATETIME NULL,
	PlanDeliveryDate_M DATETIME NULL
)

IF(@v_site = 'GOTEK' )BEGIN

 INSERT into #gtk_rpt_logist (Site,Co_num,Co_line,cust_num,cust_seq,
                              Cust_name,FrontSlsmanName,SlsmanName,ShipCodeDescr, ref_num,ref_line_suf,
                              item,Item_Desc,
                              DatePlan_Mnfg,DatePlan_Whse,DatePlan_Ship,DateActual_Ship,DateCreate_Row ,FactProdReqDate,FactOnWhseDate,FactDeliveryDate,ActualDeliveryDate, 
                             PlanDeliveryDate,PlanProdReqDate)
  SELECT 'GOTEK',c.Co_num,c.Co_line,cust_num,cust_seq,Cust_name,FrontSlsmanName,SlsmanName,ISNULL(ShipCodeDescr,'') AS ShipCodeDescr, ref_num,ref_line_suf,item,ItemDescription,
                             PlanStartProdDate,
                             PlanOnWhseDate,
                             PlanShipDate,
                             FactShipDate,
                             RowCreateDate,
                             FactProdReqDate,
                             FactOnWhseDate,FactDeliveryDate,ActualDeliveryDate, 
                             PlanDeliveryDate,PlanProdReqDate
from GTK_PL_Gotek..GTK_coRow c,  GTK_PL_Gotek..GTK_coiRow ci 
where  c.Co_num = ci.Co_num and c.Co_line = ci.Co_line  and FactShipDate BETWEEN @v_startdate AND @v_enddate
AND ISNULL(c.Cust_name,'')<> '' 

--IF (@v_type_rep = 4) begin

 Update gc set
    gc.statusrow = case p.PrichOtklJob when '1' then 'Недоступность сырья и материалов' 
                  when '2' then 'Снижение производственной скорости оборудования'
                  when '3' then 'Простои оборудования'
                  when '4' then 'Раннее производство заказов для оптимальной комплектации'
                  when '5' then 'Позднее производство заказов для оптимальной комплектации' 
                  when '6' then 'Производство срочных/внеплановых заказов'
                  when '7' then 'Перенос срока поставки заказа клиентом' 
                  when '8' then 'Досрочное изготовление заказо из-за отсутсвия загрузки' 
                  else 'Без отклонений' 
       END,
     gc.FactProdReqDate = p.DatePlan  
     from #gtk_rpt_logist gc
     left outer join SL_GOTEK.dbo.coitem ci on (gc.co_num = ci.co_num and gc.co_line = ci.co_line )
     left outer join SL_GOTEK.dbo.GTK_ASC_Plan p on (ci.ref_num = p.job )

--END
UPDATE gc SET
    gc.statusrow = m.Uf_DescrOtmenaDost,
    gc.FactDeliveryDate = c.DateDostFact,
    gc.PlanDeliveryDate_m = dateadd(hh,- case when Probeg > 500 then Probeg/50 + (Probeg/500 - 1)*12 else Probeg/50 end , c.DateDost), 
    gc.DatePlan_Ship_m = Uf_DateOtgrRasch
    from #gtk_rpt_logist gc
	jOIN  SL_GOTEK.dbo.GTK_CAR_ZayLine ci ON  ci.co_num = gc.Co_Num AND ci.Co_Line = gc.Co_Line
	--left outer join SL_GOTEK.dbo.GTK_CAR_MerchZayLns ci on (gc.co_num = ci.CoNum and gc.co_line = ci.CoLine )
	left outer join SL_GOTEK.dbo.GTK_CAR_ZayHdr c on (ci.ZayNum = c.ZayNum)
	left outer join SL_GOTEK.dbo.GTK_CAR_MerchZay m on (c.MerchZay = m.ZayNum)
	


IF (@v_type_rep = 6) BEGIN
 Update gc SET
    gc.statusrow = c.DescrOtmena,
     gc.PlanDeliveryDate_m = dateadd(hh,- case when Probeg > 500 then Probeg/50 + (Probeg/500 - 1)*12 else Probeg/50 end , c.DateDost), 
    gc.DatePlan_Ship_m = Uf_DateOtgrRasch
    from #gtk_rpt_logist gc
	jOIN  SL_GOTEK.dbo.GTK_CAR_MerchZayLns ci ON  ci.conum = gc.Co_Num AND ci.CoLine = gc.Co_Line
	--left outer join SL_GOTEK.dbo.GTK_CAR_MerchZayLns ci on (gc.co_num = ci.CoNum and gc.co_line = ci.CoLine )
	left outer join SL_GOTEK.dbo.GTK_CAR_MerchZay c on (ci.ZayNum = c.ZayNum)
	left outer join SL_GOTEK.dbo.GTK_CAR_ZayHdr z on (z.MerchZay = c.ZayNum)
	where c.Otmena = 0 and c.VidOtgr = '01' 

end

/*IF (@v_type_rep = 7) BEGIN
  UPDATE gc SET
    gc.statusrow = m.Uf_DescrOtmenaDost,
    gc.FactDeliveryDate = c.DateDostFact
    from #gtk_rpt_logist gc
	jOIN  SL_GOTEK.dbo.GTK_CAR_ZayLine ci ON  ci.co_num = gc.Co_Num AND ci.Co_Line = gc.Co_Line
	--left outer join SL_GOTEK.dbo.GTK_CAR_MerchZayLns ci on (gc.co_num = ci.CoNum and gc.co_line = ci.CoLine )
	left outer join SL_GOTEK.dbo.GTK_CAR_ZayHdr c on (ci.ZayNum = c.ZayNum)
	left outer join SL_GOTEK.dbo.GTK_CAR_MerchZay m on (c.MerchZay = m.ZayNum)
	where m.Uf_DescrOtmenaDost is not null

end
*/




END

IF(@v_site = 'Center' )BEGIN
INSERT into #gtk_rpt_logist (Site,Co_num,Co_line,cust_num,cust_seq,
                              Cust_name,FrontSlsmanName,SlsmanName,ShipCodeDescr, ref_num,ref_line_suf,
                              item,Item_Desc,
                              DatePlan_Mnfg,DatePlan_Whse,DatePlan_Ship,DateActual_Ship,DateCreate_Row ,FactProdReqDate,FactOnWhseDate,FactDeliveryDate,ActualDeliveryDate, 
                             PlanDeliveryDate,PlanProdReqDate)
  SELECT 'Center',c.Co_num,c.Co_line,cust_num,cust_seq,Cust_name,FrontSlsmanName,SlsmanName,ShipCodeDescr, ref_num,ref_line_suf,item,ItemDescription,
                             PlanStartProdDate,PlanOnWhseDate,PlanShipDate,FactShipDate,RowCreateDate,
                             FactProdReqDate,FactOnWhseDate,FactDeliveryDate,ActualDeliveryDate, 
                             PlanDeliveryDate,PlanProdReqDate
from gtk_pl_center..GTK_coRow c,  gtk_pl_center..GTK_coiRow ci 
where  c.Co_num = ci.Co_num and c.Co_line = ci.Co_line  and FactShipDate BETWEEN @v_startdate AND @v_enddate
AND ISNULL(c.Cust_name,'')<> ''
--IF (@v_type_rep = 4) begin
 Update gc set
     gc.statusrow = case p.PrichOtklJob when '1' then 'Недоступность сырья и материалов' 
                  when '2' then 'Снижение производственной скорости оборудования'
                  when '3' then 'Простои оборудования'
                  when '4' then 'Раннее производство заказов для оптимальной комплектации'
                  when '5' then 'Позднее производство заказов для оптимальной комплектации' 
                  when '6' then 'Производство срочных/внеплановых заказов'
                  when '7' then 'Перенос срока поставки заказа клиентом' 
                  when '8' then 'Досрочное изготовление заказо из-за отсутсвия загрузки' 
                  else 'Без отклонений' 
       end,
     gc.FactProdReqDate = p.DatePlan  
    from #gtk_rpt_logist gc
    left outer join SL_Center.dbo.coitem ci on (gc.co_num = ci.co_num and gc.co_line = ci.co_line )
    left outer join SL_Center.dbo.GTK_ASC_Plan p on (ci.ref_num = p.job )

--END

UPDATE gc SET
    gc.statusrow = m.Uf_DescrOtmenaDost,
    gc.FactDeliveryDate = c.DateDostFact,
    gc.PlanDeliveryDate_m = dateadd(hh,- case when Probeg > 500 then Probeg/50 + (Probeg/500 - 1)*12 else Probeg/50 end , c.DateDost), 
    gc.DatePlan_Ship_m = Uf_DateOtgrRasch
    from #gtk_rpt_logist gc
	jOIN  SL_Center.dbo.GTK_CAR_ZayLine ci ON  ci.co_num = gc.Co_Num AND ci.Co_Line = gc.Co_Line
	--left outer join SL_GOTEK.dbo.GTK_CAR_MerchZayLns ci on (gc.co_num = ci.CoNum and gc.co_line = ci.CoLine )
	left outer join SL_Center.dbo.GTK_CAR_ZayHdr c on (ci.ZayNum = c.ZayNum)
	left outer join SL_Center.dbo.GTK_CAR_MerchZay m on (c.MerchZay = m.ZayNum)
	--where m.Uf_DescrOtmenaDost is not null


IF (@v_type_rep = 6) BEGIN
     Update gc SET
      gc.statusrow = c.DescrOtmena
      
		from #gtk_rpt_logist gc
		jOIN  SL_Center.dbo.GTK_CAR_MerchZayLns ci ON  ci.conum = gc.Co_Num AND ci.CoLine = gc.Co_Line
		--left outer join SL_GOTEK.dbo.GTK_CAR_MerchZayLns ci on (gc.co_num = ci.CoNum and gc.co_line = ci.CoLine )
		left outer join SL_Center.dbo.GTK_CAR_MerchZay c on (ci.ZayNum = c.ZayNum)
		left outer join SL_Center.dbo.GTK_CAR_ZayHdr z on (z.MerchZay = c.ZayNum)
		--where c.Otmena = 0 and c.VidOtgr = '01' and c.DescrOtmena is not NULL

     END
END
/*IF (@v_type_rep = 7) BEGIN
  UPDATE gc SET
    gc.statusrow = m.Uf_DescrOtmenaDost,
    gc.FactDeliveryDate = c.DateDostFact
    from #gtk_rpt_logist gc
	jOIN  SL_Center.dbo.GTK_CAR_ZayLine ci ON  ci.co_num = gc.Co_Num AND ci.Co_Line = gc.Co_Line
	--left outer join SL_GOTEK.dbo.GTK_CAR_MerchZayLns ci on (gc.co_num = ci.CoNum and gc.co_line = ci.CoLine )
	left outer join SL_Center.dbo.GTK_CAR_ZayHdr c on (ci.ZayNum = c.ZayNum)
	left outer join SL_Center.dbo.GTK_CAR_MerchZay m on (c.MerchZay = m.ZayNum)
	where m.Uf_DescrOtmenaDost is not null

end

*/

IF(@v_site = 'Spb' )BEGIN
	INSERT into #gtk_rpt_logist (Site,Co_num,Co_line,cust_num,cust_seq,
								  Cust_name,FrontSlsmanName,SlsmanName,ShipCodeDescr, ref_num,ref_line_suf,
								  item,Item_Desc,
								  DatePlan_Mnfg,DatePlan_Whse,DatePlan_Ship,DateActual_Ship,DateCreate_Row ,
								  FactProdReqDate,FactOnWhseDate,FactDeliveryDate,ActualDeliveryDate, 
								  PlanDeliveryDate,PlanProdReqDate)
	  SELECT 'Spb',c.Co_num,c.Co_line,cust_num,cust_seq,Cust_name,FrontSlsmanName,SlsmanName,ShipCodeDescr, ref_num,ref_line_suf,item,ItemDescription,
								 PlanStartProdDate,PlanOnWhseDate,PlanShipDate,FactShipDate,RowCreateDate,
								 FactProdReqDate,FactOnWhseDate,FactDeliveryDate,ActualDeliveryDate, 
								 PlanDeliveryDate,PlanProdReqDate
	from GTK_PL_SPB..GTK_coRow c,  GTK_PL_SPB..GTK_coiRow ci 
	where  c.Co_num = ci.Co_num and c.Co_line = ci.Co_line  and FactShipDate BETWEEN @v_startdate AND @v_enddate
	AND ISNULL(c.Cust_name,'')<> ''
--	IF (@v_type_rep = 4) begin
		Update gc set
			 gc.statusrow = case p.PrichOtklJob when '1' then 'Недоступность сырья и материалов' 
						  when '2' THEN 'Снижение производственной скорости оборудования'
						  when '3' then 'Простои оборудования'
						  when '4' then 'Раннее производство заказов для оптимальной комплектации'
						  when '5' then 'Позднее производство заказов для оптимальной комплектации' 
						  when '6' then 'Производство срочных/внеплановых заказов'
						  when '7' then 'Перенос срока поставки заказа клиентом' 
						  when '8' then 'Досрочное изготовление заказо из-за отсутсвия загрузки' 
						  else 'Без отклонений' 
						  end,
			 gc.FactProdReqDate = p.DatePlan  
			 from #gtk_rpt_logist gc
			 left outer join SL_spb.dbo.coitem ci on (gc.co_num = ci.co_num and gc.co_line = ci.co_line )
			 left outer join SL_spb.dbo.GTK_ASC_Plan p on (ci.ref_num = p.job )
--	END

 UPDATE gc SET
    gc.statusrow = m.Uf_DescrOtmenaDost,
    gc.FactDeliveryDate = c.DateDostFact,
    gc.PlanDeliveryDate_m = dateadd(hh,- case when Probeg > 500 then Probeg/50 + (Probeg/500 - 1)*12 else Probeg/50 end , c.DateDost), 
    gc.DatePlan_Ship_m = Uf_DateOtgrRasch
    from #gtk_rpt_logist gc
	jOIN  SL_spb.dbo.GTK_CAR_ZayLine ci ON  ci.co_num = gc.Co_Num AND ci.Co_Line = gc.Co_Line
	--left outer join SL_GOTEK.dbo.GTK_CAR_MerchZayLns ci on (gc.co_num = ci.CoNum and gc.co_line = ci.CoLine )
	left outer join SL_spb.dbo.GTK_CAR_ZayHdr c on (ci.ZayNum = c.ZayNum)
	left outer join SL_spb.dbo.GTK_CAR_MerchZay m on (c.MerchZay = m.ZayNum)
	where m.Uf_DescrOtmenaDost is not null



	IF (@v_type_rep = 6) BEGIN
     Update gc SET
      gc.statusrow = c.DescrOtmena
		from #gtk_rpt_logist gc
		jOIN  SL_spb.dbo.GTK_CAR_MerchZayLns ci ON  ci.conum = gc.Co_Num AND ci.CoLine = gc.Co_Line
		--left outer join SL_GOTEK.dbo.GTK_CAR_MerchZayLns ci on (gc.co_num = ci.CoNum and gc.co_line = ci.CoLine )
		left outer join SL_spb.dbo.GTK_CAR_MerchZay c on (ci.ZayNum = c.ZayNum)
		left outer join SL_spb.dbo.GTK_CAR_ZayHdr z on (z.MerchZay = c.ZayNum)
		where c.Otmena = 0 and c.VidOtgr = '01' and c.DescrOtmena is not NULL

     END
/*IF (@v_type_rep = 7) BEGIN
  UPDATE gc SET
    gc.statusrow = m.Uf_DescrOtmenaDost,
    gc.FactDeliveryDate = c.DateDostFact
    from #gtk_rpt_logist gc
	jOIN  SL_spb.dbo.GTK_CAR_ZayLine ci ON  ci.co_num = gc.Co_Num AND ci.Co_Line = gc.Co_Line
	--left outer join SL_GOTEK.dbo.GTK_CAR_MerchZayLns ci on (gc.co_num = ci.CoNum and gc.co_line = ci.CoLine )
	left outer join SL_spb.dbo.GTK_CAR_ZayHdr c on (ci.ZayNum = c.ZayNum)
	left outer join SL_spb.dbo.GTK_CAR_MerchZay m on (c.MerchZay = m.ZayNum)
	where m.Uf_DescrOtmenaDost is not null

end
*/
end
/*
--1- точность открытия заказов

if @type_rep = 1
  begin
    
    update gtk_rpt_logist set statusrow = 'С отклонениями' where DateActual_Ship - DateCreate_Row  > 2  
    update gtk_rpt_logist set statusrow = 'Без отклонений' where DatePlan_Mnfg - DateCreate_Row  <= 2          
  end

--2 точность планирования заказов продажами
if @type_rep = 2
  begin
    update gtk_rpt_logist set statusrow = 'С отклонениями' where DatePlan_Mnfg - DatePlan_Ship  > 0  
    update gtk_rpt_logist set statusrow = 'Без отклонений' where DatePlan_Mnfg - DatePlan_Ship  = 0
                   
  end
-- точность постановки заказов в производство
if @type_rep = 3
  begin
    Update gc set
    gc.statusrow = case p.PrichOtklJob when '1' then 'Недоступность сырья и материалов' 
                  when '2' then 'Снижение производственной скорости оборудования'
                  when '3' then 'Простои оборудования'
                  when '4' then 'Раннее производство заказов для оптимальной комплектации'
                  when '5' then 'Позднее производство заказов для оптимальной комплектации' 
                  when '6' then 'Производство срочных/внеплановых заказов'
                  when '7' then 'Перенос срока поставки заказа клиентом' 
                  when '8' then 'Досрочное изготовление заказо из-за отсутсвия загрузки' 
                  else 'Без отклонений' 
       end
     from gtk_rpt_logist gc
     left outer join SL_GOTEK.dbo.coitem ci on (gc.co_num = ci.co_num and gc.co_line = ci.co_line )
     left outer join SL_GOTEK.dbo.GTK_ASC_Plan p on (ci.ref_num = p.job )
     where gc.DatePlan_Mnfg >= @startdate  and gc.site = 'GOTEK'

     Update gc set
     gc.statusrow = case p.PrichOtklJob when '1' then 'Недоступность сырья и материалов' 
                  when '2' then 'Снижение производственной скорости оборудования'
                  when '3' then 'Простои оборудования'
                  when '4' then 'Раннее производство заказов для оптимальной комплектации'
                  when '5' then 'Позднее производство заказов для оптимальной комплектации' 
                  when '6' then 'Производство срочных/внеплановых заказов'
                  when '7' then 'Перенос срока поставки заказа клиентом' 
                  when '8' then 'Досрочное изготовление заказо из-за отсутсвия загрузки' 
                  else 'Без отклонений' 
       end
    from gtk_rpt_logist gc
    left outer join SL_Center.dbo.coitem ci on (gc.co_num = ci.co_num and gc.co_line = ci.co_line )
    left outer join SL_Center.dbo.GTK_ASC_Plan p on (ci.ref_num = p.job )
    where gc.DatePlan_Mnfg >= @startdate  and gc.site = 'Center'

     Update gc set
     gc.statusrow = case p.PrichOtklJob when '1' then 'Недоступность сырья и материалов' 
                  when '2' then 'Снижение производственной скорости оборудования'
                  when '3' then 'Простои оборудования'
                  when '4' then 'Раннее производство заказов для оптимальной комплектации'
                  when '5' then 'Позднее производство заказов для оптимальной комплектации' 
                  when '6' then 'Производство срочных/внеплановых заказов'
                  when '7' then 'Перенос срока поставки заказа клиентом' 
                  when '8' then 'Досрочное изготовление заказо из-за отсутсвия загрузки' 
                  else 'Без отклонений' 
                  end
     from gtk_rpt_logist gc
     left outer join SL_spb.dbo.coitem ci on (gc.co_num = ci.co_num and gc.co_line = ci.co_line )
     left outer join SL_spb.dbo.GTK_ASC_Plan p on (ci.ref_num = p.job )
     where gc.DatePlan_Mnfg >= @startdate  and gc.site = 'SPB'

  end
  
  --4 точность выхода заказов на склад 
if @type_rep = 4
  begin
    update gtk_rpt_logist set statusrow = 'С отклонениями' where DatePlan_Mnfg - DatePlan_Ship  > 0  
    update gtk_rpt_logist set statusrow = 'Без отклонений' where DatePlan_Mnfg - DatePlan_Ship  = 0
                   
  end

--  5- точность отгрузки заказов
if @type_rep = 5
  begin
    update gtk_rpt_logist set statusrow = 'С отклонениями' where DatePlan_Mnfg - DatePlan_Ship  > 0  
    update gtk_rpt_logist set statusrow = 'Без отклонений' where DatePlan_Mnfg - DatePlan_Ship  = 0
                   
  end
  
  --  6 точностьдоставки заказов
if @type_rep = 6
  begin
    update gtk_rpt_logist set statusrow = 'С отклонениями' where DatePlan_Mnfg - DatePlan_Ship  > 0  
    update gtk_rpt_logist set statusrow = 'Без отклонений' where DatePlan_Mnfg - DatePlan_Ship  = 0
                   
  END
  */
  
  
SELECT * FROM #gtk_rpt_logist -- WHERE Co_Num = 'ЗК00027405'  AND Co_Line = 558