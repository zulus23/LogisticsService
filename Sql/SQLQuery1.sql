USE [GTK_GROUP_REPORT]
GO

/****** Object:  Table [dbo].[GTK_RPT_LOGIST]    Script Date: 08/04/2016 16:39:58 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[GTK_RPT_LOGIST](
	[Site] [NVARCHAR](15) NULL,
	[Co_Num] [NVARCHAR](15) NULL,
	[Co_Line] [INT] NULL,
	[Cust_num] [NVARCHAR](15) NULL,
	[Cust_Seq] [INT] NULL,
	[Cust_name] [NVARCHAR](70) NULL,
	[FrontSlsmanName] [NVARCHAR](50) NULL,
	[SlsmanName] [NVARCHAR](50) NULL,
	[ShipCodeDescr] [NVARCHAR](12) NULL,
	[ref_num] [NVARCHAR](20) NULL,
	[ref_line_suf] [INT] NULL,
	[DatePlan_Mnfg] [DATETIME] NULL,
	[DatePlan_Whse] [DATETIME] NULL,
	[DatePlan_Ship] [DATETIME] NULL,
	[DateActual_Ship] [DATETIME] NULL,
	[DateCreate_Row] [DATETIME] NULL,
	[Item] [NVARCHAR](15) NULL,
	[Item_Desc] [NVARCHAR](60) NULL,
	[Remark_Row] [NVARCHAR](50) NULL
) ON [PRIMARY]

GO


