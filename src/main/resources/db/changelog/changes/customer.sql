--changeset user:createcustomertable

CREATE TABLE IF NOT EXISTS CUSTOMER(
    ID  SERIAL PRIMARY KEY,
    NAME varchar(50),
    EMAIL varchar(50),
    CONTACT_NO varchar(15)
);
 --rollback drop table CUSTOMER;