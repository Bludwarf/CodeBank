REM source : https://raymii.org/s/tutorials/Encrypt_and_decrypt_files_to_public_keys_via_the_OpenSSL_Command_Line.html
REM pour chiffrer des fichiers volumineux en évitant l'erreur suivante :
REM RSA operation error:  020:error:0406D06E:rsa routines:RSA_padding_add_PKCS1_type_2:data too large for key size:.\crypto\rsa\rsa_pk1.c:151:

set key=..\..\..\..\lib\Crypto\exs.capgemini.com.key
set cer=..\..\..\..\lib\Crypto\exs.capgemini.com.cer
set pubkey=publickey.pem

REM Clé publique
openssl rsa -in %key% -out %pubkey% -outform PEM -pubout

REM random password file
set pwd=password-file.bin
openssl rand -base64 128 -out %pwd%



REM Encrypt the file with the random key
set in=input.txt
openssl enc -aes-256-cbc -salt -in %in% -out %in%.enc -pass file:%pwd%

REM Encrypt the random key with the public keyfile : You can safely send the %pwd%.enc and the %in%.enc to the other party.
openssl rsautl -encrypt -inkey %pubkey% -pubin -in %pwd% -out %pwd%.enc



REM Decrypt the random key with our private key file-
openssl rsautl -decrypt -inkey %key% -in %pwd%.enc -out %pwd%

REM Decrypt the large file with the random key
openssl enc -d -aes-256-cbc -in %in%.enc -out %in% -pass file:%pwd%
