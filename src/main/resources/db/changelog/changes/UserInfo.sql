--changeset abc:createusertable
CREATE TABLE IF NOT EXISTS USERINFO(
id SERIAL PRIMARY KEY,
name VARCHAR(20) UNIQUE NOT NULL ,
password VARCHAR(200) NOT NULL,
roles VARCHAR(200)
);

 --rollback drop table USER_DETAILS; 
 