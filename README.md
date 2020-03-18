# Pikatilaussysteemi 2020 Kevät - OTP Ryhmä 1 

This is a school project for electronic food ordering software that can be used by restaurants or cafes!

Kouluprojekti elektroniselle pikatilausohjelmistolle, joka esimerkiksi pikaruokalat tai kahvilat voivat käyttää!

## Ryhmän jäsenet

* Kimmo Perälä
* Samu Rinne
* Arttu Seuna
* Mikael Tikka

## Käytössä olevat tekniikat

* Java, JavaFX, JUnit 5
* Maven
* MySQL (MariaDB)
* Hibernate
* Docker
* Jenkins

## Projektin konfigurointi

### Tarvittavat lisäosat

Kaikki projektin käyttämät lisäosat löytyvät Pom.xml-tiedostosta, joten Maven hoitaa niiden hakemisen.

### Tietokanta
Mikäli haluat lähteä kehittämään sovellusta toimi seuraavasti: 

* Tee itsellesi paikalliset tietokannat testiympäristölle sekä itse ohjelmalle.
* Luo hibernate.cfg.xml tiedoston main/resources kansioon. 
* Vaihda molempien (main/resources/hibernate sekä test/resources/hibernate) tiedot oman tietokantasi tietoihin:
```
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/TIETOKANNAN_NIMI</property>
<property name="hibernate.connection.username">KÄYTTÄJÄTUNNUS</property>
<property name="hibernate.connection.password">SALASANA</property>
```

Tämän jälkeen voit käynnistää ohjelman ja luoda itsellesti testituotteita ja kategorioita. Tämän osuuden voit myös halutessasi hoitaa suoraan MySQL komennoilla.
