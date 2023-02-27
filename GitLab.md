# rules

## Héritage de règles de base

Règles de base :

```yml
.default_rules:
  rules:
    - if: $CI_COMMIT_REF_NAME == "develop"
    - if: $CI_COMMIT_REF_NAME == "release/beta"
```

Héritage :

```yml
  rules:
    - !reference [.default_rules, rules]
```

**Attention** : Il faut bien écrire `default_rules` et pas `default-rules` qui ne fonctionne pas.

## changes

```yml
  rules:
    - changes:
        - dossier/**/*
```

**Attention** : ne pas oublier `/**/*` si on souhaite inclure tous les sous-dossiers et leurs sous-dossiers et ainsi de suite.
