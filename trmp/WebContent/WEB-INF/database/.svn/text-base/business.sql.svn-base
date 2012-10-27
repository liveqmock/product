-- Create table
create table DMLB
(
  DMLB NUMBER,
  LBSM VARCHAR2(100),
  YXBJ VARCHAR2(1) default '1'
)
tablespace SOLENT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table DMLB
  is '代码类别';
-- Add comments to the columns 
comment on column DMLB.DMLB
  is '代码类别';
comment on column DMLB.LBSM
  is '类别说明';
comment on column DMLB.YXBJ
  is '有效标记';

  
  
  -- Create table
create table DMSM
(
  DMLB  NUMBER,
  DMZ   VARCHAR2(30),
  DMSM1 VARCHAR2(100),
  DMSM2 VARCHAR2(100),
  YXBZ  VARCHAR2(1) default '1'
)
tablespace SOLENT
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    minextents 1
    maxextents unlimited
  );
-- Add comments to the table 
comment on table DMSM
  is '代码说明';
-- Add comments to the columns 
comment on column DMSM.DMLB
  is '代码类别';
comment on column DMSM.DMZ
  is '代码值';
comment on column DMSM.DMSM1
  is '代码说明1';
comment on column DMSM.DMSM2
  is '代码说明2';
comment on column DMSM.YXBZ
  is '有效标记';
  
  
--kongxiao 2010-10-15 11:23
  
create table DS_TM(
  				XH INTEGER CONSTRAINT PK_TM PRIMARY KEY,
  				BT VARCHAR2(200) NOT NULL ，
  				KSSJ DATE NOT NULL,
  				JSSJ DATE NOT NULL
  				);
CREATE TABLE DS_XX(
					BS VARCHAR2(10) NOT NULL ,
					NR VARCHAR2(200) NOT NULL ,
					PID INTEGER CONSTRAINT FK_XX REFERENCES DS_TM(XH),
					DJCS INTEGER ,
					constraint pk_XX primary key(BS,PID)
				);
				
			
create sequence auto_increment 
					increment by 1 
					start with 1 
					nomaxvalue 
					nocycle 
					cache 10 
					noorder;
					
---kongxiao end ----

					
					
drop table DS_KHZX cascade constraints;

/*==============================================================*/
/* Table: DS_KHZX                                               */
/*==============================================================*/
create table DS_KHZX  (
   XH                   NUMBER                          not null,
   V_USER               VARCHAR2(20),
   V_EMAIL              VARCHAR2(50),
   V_TEL                VARCHAR2(20),
   V_TITLE              VARCHAR2(50),
   V_CONTENT            VARCHAR2(500),
   constraint PK_DS_KHZX primary key (XH)
);

comment on table DS_KHZX is
'客户咨询信息表';

create table DS_NEWS  (
   I_XH                 NUMBER                          not null,
   V_TITLE              VARCHAR2(50),
   D_DATE               DATE,
   V_URL                VARCHAR2(100),
   V_NAME               VARCHAR2(20),
   I_STATE              NUMBER default(0),
   V_DESCRIBE           VARCHAR2(50),
   constraint PK_DS_NEWS primary key (I_XH)
);
comment on table DS_NEWS is
'新闻信息表';

create table JNY_CANTEENS 
(
    CANTEEN_ID           NUMBER                         not null,
    CITY_ID              INTEGER,
    FULLNAME             VARCHAR2(45),
    ACCOUNT_ID           VARCHAR2(30),
    ADDRESS              VARCHAR2(50),
    POSTCODE             VARCHAR2(10),
    PERSON_NAME          VARCHAR2(20),
    BANK                 VARCHAR2(50),
    PERSONAL_DEBIT_ID    VARCHAR2(30),
    HEAD_MAN_NAME        VARCHAR2(20),
    HEAD_MAN_FIXED_PHONE VARCHAR2(15),
    HEAD_MAN_CELL_PHONE  VARCHAR2(20),
    HEAD_MAN_FAX         VARCHAR2(15),
    HEAD_MAN_QQ          VARCHAR2(15),
    HEAD_MAN_EMAIL       VARCHAR2(50),
    SALESMAN_NAME        VARCHAR2(20),
    SALESMAN_FIXED_PHONE VARCHAR2(15),
    SALESMAN_CELL_PHONE  VARCHAR2(20),
    SALESMAN_FAX         VARCHAR2(15),
    SALESMAN_QQ          VARCHAR2(15),
    SALESMAN_EMAIL       VARCHAR2(50),
    CANTEEN_TYPE         VARCHAR2(20),
    DINNER_LEVEL         VARCHAR2(20),
    CANTEEN_PHOTO        BLOB,
    constraint PK_JNY_CANTEENS primary key (CANTEEN_ID)
);

comment on table JNY_CANTEENS is 
'餐厅表';


'车辆维护表'

create table  JNY_VEHICLE(
	ID INTEGER,
	CITY_ID VARCHAR2(20),
	MOTORCADE varchar2(40), '车队名称'
	BANK_NAME VARCHAR2(30),
	ACCOUNT VARCHAR2(20),
	ADDRESS VARCHAR2(50),
	POST_CODE VARCHAR2(6),
	SUPER_NAME VARCHAR2(10), '负责人名字'
	SUPER_TEL_NUM VARCHAR2(15),
	SUPER_CELLPHONE_NUM VARCHAR2(12),
	SUPER_FAX_NUM VARCHAR2(15),
	SUPER_QQ VARCHAR2(12),
	SUPER_EMAIL VARCHAR2(40),
	SERVICE_NAME VARCHAR2(10),
	SERVICE_TEL_NUM VARCHAR2(15),
	SERVICE_CELLPHONE_NUM VARCHAR2(12),
	SERVICE_FAX_NUM VARCHAR2(15),
	SERVICE_QQ VARCHAR2(12),
	SERVICE_EMAIL VARCHAR2(40),
	SPACE VARCHAR2(3), '座位的数量'
	LICENSE VARCHAR2(10),
	BUY_DATE DATE,
	DRIVER_NAME VARCHAR2(10),
	DRIVER_TEL_NUM VARCHAR2(12),
	DESCRIBE VARCHAR2(100)
);

/*==============================================================*/
/* Table: JNY_TRAVELAGENCY                                      */
/*==============================================================*/
create table JNY_TRAVELAGENCY 
(
    TRAVELAGENCY_ID      NUMBER                         not null,
    COMPANY_NAME         VARCHAR2(50),
    COMPANY_BANKNO       VARCHAR2(25),
    OPENING_BANK         VARCHAR2(50),
    COMPANY_ADDRESS      VARCHAR2(150),
    POSTCODE             VARCHAR2(10),
    MAN_NAME             VARCHAR2(10),
    BANK_NAME            VARCHAR2(50),
    MAN_BANKNO           VARCHAR2(25),
    CHIEF_NAME           VARCHAR2(20),
    FIXED_TELEPHONE      VARCHAR2(15),
    FIXED_MOBILEPHONE    VARCHAR2(15),
    FIXED_FAX            VARCHAR2(15),
    FIXED_QQ             VARCHAR2(11),
    FIXED_EMAIL          VARCHAR2(30),
    SALESMAN_NAME        VARCHAR2(10),
    SALESMAN_TELEPHONE   VARCHAR2(15),
    SALESMAN_MOBILEPHONE VARCHAR2(15),
    SALESMAN_FAX         VARCHAR2(15),
    SALESMAN_QQ          VARCHAR2(11),
    SALESMAN_EMAIL       VARCHAR2(30),
    BUSINESS_TYPE        NUMBER,
    LOGIC_DEL            NUMBER,
    constraint PK_JNY_TRAVELAGENCY primary key (TRAVELAGENCY_ID)
);

comment on table JNY_TRAVELAGENCY is 
'组团社信息表';

/*==============================================================*/
/* Table: JNY_TICKET                                            */
/*==============================================================*/
create table JNY_TICKET 
(
    TICKET_ID            NUMBER                         not null,
    CITY_ID              NUMBER,
    COM_NAME             VARCHAR2(50),
    COM_BANKNO           VARCHAR2(25),
    OPEN_BANK            VARCHAR2(50),
    COM_ADDRESS          VARCHAR2(150),
    POSTCODE             VARCHAR2(10),
    USER_NAME            VARCHAR2(10),
    BANK_NAME            VARCHAR2(50),
    USER_BANKNO          VARCHAR2(25),
    CHIEF_NAME           VARCHAR2(20),
    FIXED_TELEPHONE      VARCHAR2(15),
    FIXED_MOBILEPHONE    VARCHAR2(15),
    FIXED_FAX            VARCHAR2(15),
    FIXED_QQ             VARCHAR2(13),
    FIXED_EMAIL          VARCHAR2(30),
    SALESMAN_NAME        VARCHAR2(10),
    SALESMAN_TELEPHONE   VARCHAR2(15),
    SALESMAN_MOBILEPHONE VARCHAR2(15),
    SALESMAN_FAX         VARCHAR2(15),
    SALESMAN_QQ          VARCHAR2(13),
    SALESMAN_EMAIL       VARCHAR2(30),
    TICKET_TYPE          NUMBER,
    constraint PK_JNY_TICKET primary key (TICKET_ID)
);

comment on table JNY_TICKET is 
'票务信息表';

/*==============================================================*/
/* Table: JNY_SHOP                                              */
/*==============================================================*/
create table JNY_SHOP 
(
    SHOP_ID              NUMBER                         not null,
    CITY_ID              NUMBER,
    COM_NAME             VARCHAR2(50),
    COM_BANKNO           VARCHAR2(25),
    OPEN_BANK            VARCHAR2(50),
    COM_ADDRESS          VARCHAR2(150),
    POSTCODE             VARCHAR2(10),
    USER_NAME            VARCHAR2(10),
    BANK_NAME            VARCHAR2(50),
    USER_BANKNO          VARCHAR2(25),
    CHIEF_NAME           VARCHAR2(20),
    FIXED_TELEPHONE      VARCHAR2(15),
    FIXED_MOBILEPHONE    VARCHAR2(15),
    FIXED_FAX            VARCHAR2(15),
    FIXED_QQ             VARCHAR2(13),
    FIXED_EMAIL          VARCHAR2(30),
    SALESMAN_NAME        VARCHAR2(10),
    SALESMAN_TELEPHONE   VARCHAR2(15),
    SALESMAN_MOBILEPHONE VARCHAR2(15),
    SALESMAN_FAX         VARCHAR2(15),
    SALESMAN_QQ          VARCHAR2(13),
    SALESMAN_EMAIL       VARCHAR2(30),
    IF_SHOP              NUMBER,
    KICKBACK_SCALE       FLOAT,
    PEOPLE_FEE           FLOAT,
    COM_SCALE            FLOAT,
    ROYALTIES_SCALE      NUMBER,
    constraint PK_JNY_SHOP primary key (SHOP_ID)
);

comment on table JNY_SHOP is 
'购物点信息表';


/*==============================================================*/
/* Table: JNY_GROUP_TRAVELGENCY                                 */
/*==============================================================*/
create table JNY_GROUP_TRAVELGENCY 
(
    ID                   NUMBER                         not null,
    GROUP_ID             NUMBER,
    TRAVELAGENCY_NAME    VARCHAR2(30),
    RECEIVED_ADDRESS     VARCHAR2(30),
    SEND_ADDRESS         VARCHAR2(30),
    RECEIVE_TRAFFIC      NUMBER,
    SEND_TRAFFIC         NUMBER,
    GUEST_ADDRESS_ID     NUMBER,
    ADULT                NUMBER,
    CHILDREN             NUMBER,
    COMMENTS             VARCHAR2(100),
    RESERVE              NUMBER,
    constraint PK_JNY_GROUP_TRAVELGENCY primary key (ID)
);

comment on table JNY_GROUP_TRAVELGENCY is 
'团组团社';

alter table JNY_GROUP_TRAVELGENCY
   add constraint FK_JNY_GROU_REFERENCE_JNY_GROU foreign key (GROUP_ID)
      references JNY_GROUP (GROUP_ID)
      on update restrict
      on delete restrict;


/*==============================================================*/
/* Table: JNY_GROUP_SHOP                                        */
/*==============================================================*/
create table JNY_GROUP_SHOP 
(
    ID                   NUMBER                         not null,
    GROUP_ID             NUMBER,
    SHOP_ID              NUMBER,
    PER_FEE              NUMBER,
    PERSION_COUNT        NUMBER,
    reserve              varchar2(10),
    comments             varchar2(100),
    constraint PK_JNY_GROUP_SHOP primary key (ID)
);

comment on table JNY_GROUP_SHOP is 
'团购物';

alter table JNY_GROUP_SHOP
   add constraint FK_JNY_GROU_REFERENCE_JNY_GROU foreign key (GROUP_ID)
      references JNY_GROUP (GROUP_ID)
      on update restrict
      on delete restrict;
      
/*==============================================================*/
/* Table: JNY_GROUP_SHOP_DETAIL                                 */
/*==============================================================*/
create table JNY_GROUP_SHOP_DETAIL 
(
    ID                   NUMBER                         not null,
    JNY_GROUP_SHOP_ID    NUMBER,
    GOOD                 VARCHAR2(20),
    SUM_SHOP             NUMBER,
    PERSION_COUNT        NUMBER,
    reserve              varchar2(10),
    constraint PK_JNY_GROUP_SHOP_DETAIL primary key (ID)
);

comment on table JNY_GROUP_SHOP_DETAIL is 
'团购物明细';

alter table JNY_GROUP_SHOP_DETAIL
   add constraint FK_JNY_GROU_REFERENCE_JNY_GROU foreign key (JNY_GROUP_SHOP_ID)
      references JNY_GROUP_SHOP (ID)
      on update restrict
      on delete restrict;
      
/*==============================================================*/
/* Table: JNY_GROUP_TICKET                                      */
/*==============================================================*/
create table JNY_GROUP_TICKET 
(
    TICKET_ID            NUMBER,
    GROUP_ID             NUMBER,
    TICKET_DATE          DATE,
    PER_PRICE            NUMBER,
    COUNT                NUMBER,
    BEGIN_STATION        VARCHAR2(20),
    END_STATION          VARCHAR2(20),
    DUE_CONFIRM          NUMBER,
    COMMENTS             VARCHAR2(200),
    RESERVE              VARCHAR2(50)
);

comment on table JNY_GROUP_TICKET is 
'团票务';


/*==============================================================*/
/* Table: JNY_GROUP_GUIDE_OTHER                                 */
/*==============================================================*/
create table JNY_GROUP_GUIDE_OTHER 
(
    ID                   NUMBER                         not null,
    JNY_GROUP_GUIDE_ID   NUMBER,
    NAME                 VARCHAR2(20),
    FEE                  NUMBER,
    constraint PK_JNY_GROUP_GUIDE_OTHER primary key (ID)
);

comment on table JNY_GROUP_GUIDE_OTHER is 
'团队导游其他费用';

alter table JNY_GROUP_GUIDE_OTHER
   add constraint FK_JNY_GROU_REFERENCE_JNY_GROU foreign key (JNY_GROUP_GUIDE_ID)
      references JNY_GROUP_GUIDE (ID)
      on update restrict
      on delete restrict;
      
/*==============================================================*/
/* Table: JNY_GROUP_GUIDE                                       */
/*==============================================================*/
create table JNY_GROUP_GUIDE 
(
    ID                   NUMBER                         not null,
    GRUOP_ID             NUMBER,
    GUIDE_ID             NUMBER,
    DUE_CONFIRM          NUMBER,
    COMMENTS             VARCHAR2(100),
    RESEVE               VARCHAR2(50),
    constraint PK_JNY_GROUP_GUIDE primary key (ID)
);

comment on table JNY_GROUP_GUIDE is 
'团队导游';

alter table JNY_GROUP_GUIDE
   add constraint FK_JNY_GROU_REFERENCE_JNY_GROU foreign key (GRUOP_ID)
      references JNY_GROUP (GROUP_ID)
      on update restrict
      on delete restrict;

/*==============================================================*/
/* Table: JNY_GROUP_HOTEL                                       */
/*==============================================================*/
create table JNY_GROUP_HOTEL 
(
    HOTEL_ID             NUMBER,
    GROUP_ID             NUMBER,
    BOOKING_DATE         DATE,
    DUE_PRICE            NUMBER,
    DRIVER_NATIVE_FEE    NUMBER,
    DUE_CONFIRM          NUMBER,
    PAY_METHOD           NUMBER,
    COMMENTS             VARCHAR2(200),
    RESERVE              VARCHAR2(200)
);

comment on table JNY_GROUP_HOTEL is 
'团所对应的酒店';

alter table JNY_GROUP_HOTEL
   add constraint FK_JNY_GROU_REFERENCE_JNY_GROU foreign key (GROUP_ID)
      references JNY_GROUP (GROUP_ID)
      on update restrict
      on delete restrict;

/*==============================================================*/
/* Table: JNY_GROUP_RESTAURANT                                  */
/*==============================================================*/
create table JNY_GROUP_RESTAURANT 
(
    RESTAURANT_ID        NUMBER                         not null,
    GROUP_ID             NUMBER,
    BREAKFIRST_PRICE     NUMBER,
    DINNER_PRICE         NUMBER,
    DINNER_FACT_PRICE    NUMBER,
    "DATE"               DATE,
    PAY_METHOD           NUMBER,
    B_OR_D               NUMBER,
    COMMENTS             VARCHAR2(100),
    RESERVE              VARCHAR2(50),
    constraint PK_JNY_GROUP_RESTAURANT primary key (RESTAURANT_ID)
);

comment on table JNY_GROUP_RESTAURANT is 
'团餐厅';

alter table JNY_GROUP_RESTAURANT
   add constraint FK_JNY_GROU_REFERENCE_JNY_GROU foreign key (GROUP_ID)
      references JNY_GROUP (GROUP_ID)
      on update restrict
      on delete restrict;
 
/*==============================================================*/
/* Table: JNY_GROUP_GROUND_AGENCY                               */
/*==============================================================*/
create table JNY_GROUP_GROUND_AGENCY 
(
    ID                   NUMBER                         not null,
    JNY_GROUP_ID         NUMBER,
    GROUND_ID            NUMBER,
    OWE_MONEY            NUMBER,
    DEFICIT_MONEY        NUMBER,
    FAX_PICTURE1         BLOB,
    FAX_PICTURE2         BLOB,
    FAX_PICTURE3         BLOB,
    FAX_PICTURE4         BLOB,
    COMMENTS             VARCHAR2(100),
    constraint PK_JNY_GROUP_GROUND_AGENCY primary key (ID)
);

comment on table JNY_GROUP_GROUND_AGENCY is 
'团地接社';

alter table JNY_GROUP_GROUND_AGENCY
   add constraint FK_JNY_GROU_REFERENCE_JNY_GROU foreign key (JNY_GROUP_ID)
      references JNY_GROUP (GROUP_ID)
      on update restrict
      on delete restrict;


/*==============================================================*/
/* Table: JNY_GROUP_TRAVELGENCY_CONFIRM                         */
/*==============================================================*/
create table JNY_GROUP_TRAVELGENCY_CONFIRM 
(
    ID                   NUMBER                         not null,
    JNY_GROUP_TRAVELAGENCY_ID NUMBER,
    LINE                 NUMBER,
    DETAIL_PRICE         VARCHAR2(200),
    RECEIVABLE           NUMBER,
    FAX_PICTURE1         BLOB,
    FAX_PICTURE2         BLOB,
    FAX_PICTURE3         BLOB,
    FAX_PICTURE4         BLOB,
    COMMENTS             VARCHAR2(50),
    constraint PK_JNY_GROUP_TRAVELGENCY_CONFI primary key (ID)
);

comment on table JNY_GROUP_TRAVELGENCY_CONFIRM is 
'团确认件';


alter table JNY_GROUP_TRAVELGENCY_CONFIRM
   add constraint FK_JNY_GROU_REFERENCE_JNY_GROU foreign key (JNY_GROUP_TRAVELAGENCY_ID)
      references JNY_GROUP_TRAVELGENCY (ID)
      on update restrict
      on delete restrict;




/*==============================================================*/
/* Table: JNY_GROUP_VEHICLE                                     */
/*==============================================================*/
create table JNY_GROUP_VEHICLE 
(
    VEHICLE_ID           NUMBER,
    GROUP_ID             NUMBER,
    SEAT_COUNT           VARCHAR2(10),
    BEGIN_DATE           DATE,
    END_DATE             DATE,
    PRICE                NUMBER,
    DUE_CONFIRM          NUMBER,
    PAY_METHOD           NUMBER,
    COMMENTS             VARCHAR2(200),
    RESERVE              VARCHAR2(50)
);

comment on table JNY_GROUP_VEHICLE is 
'团车辆';


/*==============================================================*/
/* Table: JNY_GROUP_SCENERY                                     */
/*==============================================================*/
create table JNY_GROUP_SCENERY 
(
    SCENERY_ID           NUMBER,
    GROUP_ID             NUMBER,
    PAY_METHOD           NUMBER,
    PRICE                NUMBER,
    COMMENTS             VARCHAR2(200),
    RESERVE              VARCHAR2(100)
);

comment on table JNY_GROUP_SCENERY is 
'团景点';


/*==============================================================*/
/* Table: JNY_GROUP_ADD                                         */
/*==============================================================*/
create table JNY_GROUP_ADD 
(
    ID                   NUMBER                         not null,
    GROUP_ID             NUMBER,
    SCENERY_ID           NUMBER,
    PRICE                NUMBER,
    PEOPLE_COUNT         NUMBER,
    PER_FEE              NUMBER,
    BASIC_PRICE          NUMBER,
    PAY_METHOD           NUMBER,
    COMMENTS             VARCHAR2(100),
    reserve              varchar2(10),
    constraint PK_JNY_GROUP_ADD primary key (ID)
);

comment on table JNY_GROUP_ADD is 
'团加点';



