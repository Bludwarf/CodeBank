# Variables
MOUNT="/bin/mount"
basedir=`dirname $0`
user=$(whoami)
RETOUR=$?

# Première ligne d'un fichier
head -n 1 input.txt

# Dernière ligne d'un fichier
tail -n 1 input.txt
