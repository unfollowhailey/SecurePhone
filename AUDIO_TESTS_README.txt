================================================================================
                      AUDIO COMPONENTS TEST GUIDE
                          SecurePhone Project - Tflow
================================================================================

================================================================================
TESTS UNITAIRES (JUnit)
================================================================================

FICHIERS CRÉÉS:
1. client/src/test/java/com/securephone/client/audio/AudioBufferTest.java
   - Tests thread-safety du buffer audio
   - Tests push/poll/capacity/clear
   - Tests multi-threaded

2. client/src/test/java/com/securephone/client/audio/OpusCodecTest.java
   - Tests encode/decode du codec stub
   - Tests cas limites (null, empty)
   - Tests round-trip

3. client/src/test/java/com/securephone/client/models/AudioDeviceTest.java
   - Tests du modèle AudioDevice
   - Tests construction, getters, toString()
   - Tests différentes configurations (mono, stéréo, différents taux)

4. client/src/test/java/com/securephone/client/models/UserSessionTest.java
   - Tests du modèle UserSession
   - Tests setters/getters
   - Tests cycle de vie complet (login → select device → connect)

LANCER LES TESTS:
================

Option 1: Avec Maven (si pom.xml configuré avec junit)
-----------------------------------------------
cd /opt/lampp/htdocs/SecurePhone/client
mvn test

Option 2: Avec Gradle (si build.gradle configuré)
-----------------------------------------------
cd /opt/lampp/htdocs/SecurePhone/client
gradle test

Option 3: Directement avec JUnit (si jar disponible)
-----------------------------------------------
javac -cp /path/to/junit.jar src/test/java/com/securephone/client/audio/AudioBufferTest.java
java -cp /path/to/junit.jar:. org.junit.runner.JUnitCore com.securephone.client.audio.AudioBufferTest

RÉSULTATS ATTENDUS:
===================
Si tous les tests passent, vous devriez voir:
✓ AudioBufferTest: 8 tests
✓ OpusCodecTest: 9 tests
✓ AudioDeviceTest: 8 tests
✓ UserSessionTest: 10 tests

Total: 35 tests unitaires

================================================================================
TEST D'INTÉGRATION AUDIO (Interactive)
================================================================================

FICHIER CRÉÉ:
client/src/test/java/com/securephone/client/audio/AudioIntegrationTest.java

PRÉREQUIS:
- Machine avec microphone (INPUT)
- Machine avec haut-parleurs/casque (OUTPUT)
- Pas de son système bloquant (fermer VLC, etc.)

LANCER LE TEST:
===============

Méthode 1: Directement via Java
-------------------------------
cd /opt/lampp/htdocs/SecurePhone/client/src/test/java
javac -d . com/securephone/client/audio/*.java com/securephone/client/models/*.java
java -cp . com.securephone.client.audio.AudioIntegrationTest

Méthode 2: Via classpath complet
------------------------------
cd /opt/lampp/htdocs/SecurePhone
java -cp client/src/main/java:client/src/test/java com.securephone.client.audio.AudioIntegrationTest

SCÉNARIO DU TEST:
=================

1️⃣  TEST 1: AudioBuffer (automatique)
   - Crée un buffer de capacité 5
   - Push 3 frames de test
   - Poll et vérifie les données
   - Clear et vérifie la taille
   - Durée: < 1 seconde
   - Résultat attendu: "✓ AudioBuffer test passed."

2️⃣  TEST 2: Capture & Playback Audio (interactif)
   
   ÉTAPE A: Préparation
   - Le programme affiche les instructions
   - "Press ENTER to begin: "
   
   ÉTAPE B: Enregistrement (5 secondes)
   - Appuyez sur ENTRÉE
   - Le programme affiche: "🎤 RECORDING... (5 seconds)"
   - PARLEZ CLAIREMENT dans votre microphone
   - Attendez 5 secondes (le programme arrête automatiquement)
   
   ÉTAPE C: Relecture
   - Vous entendez votre voix rejouée dans les haut-parleurs
   - Le programme affiche: "🔊 PLAYBACK..."
   - Durée de relecture: ~5 secondes (durée de votre enregistrement)
   - Résultat attendu: "✓ Playback completed."

RÉSULTATS ATTENDUS:
===================

Sortie console:
```
=====================================
   AUDIO INTEGRATION TEST
=====================================

TEST 1: AudioBuffer push/poll
------------------------------
Pushed 3 frames. Buffer size: 3
Polled frame: [1, 2, 3]. Buffer size: 2
Cleared buffer. Size: 0
✓ AudioBuffer test passed.

TEST 2: Audio Capture and Playback
-----------------------------------

Microphone test:
1. Press ENTER to start recording
2. Speak clearly into your microphone
3. Wait 5 seconds, then recording stops automatically

Press ENTER to begin: [USER PRESSES ENTER]

🎤 RECORDING... (5 seconds)
⏹️  Recording stopped.
Buffer contains 48 frames.

🔊 PLAYBACK...
✓ Playback completed.

✓ All tests completed.
```

AUDIO QUALITY EXPECTATIONS:
===========================

Qualité attendue:
- Format: 48 kHz, 16-bit, Mono (standard VoIP)
- Latence: < 200ms entre capture et playback
- Pas d'écho/feedback (buffers séparés)
- Audio clair, sans saturation

DÉPANNAGE:
==========

Problème: "LineUnavailableException"
Cause: Pas d'appareil audio trouvé
Solution: Vérifier:
  - Microphone connecté et actif
  - Haut-parleurs connectés et non en mode muet
  - Pas d'autre application utilisant l'audio

Problème: Son déformé ou coupé
Cause: Buffer trop petit ou thread trop lent
Solution:
  - Augmenter la taille du buffer (AudioBuffer)
  - Réduire la taille des frames (actuellement 320 bytes)

Problème: Pas d'audio du tout
Cause: Application n'a pas accès aux périphériques
Solution:
  - Relancer avec permissions élevées (sudo si Linux)
  - Vérifier les paramètres audio du système

================================================================================
NOTES IMPORTANTES
================================================================================

✓ TESTS UNITAIRES:
  - Peuvent être lancés en CI/CD (GitHub Actions, Jenkins, etc.)
  - Pas de dépendance matérielle
  - Rapides (< 1 seconde)
  - Idéal pour regress testing

⏳ TEST D'INTÉGRATION:
  - Teste la vraie carte son
  - Requiert interaction utilisateur
  - Bon pour validation matérielle
  - À faire avant release en production

🔄 FUTUR:
  - Remplacer OpusCodec stub par opus-java ou binding JNI
  - Ajouter tests vidéo (VideoCapture, H264Codec, VideoPlayer)
  - Ajouter tests latence réseau (avec AudioClient/VideoClient)
  - Ajouter tests multi-utilisateurs (simulation serveur)

================================================================================
