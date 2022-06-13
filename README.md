# NOVI Educational Backend

## Beschrijving
Deze backend is gebouwd door NOVI en mag alleen worden gebruikt voor opleidings-doeleinden.

Wanneer studenten de Fullstack leerlijn volgen, ontstaan veel vragen over hoe de communicatie werkt tussen de front-end en back-end applicattie. Deze backend is ontwikkeld voor het demonstreren van deze communicatie. Hierbij kan gebruik worden gemaakt van de _CRUD_ requesten voor een student en het uploaden en downloaden van een afbeelding. Het is niet mogelijk om andere informatie (naast `studentNumber`, `email`, `naam`, `opleiding` en `afbeelding`) op te slaan in deze database.

Binnen de backend wordt gebruik gemaakt van een postgres database, deze draait op jdbc:postgresql://localhost:5432/communication. Deze instellingen staan in de application.properties. Deze zullen aangepast moeten worden als u deze applicatie op uw eigen pc wilt draaien. 
Pas in dat geval de volgende waarde aan:
- spring.datasource.url (pas hier de naam "communication" aan, naar de naam van uw eigen database).
- spring.datasource.username (verander deze waarde naar de naam van de hoofdgebruiker van uw database).
- spring.datasource.password (verander deze waarde naar het wachtwoord van uw eigen database).

In deze applicatie worden afbeeldingen niet opgeslagen in de database, maar in de projectmap van de applicatie. Als u deze code cloned, moet ook de volgende waarde aangepast worden in de application.properties:
- my.upload_location (pas deze locatie aan naar de plaats waar u het project op uw pc hebt staan).

## Inhoud
* [Beschrijving](#beschrijving)
* [Rest endpoints](#rest-endpoints)
    * [Student opslaan](#1-student-aanmaken)
    * [Student opvragen](#2-student-opvragen)
    * [Alle Studenten opvragen](#3-alle-studenten-opvragen)
    * [Student gegevens bewerken](*4-student-gegevens-bewerken)
    * [Afbeelding voor student opslaan](#5-afbeelding-voor-student-opslaan)
    * [Student verwijderen](#6-student-verwijderen-uit-database)
    * [Afbeelding opslaan](#7-afbeelding-uploaden)
    * [Afbeelding ophalen](#8-afbeelding-ophalen)

## Rest endpoints
Alle rest-endpoints draaien op deze server: http://localhost:8080 Dit is de basis-uri. Alle voorbeeld-data betreffende de endpoints zijn in JSON format weergegeven. 

### 1. Student aanmaken.
`POST /students`

Het aanmaken van een nieuwe student vereist de volgende informatie:

```json
{
   "emailAddress": "johan.v@test.nl",
   "name": "Johan van Oosten",
   "course": "Back-end"
}
```

### 2. Student opvragen.
`GET /students/1001`

Het ophalen van een student, gebeurd aan de hand van het studenten nummer. Deze sturen we mee via de url, in dit geval 1001.


### 3. Alle studenten opvragen.
`GET /students`

Hiermee halen we alle geregistreerde studenten uit de database.

### 4. Student gegevens bewerken.
`PUT /students/1001`

Door het studentnummer mee te geven in de url, kunnen we de student met dat studentnummer bewerken. Het json object dat mee gestuurd moet worden is:

```json
{
    "emailAddress": "sam.b@test.nl",
    "name": "Sam Barnhoorn",
    "course": "Back-end",
    "fileDocument": null
}
```
Hierbij zijn de waarde van de velden (de delen achter de dubbele punt) variabel en kunnen dus gewijzigd worden.

### 5. Afbeelding voor student opslaan.
`POST /students/1001/photo`

Er kan een pasfoto worden toegevoegd aan een student door een afbeelding te uploaden naar deze url, waarbij 1001 het studentnummer is. Dit gebeurd door de afbeelding te versturen onder de key: "file":


### 6. Student verwijderen uit database.
`DELETE /students/1001`

Doormiddel van het studentnummer wat is mee gestuurd in de url, worden de student gegevens van de student met dit nummer verwijderd uit de database.

### 7. Afbeelding uploaden
`POST /upload`


Er kan een pasfoto worden toegevoegd aan de database door een afbeelding te uploaden naar deze url. Dit gebeurd door de afbeelding te versturen onder de key: "file":

### 8. Afbeelding ophalen
`GET /download/foto.JPG`

Door in deze url de naam van de foto mee te sturen, wordt de foto opgehaald uit de filestorage. _Let op_ Deze naam moet exact overeenkomen!
