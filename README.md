# 🎓 Student Management System

Un système complet de gestion des étudiants développé avec **Spring Boot 3.5.0** et **PostgreSQL**. L'application offre une authentification JWT, une vérification par email, la réinitialisation de mot de passe, la gestion des demandes de congé et un contrôle d'accès basé sur les rôles (Admin/User).

## ✨ Fonctionnalités

### 🔐 Authentification & Autorisation
- Authentification avec JWT (JSON Web Token)
- Contrôle d'accès basé sur les rôles (ADMIN / USER)
- Inscription des utilisateurs avec vérification par email
- Réinitialisation de mot de passe avec token par email
- Sécurisation des endpoints avec Spring Security

### 👨‍🎓 Gestion des Étudiants
- Opérations CRUD (Créer, Lire, Mettre à jour, Supprimer)
- Pagination pour la liste des étudiants
- Assignation des étudiants à des départements
- Vérification par email pour les nouveaux étudiants

### 📅 Gestion des Demandes de Congé
- Les étudiants peuvent soumettre des demandes de congé
- Consultation de toutes les demandes d'un étudiant
- Suivi des demandes avec dates et motifs

### 📧 Services Email
- Email d'activation de compte avec token de vérification
- Email de réinitialisation de mot de passe avec token sécurisé
- Configuration SMTP avec Gmail

### 🛡️ Sécurité
- Cryptage des mots de passe avec BCrypt
- Génération et validation des tokens JWT
- Sécurité au niveau des méthodes avec `@PreAuthorize`
- Filtres de sécurité personnalisés

## 🏗️ Architecture Technique

┌─────────────────────────────────────────────────────┐
│ CLIENT (Postman / Frontend) │
└─────────────────────────────────────────────────────┘
│
▼
┌─────────────────────────────────────────────────────┐
│ CONTROLLER (Endpoints REST) │
│ - AuthController : /auth/* │
│ - EtudiantController : /etudiants/* │
│ - DepartmentController : /departments/* │
└─────────────────────────────────────────────────────┘
│
▼
┌─────────────────────────────────────────────────────┐
│ SERVICE (Logique Métier) │
│ - Authentification │
│ - Gestion des étudiants │
│ - Traitement des demandes de congé │
└─────────────────────────────────────────────────────┘
│
▼
┌─────────────────────────────────────────────────────┐
│ REPOSITORY (Accès aux données) │
│ - JPA/Hibernate avec PostgreSQL │
└─────────────────────────────────────────────────────┘


## 🚀 Technologies Utilisées

| Technologie | Version |
|-------------|---------|
| Spring Boot | 3.5.0 |
| Java | 17 |
| PostgreSQL | 16+ |
| JWT | 0.12.6 |
| Maven | 3.8+ |
| Lombok | Dernière version |

## 📋 Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- Java 17 ou supérieur
- Maven 3.8 ou supérieur
- PostgreSQL 14 ou supérieur
- Git (optionnel)

## 🔧 Installation et Configuration

### 1. Cloner le repository
```bash
git clone https://github.com/chaimaa123-info/Student-Management-System.git
cd Student-Management-System
``` 

### 2. Configurer PostgreSQL
```sql
-- Créer la base de données
CREATE DATABASE etudiant_sys;
 ``` 

### 3. Configurer l'application

Ouvrez le fichier `src/main/resources/application.properties` et configurez-le avec vos informations :

```properties
# Base de données
spring.datasource.url=jdbc:postgresql://localhost:5432/etudiant_sys
spring.datasource.username=postgres
spring.datasource.password=${DB_PASSWORD}

# Email (pour l'envoi des emails d'activation)
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=${GMAIL_APP_USERNAME}
spring.mail.password=${GMAIL_APP_PASSWORD}
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# JWT (clé secrète pour la génération des tokens)
jwt.secret=d2e85a98284ad65f2dd3d847ea58a5e9490e5ee697dbb328c2b1ea7f5eefd6db

# Serveur
server.port=8080
``` 

### 4. Compiler et exécuter

```bash
# Compiler le projet
mvn clean install

# Démarrer l'application
mvn spring-boot:run
``` 