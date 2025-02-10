CREATE TABLE ORDER_ADM.SHEDLOCK (
    NAME                           VARCHAR2(64)    NOT NULL,
    LOCK_UNTIL                     TIMESTAMP       NOT NULL,
    LOCKED_AT                      TIMESTAMP       NOT NULL,
    LOCKED_BY                      VARCHAR2(255)   NOT NULL
  ) TABLESPACE TSDORDER01 INITRANS 2;

CREATE PUBLIC SYNONYM SHEDLOCK FOR ORDER_ADM.SHEDLOCK;

ALTER TABLE ORDER_ADM.SHEDLOCK
    ADD CONSTRAINT SHEDLOCK_PK PRIMARY KEY (NAME) USING INDEX INITRANS 2 TABLESPACE TSDORDER01;

COMMENT ON TABLE  ORDER_ADM.SHEDLOCK                                IS '[FRAMEWORK] - Tabela responsável pelo controle de shedlock do order.';
COMMENT ON COLUMN ORDER_ADM.SHEDLOCK.NAME                           IS '[NOT_SECURITY_APPLY] Chave primaria [SHEDLOCK_PK] Nome do respectivo job em execução.';
COMMENT ON COLUMN ORDER_ADM.SHEDLOCK.LOCK_UNTIL                     IS '[NOT_SECURITY_APPLY] Data e hora lockAtMostFor do lock.';
COMMENT ON COLUMN ORDER_ADM.SHEDLOCK.LOCKED_AT                      IS '[NOT_SECURITY_APPLY] Data e hora inicio do lock.';
COMMENT ON COLUMN ORDER_ADM.SHEDLOCK.LOCKED_BY                      IS '[NOT_SECURITY_APPLY] Objeto responsável pelo lock.';

GRANT SELECT, INSERT, UPDATE ON ORDER_ADM.SHEDLOCK                       TO ORDERUBR;
GRANT READ                   ON ORDER_ADM.SHEDLOCK                       TO RL_COMPLIANCE_NSA;
