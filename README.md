# TP2 - Développement d'Applications Réparties (LSI3)

**Étudiant :** Chayma Bouassida  
**Matière :** Développement d'applications réparties  
**Année universitaire :** 2026 – 2027

Projet Maven pour le TP2 : échange de chaînes de caractères et d'objets
entre Client/Serveur via les sockets TCP.

---

# Activité 2-1 : Échange de CC entre Client/Serveur

## Objectif
Étendre l'application TP1 pour envoyer une **opération complète** au serveur
(ex : `55 * 25`) au lieu d'un seul entier, et lui permettre de renvoyer le résultat.

## Protocole de communication

Le client envoie **3 lignes successives** au serveur :
1. Ligne 1 : premier opérande (ex : `55`)
2. Ligne 2 : opérateur (ex : `*`)
3. Ligne 3 : deuxième opérande (ex : `25`)

Le serveur répond par **une ligne** contenant le résultat.
La communication se termine quand le client envoie `quit`.

- **Flux utilisés :** `BufferedReader` / `PrintWriter` (basés sur les flux de la socket)
- **Encodage :** texte UTF-8 via `InputStreamReader`

## Validation de la syntaxe (côté client)

Avant tout envoi, le client valide la saisie avec une expression régulière :

`^\s*(-?\d+(?:\.\d+)?)\s*([+\-*/])\s*(-?\d+(?:\.\d+)?)\s*$`

Cette validation **empêche l'envoi** de chaînes incorrectes comme `25 ** 55` ou `abc + 3`.

Décomposition du motif :
- `^\s*` : début de chaîne, espaces optionnels
- `(-?\d+(?:\.\d+)?)` : premier opérande (entier ou décimal, signe optionnel)
- `\s*([+\-*/])\s*` : opérateur (`+`, `-`, `*`, `/`) entouré d'espaces optionnels
- `(-?\d+(?:\.\d+)?)` : deuxième opérande
- `\s*$` : espaces optionnels puis fin de chaîne

## Code Source

### Server.java
![Server Code](screenshots/a21_server_code.png)

### Client.java
![Client Code](screenshots/a21_client_code.png)

## Compilation
![Build](screenshots/a21_build.png)

## Déroulement de l'exécution

### 1. Serveur en attente
![Server waiting](screenshots/a21_server_waiting.png)

### 2. Client connecté
![Client connected](screenshots/a21_client_connected.png)

### 3. Serveur détecte la connexion
![Server connected](screenshots/a21_server_connected.png)

## Tests effectués

### Console Client — Tous les tests
![All tests client](screenshots/a21_all_tests_client.png)

### Console Serveur — Toutes les opérations reçues
![All tests server](screenshots/a21_all_tests_server.png)

## Résultats des tests

| Entrée | Attendu | Obtenu |
|---|---|---|
| `55 * 25` | 1375.0 | `55.0 * 25.0 = 1375.0` ✅ |
| `10 + 4` | 14.0 | `10.0 + 4.0 = 14.0` ✅ |
| `100 - 42` | 58.0 | `100.0 - 42.0 = 58.0` ✅ |
| `10 / 4` | 2.5 | `10.0 / 4.0 = 2.5` ✅ |
| `10 / 0` | Erreur | `Erreur : division par zéro impossible` ✅ |
| `25 ** 55` | Rejeté | `Syntaxe invalide, rien n'est envoyé` ✅ |
| `abc + 3` | Rejeté | `Syntaxe invalide, rien n'est envoyé` ✅ |
| `quit` | Fin | `Fin de la communication` ✅ |

## Interprétation

Le client envoie l'opération décomposée en **trois lignes** via `PrintWriter`.
Le serveur les reçoit via `BufferedReader.readLine()` et effectue le calcul.

**Point clé :** la validation syntaxique est faite **côté client AVANT l'envoi**, comme le montre
la console serveur — les chaînes invalides (`25 ** 55`, `abc + 3`) n'apparaissent jamais côté serveur.

L'utilisation de `PrintWriter` avec `autoFlush=true` garantit que chaque `println` est envoyé
immédiatement sans avoir à appeler explicitement `flush()`.

---