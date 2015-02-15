--------------------------------------------------------
--  DDL for Table CATEGORY
--------------------------------------------------------

  CREATE TABLE "CATEGORY" 
   (	"ID" NUMBER(10,0), 
	"NAME" VARCHAR2(45 CHAR)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "CLASS" ;
--------------------------------------------------------
--  DDL for Table CUSTOMER
--------------------------------------------------------

  CREATE TABLE "CUSTOMER" 
   (	"ID" NUMBER(10,0), 
	"NAME" VARCHAR2(45 CHAR), 
	"ADDRESS" VARCHAR2(45 CHAR), 
	"CITY" VARCHAR2(45 CHAR), 
	"STATE_REGION" VARCHAR2(45 CHAR), 
	"COUNTRY" VARCHAR2(45 CHAR), 
	"ZIP" VARCHAR2(45 CHAR), 
	"PHONE" VARCHAR2(45 CHAR), 
	"EMAIL" VARCHAR2(45 CHAR), 
	"CC" VARCHAR2(19 CHAR)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "CLASS" ;
--------------------------------------------------------
--  DDL for Table CUSTOMER_ORDER
--------------------------------------------------------

  CREATE TABLE "CUSTOMER_ORDER" 
   (	"ID" NUMBER(10,0), 
	"AMOUNT" NUMBER(38,2), 
	"DATE_CREATED" DATE, 
	"CONFIRMATION_NUMBER" NUMBER(10,0), 
	"CUSTOMER_ID" NUMBER(10,0)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "CLASS" ;
--------------------------------------------------------
--  DDL for Table ORDERED_PRODUCT
--------------------------------------------------------

  CREATE TABLE "ORDERED_PRODUCT" 
   (	"CUSTOMER_ORDER_ID" NUMBER(10,0), 
	"PRODUCT_ID" NUMBER(10,0), 
	"QUANTITY" NUMBER(10,0)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "CLASS" ;
--------------------------------------------------------
--  DDL for Table PRODUCT
--------------------------------------------------------

  CREATE TABLE "PRODUCT" 
   (	"ID" NUMBER(10,0), 
	"NAME" VARCHAR2(45 CHAR), 
	"PRICE" NUMBER(38,2), 
	"DESCRIPTION" CLOB, 
	"LAST_UPDATE" DATE, 
	"CATEGORY_ID" NUMBER(10,0)
   ) PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "CLASS" 
 LOB ("DESCRIPTION") STORE AS (
  TABLESPACE "CLASS" ENABLE STORAGE IN ROW CHUNK 8192 PCTVERSION 10
  NOCACHE LOGGING 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)) ;


--------------------------------------------------------
--  Constraints for Table CATEGORY
--------------------------------------------------------

  ALTER TABLE "CATEGORY" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "CATEGORY" MODIFY ("NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CUSTOMER
--------------------------------------------------------

  ALTER TABLE "CUSTOMER" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER" MODIFY ("NAME" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER" MODIFY ("ADDRESS" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER" MODIFY ("CITY" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER" MODIFY ("STATE_REGION" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER" MODIFY ("COUNTRY" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER" MODIFY ("ZIP" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER" MODIFY ("PHONE" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER" MODIFY ("EMAIL" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER" MODIFY ("CC" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table CUSTOMER_ORDER
--------------------------------------------------------

  ALTER TABLE "CUSTOMER_ORDER" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER_ORDER" MODIFY ("AMOUNT" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER_ORDER" MODIFY ("DATE_CREATED" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER_ORDER" MODIFY ("CONFIRMATION_NUMBER" NOT NULL ENABLE);
 
  ALTER TABLE "CUSTOMER_ORDER" MODIFY ("CUSTOMER_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table ORDERED_PRODUCT
--------------------------------------------------------

  ALTER TABLE "ORDERED_PRODUCT" MODIFY ("CUSTOMER_ORDER_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ORDERED_PRODUCT" MODIFY ("PRODUCT_ID" NOT NULL ENABLE);
 
  ALTER TABLE "ORDERED_PRODUCT" MODIFY ("QUANTITY" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table PRODUCT
--------------------------------------------------------

  ALTER TABLE "PRODUCT" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "PRODUCT" MODIFY ("NAME" NOT NULL ENABLE);
 
  ALTER TABLE "PRODUCT" MODIFY ("PRICE" NOT NULL ENABLE);
 
  ALTER TABLE "PRODUCT" MODIFY ("LAST_UPDATE" NOT NULL ENABLE);
 
  ALTER TABLE "PRODUCT" MODIFY ("CATEGORY_ID" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table STAFF
--------------------------------------------------------

  ALTER TABLE "STAFF" MODIFY ("ID" NOT NULL ENABLE);
 
  ALTER TABLE "STAFF" ADD PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS 
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT)
  TABLESPACE "CLASS"  ENABLE;
--------------------------------------------------------
--  DDL for Trigger CATEGORY_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CATEGORY_ID_TRIG" BEFORE INSERT OR UPDATE ON category
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.id IS NULL THEN
    SELECT  category_id_1SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(id),0) INTO v_newVal FROM category;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT category_id_1SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
   -- assign the value from the sequence to emulate the identity column
   :new.id := v_newVal;
  END IF;
END;
/
ALTER TRIGGER "CATEGORY_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger CUSTOMER_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CUSTOMER_ID_TRIG" BEFORE INSERT OR UPDATE ON customer
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.id IS NULL THEN
    SELECT  customer_id_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(id),0) INTO v_newVal FROM customer;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT customer_id_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
   -- assign the value from the sequence to emulate the identity column
   :new.id := v_newVal;
  END IF;
END;
/
ALTER TRIGGER "CUSTOMER_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger CUSTOMER_ORDER_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "CUSTOMER_ORDER_ID_TRIG" BEFORE INSERT OR UPDATE ON customer_order
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.id IS NULL THEN
    SELECT  customer_order_id_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(id),0) INTO v_newVal FROM customer_order;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT customer_order_id_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
   -- assign the value from the sequence to emulate the identity column
   :new.id := v_newVal;
  END IF;
END;
/
ALTER TRIGGER "CUSTOMER_ORDER_ID_TRIG" ENABLE;
--------------------------------------------------------
--  DDL for Trigger PRODUCT_ID_TRIG
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "PRODUCT_ID_TRIG" BEFORE INSERT OR UPDATE ON product
FOR EACH ROW
DECLARE 
v_newVal NUMBER(12) := 0;
v_incval NUMBER(12) := 0;
BEGIN
  IF INSERTING AND :new.id IS NULL THEN
    SELECT  product_id_SEQ.NEXTVAL INTO v_newVal FROM DUAL;
    -- If this is the first time this table have been inserted into (sequence == 1)
    IF v_newVal = 1 THEN 
      --get the max indentity value from the table
      SELECT NVL(max(id),0) INTO v_newVal FROM product;
      v_newVal := v_newVal + 1;
      --set the sequence to that value
      LOOP
           EXIT WHEN v_incval>=v_newVal;
           SELECT product_id_SEQ.nextval INTO v_incval FROM dual;
      END LOOP;
    END IF;
   -- assign the value from the sequence to emulate the identity column
   :new.id := v_newVal;
  END IF;
END;
/
ALTER TRIGGER "PRODUCT_ID_TRIG" ENABLE;

