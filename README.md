# Educat'IF

*Un git pour faciliter notre avancée sur le développement de l'appli Educat'IF*

## Initialisation

```
git clone https://github.com/RedHBlack/DASI_B3422.git
```

Il sera nécessaire de créer un token pour la connexion :

`Settings`->`Developer Settings `->`Personal access tokens (classic)`

**IMPORTANT** :
- Le main est protégé, il faut créer une branche :
  ```
  git checkout -b dev/billy
  ```
  
- Pour fusionner une branche avec une autre :
  ```
  git checkout destination-branch
  git merge origin-branch
  ```

- Pour placer les modifications sur le main il faut push sur l'origine puis faire une merge request
  ```
  git push origin my-new-branch
  ```
  
- Avant toute manipulation sur les fichiers faire :
  ```
  git  pull
  ```
  afin de télécharger la dernière version.
  
- Pour ajouter des fichier/modifications :
  ```
  git  add .
  git  commit -m "infos nécessaires à la compréhension"
  ```
  
- Pour soumettre des fichiers/modifications :
  ```
  git  push
  ```
  (sans ça le répertoire distant ne sera pas mis à jour)
