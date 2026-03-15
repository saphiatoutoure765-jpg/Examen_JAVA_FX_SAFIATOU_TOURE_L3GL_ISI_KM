# Gestion Clinique Medicale

Projet Java realise dans le cadre du module Genie Logiciel — GL3.

## Technologies

- Java 17
- JavaFX 21
- Hibernate / JPA
- MySQL (Laragon)
- iText 5 (PDF)
- Lombok

## Prerequis

- JDK 17
- IntelliJ IDEA
- Laragon avec MySQL

## Comment lancer le projet

### 1. Creer la base de donnees

Ouvrir le terminal MySQL de Laragon et taper :

```sql
CREATE DATABASE clinique_db;
```

### 2. Ouvrir dans IntelliJ

- Ouvrir IntelliJ, cliquer sur Open et selectionner le dossier du projet
- Attendre que Maven telecharge les dependances

### 3. Lancer

- Lancer la classe Launcher.java
- Les tables se creent automatiquement par Hibernate

### 4. Creer un utilisateur admin

Dans le terminal MySQL apres le premier lancement :

```sql
USE clinique_db;

INSERT INTO utilisateurs (nom, prenom, email, motDePasse, role)
VALUES ('Admin', 'Super', 'admin@clinique.com',
'240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN');

INSERT INTO administrateurs (id, niveau)
VALUES (LAST_INSERT_ID(), 'SUPER');
```

### 5. Se connecter

- Email : admin@clinique.com
- Mot de passe : admin123

## Fonctionnalites

- Authentification avec gestion des roles (ADMIN, MEDECIN, RECEPTIONNISTE)
- Gestion des patients (ajout, modification, suppression, recherche)
- Gestion des rendez-vous avec verification des conflits d'horaire
- Gestion des consultations (diagnostic, prescription, observations)
- Facturation avec export PDF
- Generation d'ordonnances PDF

## Auteur

Projet realise dans le cadre du module Genie Logiciel — GL3
Institut Superieur d'Informatique (ISI) — Keur Massar
