Eclipse n'arrive pas à retrouver la Javadoc/les sources des dépendances Maven.
    1 - Dans le projet, aller dans Build Path puis Librairies
    2 - Supprimer toutes les entrées "M2_REPO" (ATTENTION : fait planter les tests JUnit du projet Érable)
    3 - Et pour la forme : Maven > Update Project
    
    Note suite au plantage des test JUnit d'Érable :
        En fait ce plantage venait des tests unitaires qui positionnent des variables d'environnement
        sauf si ces variables d'environnement sont déjà définies dans le système Windows pour l'utilisateur courant !