# Requirementsanalyse

- Eine CLI Verarbeitung
  - Datenbankurl, User, PW, Datenbankname, EER/RM, Filename können Übergeben werden.


- Eine Datenstruktur welche die Metadaten einer Datenbank speichert.
  - Erstellen eines Datenbank Objektes
    - Name und Tabellen werden gespeichert.
    - Setter und Getter Methoden.
    - toRMFile() erstellt ein File mit dem RM der Datenbank.
    - toRMConsole() gibt das RM in der Console aus.
    - toEER() erstellt ein PNG File mit dem EER Diagramm.
  - Erstellen eines Tabellen Objektes
    - Name und Attribute werden gespeichert.
    - Setter und Getter Methoden.  
  - Erstellen eines Attributes
    - Name, Typ, istFK, FKtabelle, FKentfernt und istPK wird gespeichert
    - Setter und Getter Methoden
    - FKtabelle als Relation zur Tabelle
    - FKentfert als Relation zum Attribut.


- Eine Verarbeitung welche die Metadaten aus der Datenbank ausliest und in die Datenstruktur speichert.
  - 


- Eine Verarbeitung zur Erstellung von RM Files (.txt)


- Eine passenden Library für die grafische Verarbeitung.


- Ein Verarbeitung zur Erstellung von ERD Files (.png/.jpeg)
