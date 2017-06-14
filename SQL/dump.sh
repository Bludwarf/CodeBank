# Génère un script pour recréer entièrement une base de données
mysqldump --no-data --skip-comments --host=your_database_hostname_or_ip.com -u your_username --password=your_password your_database_name > your_database_name.sql

# idem pour une seule table
mysqldump --no-data --skip-comments --host=your_database_hostname_or_ip.com -u your_username --password=your_password your_database_name your_table > your_table.sql