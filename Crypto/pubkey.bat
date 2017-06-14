@echo off

IF [%1] == [] (
   ECHO "Usage 1 : pubkey certificate.cer > publickey.pem"
   ECHO "Usage 2 : pubkey privatekey.*    > publickey.pem"
   EXIT /b 1
)

set in=%1%
set ext=%in:~-4,4%
IF "%ext%"==".cer" (
	REM src : http://stackoverflow.com/a/17155710/1655155
	openssl x509 -pubkey -noout -in %in%
) ELSE (
	openssl rsa -in %in% -outform PEM -pubout
)
