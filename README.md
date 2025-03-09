# Todolist
###  **Todolist - Application de Gestion de Tâches**
Une application simple en Java pour gérer des tâches avec sauvegarde en JSON via la bibliothèque **Gson**.

---

##  **Installation et Configuration**
### **Prérequis**
- Java 8+ installé (`java -version` pour vérifier)
- Maven installé (`mvn -version` pour vérifier)
- IntelliJ IDEA (ou tout autre IDE compatible avec Java)
- [Gson](https://github.com/google/gson) comme dépendance pour la sérialisation/désérialisation JSON

---

##  **Installation**
### **Cloner le projet**
```sh
git clone https://github.com/votre-repo/todolist-java.git
cd todolist-java
```

### **Ajouter Gson à votre projet**
Si vous utilisez **Maven**, ajoutez la dépendance suivante à votre fichier `pom.xml` :
```xml
<dependencies>
    <dependency>
        <groupId>com.google.code.gson</groupId>
        <artifactId>gson</artifactId>
        <version>2.10.1</version>
    </dependency>
</dependencies>
```

Si vous n'utilisez pas Maven, téléchargez `gson-2.10.1.jar` depuis [ici](https://mvnrepository.com/artifact/com.google.code.gson/gson) et ajoutez-le au classpath de votre projet.

### **Compiler et exécuter le projet**
Utilisez Maven pour compiler et exécuter :
```sh
mvn compile
mvn exec:java -Dexec.mainClass="Todolist"
```

Ou utilisez simplement :
```sh
javac Todolist.java
java Todolist
```

---

##  **Utilisation**
Une fois le programme lancé, vous pouvez entrer différentes commandes pour gérer vos tâches :

###  **Commandes disponibles**
| Commande            | Description |
|---------------------|------------|
| `add`              | Ajouter une nouvelle tâche |
| `update`           | Modifier la description d'une tâche |
| `delete`           | Supprimer une tâche |
| `mark-done`        | Marquer une tâche comme terminée |
| `mark-in-progress` | Marquer une tâche comme en cours |
| `mark-not-done`    | Réinitialiser une tâche à "todo" |
| `list`             | Afficher la liste des tâches (toutes ou par statut) |
| `exit`             | Quitter l'application |


---


###  **Auteur**
  Abir Hanned   


