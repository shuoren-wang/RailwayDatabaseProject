## Railway Project SQL Files
- AllInOneSQL.sql will create tables, insert test data, create views and stored procs.
- AllInOneSQL.sql is generated from CombineSql.exe. if any child sql file is changed, we should run it again.
- CombineSql.exe combines only following files:
 - CREATE_TABLES.sql
 - TEST_DATA.sql
 - Views/*
 - StoredProcs/*
