@echo off

IF [%1] == [] (
   ECHO "Usage : debugkey https-in-prod.key (extension .XXX obligatoire) => sortie dans le dossier ..\Tests\Wireshark"
   EXIT /b 1
)

set key=%1%
set base=%key:~0,-4%
set debugkey=..\Tests\Wireshark\%base%.debug.pem

openssl rsa -in %key% -out %debugkey%

echo "Cl‚ WireShark g‚n‚r‚e dans ..\Tests\Wireshark\%base%.debug.pem"
