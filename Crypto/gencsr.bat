rem @echo off
setlocal EnableDelayedExpansion

IF [%1%] == [] (
   ECHO "Usage : gencsr <CN> [https-in]"
   REM ECHO "Usage : gencsr tb1.mailnotification.bouygtel.fr [https-in]"
   REM %1 : Common Name (CN) pour le futur certificat
   REM %2 : Nom de base du fichier. "https-in" par défaut
   EXIT /b 1
)

set CN=%1%

REM set env=prod
REM set pre=%CN:~0,3%
REM IF "%pre%"=="tb2" (
REM     set env=maq
REM )
REM IF "%pre%"=="tb1" (
REM     set env=pprod
REM )

REM IF [%2%] == [] (
REM 	set base=https-in-%env%
REM ) ELSE (
REM 	set tmp=%2%
REM 	set base=!tmp!-%env%
REM )
set base=https

set key=%base%.key
echo cle  : %key%

set csr=%base%.csr
echo csr  : %csr%

REM set OU=IVS
REM IF "%env%"=="prod" (
REM 	set OU=OSP
REM )
set OU=private
echo OU   : %OU%

openssl req -new -sha256 -passin pass:password -key "%key%" -out "%csr%" -subj "/C=FR/ST=Ille-et-Vilaine/L=Rennes/O=BludCorp/OU=%OU%/CN=%CN%"