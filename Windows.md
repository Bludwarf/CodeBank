# Path

Pour actualiser le PATH. Il faut fermer l'invite commande et fermer aussi la fenêtre Explorer dans laquelle on a ouvert l'invite.

# Registre

## En ligne de commande

Consultation

    reg.exe query "HKLM\Software\etc"

Export (exemple pour sauvegarder les assoc fichiers)

    reg.exe export "HKEY_CURRENT_USER\Software\Microsoft\Windows\CurrentVersion\Explorer\FileExts" "D:\Presets\Windows\FileExts.reg"
    
## Nettoyer le menu contextuel (clic-droit)

    HKEY_CLASSES_ROOT\Directory\background\shellex\ContextMenuHandlers
    
    
# Services

Création :

    sc.exe create <new_service_name> binPath= "<path_to_the_service_executable>"
    
Mais pour mysql :

    mysqladmin -u root shutdown
    mysqld --install