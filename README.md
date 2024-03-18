# NOVI Studenten database (backend)

## Beschrijving
Deze backend is gebouwd door NOVI en mag alleen worden gebruikt voor opleidings-doeleinden.

Wanneer studenten de Fullstack leerlijn volgen, ontstaan veel vragen over hoe de communicatie werkt tussen de frontend en backend applicatie. Deze backend is ontwikkeld om dit te laten zien en werkt in combinatie met [deze](https://github.com/hogeschoolnovi/frontend-data-uitwisseling) frontend.

Wanneer studenten de Fullstack leerlijn volgen, ontstaan veel vragen over hoe de communicatie werkt tussen de front-end en back-end applicattie. Deze backend is ontwikkeld voor het demonstreren van deze communicatie. Hierbij kan gebruik worden gemaakt van de _CRUD_ requests om studenten op te vragen, toe te voegen of te wijzigen en kun je bestanden uploaden en downloaden. Het is niet mogelijk om andere informatie (naast `studentNumber`, `email`, `naam`, `opleiding`, `afbeelding` en `diploma`) op te slaan in deze database, tenzij je de backend applicatie zelf aanpast.

Binnen de backend wordt gebruik gemaakt van een PostGres database, deze draait op _jdbc:postgresql://localhost:5432/communication_. Deze instellingen staan in de `application.properties`. Deze zullen aangepast moeten worden als u deze applicatie op jouw locale machine wil draaien. 
Pas in dat geval de volgende waarde aan:
- `spring.datasource.url` (pas hier de naam "communication" aan, naar de naam van uw eigen database).
- `spring.datasource.username` (verander deze waarde naar de naam van de hoofdgebruiker van uw database).
- `spring.datasource.password` (verander deze waarde naar het wachtwoord van uw eigen database).

In deze applicatie worden twee manieren gedemonstreerd om bestanden op te slaan. 
- Het opslaan in de database. Het diploma van de student word in de database opgeslagen.
- Het opslaan in het bestandssysteem van je computer (of server). De foto van de student word in het bestandssysteem opgeslagen. 

### Disclaimer
Dit project is een POC en heeft enkel de nodige functionaliteiten om het principe van upload/download uit te leggen. Dingen als DTO's en security ontbreken, maar dienen in de eindopdracht natuurlijk wel gebruikt te worden.

## Inhoud
* [Beschrijving](#beschrijving)
* [Rest endpoints](#rest-endpoints)
    * [Student opslaan](#1-student-aanmaken)
    * [Student opvragen](#2-student-opvragen)
    * [Alle Studenten opvragen](#3-alle-studenten-opvragen)
    * [Student gegevens bewerken](#4-student-gegevens-bewerken)
    * [Student verwijderen](#5-student-verwijderen-uit-database)
    * [Diploma voor Student opslaan](#6-afbeelding-uploaden)
    * [Diploma voor Student ophalen](#7-afbeelding-ophalen)
    * [Afbeelding voor student opslaan](#8-afbeelding-voor-student-opslaan)
    * [Afbeelding voor student ophalen](#9-afbeelding-voor-student-opslaan)


## Rest endpoints
Alle rest-endpoints draaien op deze server: http://localhost:8080 Dit is de basis-uri. Alle voorbeeld-data betreffende de endpoints zijn in JSON format weergegeven. 

### 1. Student aanmaken
`POST /students`

Het aanmaken van een nieuwe student vereist de volgende informatie:

```json
{
   "emailAddress": "rowan.p@test.nl",
   "name": "Rowan Plooij",
   "course": "Back-end"
}
```

### 2. Specifieke student opvragen
`GET /students/1001`

Het ophalen van een student, gebeurd aan de hand van het studenten nummer. Deze sturen we mee via de url, in dit geval 1001.


### 3. Alle studenten opvragen
`GET /students`

Hiermee halen we alle geregistreerde studenten uit de database.

### 4. Student gegevens bewerken
`PUT /students/1001`

Door het studentnummer mee te geven in de url, kunnen we de student met dat studentnummer bewerken. Je kunt via dit endpoint niet de foto of het diploma van een student bewerken. Het json object dat mee gestuurd moet worden is:

```json
{
    "emailAddress": "sam.b@test.nl",
    "name": "Sam Barnhoorn",
    "course": "Back-end"
}
```
Hierbij zijn de waarde van de velden (de delen achter de dubbele punt) variabel en kunnen dus gewijzigd worden.


### 5. Student verwijderen uit database
`DELETE /students/1001`

Doormiddel van het studentnummer wat is mee gestuurd in de url, worden de student gegevens van de student met dit nummer verwijderd uit de database.

### 6. Afbeelding voor student opslaan
`POST /students/1001/photo`

Er kan een pasfoto worden toegevoegd aan een student door een afbeelding te uploaden naar deze url, waarbij 1001 het studentnummer is. Dit gebeurd door de afbeelding in de requestbody als `formData` te versturen onder de key: `"file"`:

### 7. Afbeelding voor student ophalen
`GET /students/1001/photo`

Elke student heeft 1 foto. Dit endpoint geeft je de foto die bij een student hoort. Hiervoor hoef je enkel het id van de student op te geven, bijvoorbeeld 1001. Als het request verzonden wordt in PostMan, zal de foto zichtbaar worden als het request succesvol is, omdat het request in de `content-type` header aangeeft dat het een `image/*` is. Ook in de browser zal de foto zichtbaar worden.


### 8. Diploma voor student opslaan
`POST /students/1001/diploma`


Een diploma is, net als een foto, een bestand dat je kunt uploaden. Deze wordt echter niet in het filesystem opgeslagen, maar wordt als byte array in de database opgeslagen. Het bestand wordt in de requestbody als `formData` ingevuld onder de `"file"` key.

### 9. Diploma voor student ophalen
`GET /students/1001/diploma`

Door in deze url het id van de student mee te sturen, wordt het diploma opgehaald uit de database en weergegeven in de responsebody.
