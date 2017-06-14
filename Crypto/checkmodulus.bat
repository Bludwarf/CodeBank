@echo off

IF [%1] == [] (
   ECHO "Usage : checkmodulus https-in-prod (sans extension)"
   EXIT /b 1
)

set base=%1%
set key=%base%.key
set csr=%base%.csr
set cer=%base%.cer

openssl rsa -noout -modulus -in %key%
openssl req -noout -modulus -in %csr%
openssl x509 -noout -modulus -in %cer%
