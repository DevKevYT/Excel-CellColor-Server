# Excel-CellColor-Server 
A Java Server which returns all colored cells to an uploaded Excel Sheet.
Ein Java Server der alle 

## Projects using this project:
<a>https://github.com/floodoo/untis_phasierung/</a>

## Allgemeine Informationen

Der Server besteht haupts�chlich aus zwei .jar Dateien. Die Installation erstellt 4-5 Dateien.

- `instancelog.log` (Log Datei, automatisch generiert)
- `main.conf` (Konfigurationsdatei, automatisch generiert, ver�nderbar)
- `Main.jar` (Einstiegspunkt, muss manuell ausgef�hrt werden)
- `server-hook.jar` (Automatischer download aus GitHub releases)
- `backup.tmp` (Backupdatei bei einem `server-hook.jar` update)

Die `Main.jar` Datei ist das Hauptprogramm welches manuell gepflegt werden muss.<br>
Es stellt grundlegende Administrationsbefehle zur Verf�gung und k�mmert sich darum,
dass die `server-hook.jar` immer auf dem neuesten Stand ist.<br>
Das Hauptprogramm selber sollte selten Updates ben�tigen. (-> Sektion [Updating]())

Die `server-hook.jar` k�mmert sich um alles um den Server am laufen zu halten.<br>
Sie ist nicht selbst ausf�hrbar.<br>
Sie l�sst sich mittels der Main.jar konfigurieren, ausf�hren und updaten.

## Installation

Jeder GitHub release beinhaltet zwei Bin�rdateien:
- `Main.jar` (Das Hauptprogramm wie oben beschrieben)
- `server-hook.jar` (Die Server Datei)

Um den Server zum laufen zu bringen reicht es die `Main.jar` herunterzuladen und auszuf�hren.
Konfigurationsdateien werden automatisch generiert.
Beim ersten start wird eine Fehlermeldung kommen:
```
.../server-hook.jar nicht gefunden oder ist ein Ordner. �berpr�fe main.conf oder f�hre den Befehl 'update' aus!
```
Hier reicht es den `update` Befehl auszuf�hren.

Um die `Main.jar` auszuf�hren folgenden Befehl im Ordner eingeben wo die Main.jar gedownloaded wurde:

```
java -Xmx1G -jar Main.jar
```
## Administrator Konsole
Die administrator Konsole erm�glicht es dir Befehle der `Main.jar` oder auch des Hooks auszuf�hren.<br>
Du erkennst sie an zwei `>>` ein der Konsole.
<br>
<br>
Um eine Liste von Befehlen zu erhalten, gib `help` ein.
```
>>help
               help  Gibt diese Seite aus.
     uninstall-list  Listet alle Installierten Dateien auf.
   uninstall-delete  L�scht alle Programm Dateien inklusive des aktuellen Hooks.
        hook-reload  Startet den Server neu
           hook-info  Gibt Informationen �ber die aktuelle Server Version aus
             update  Pr�ft auf eine neue Server Version und updated den Server.
  read-log <string>  Usage: 'read-log help' for more info
               exit     Stoppt den Server
```

## Server Einstellungen
Diese Sektion beschreibt, welche M�glichkeiten es gibt das Serververhalten einzustellen.

### Konfigurationsdatei main.conf
Folgende Konfigurationen werden automatisch bei der ersten ausf�hrung erstellt:

```
hook-args={
	max-clients=10.0;
	client-timeout=10000.0;
	port=6969;
}
hook-file=hook-files/server-hook.jar;
```

- `hook-file`: Relativer oder absoluter download Pfad f�r die `server-hook.jar`.
- `hook-args`: Argumente die dem `server-hook.jar` beim laden �bergeben werden.
- `max-clients`: Maximale anzahl an Clients, die gleichzeitig mit dem Server verbunden sein d�rfen.
- `client-timeout`: Maximale dauer ein Client darf mit dem Server verbunden sein, bevor die Verbindung serverwseitig getrennt wird.
- `port`: Der Port auf dem der Server laufen soll (Optional, standart: 6969)

Um �nderungen an der Konfiguration wirksam zu machen gibt es mehrere M�glichkeiten:
- Befehl `hook-reload` eingeben.
- Befehl `exit` eingeben und `Main.jar` neu starten

### Die server-hook.jar selber
Da die Hook Datei nur eine einfache .jar Datei ist die mit einem ClassLoader geladen wird, kannst du ganz einfach selber eine erstellen!

Vorraussetzung ist eine .jar Datei die eine Klasse beinhaltet mit dem Namen `Application`
die von der abstrakten Klasse `com.devkev.main.Hook` erbt.
Jetzt nur noch `server-hook.jar` im angegebenen Pfad in main.conf ersetzen oder einen eigenen erstellen und deine eigenen `hook-args` erstellen.

## Updates
Aktuell ist es nur m�glich mittels dem Befehl `update` manuell nach neuen GitHub releases zu checken.
Automatische updates werden bald kommen.
Diese Updates werden aber nur die Hook Datei beeinflussen. Um die `Main.jar` zu updaten musst du 
selber in die Github releases schauen.
Falls eine neue Version f�r `Main.jar` verf�gbar ist, wird es in der Release Beschreibung stehen.

## Server Funktionen

Es ist m�glich Befehle an den Server zu senden. Das sind einfach Strings die �ber einen Socket gesendet werden.
Clients sind auf einen Befehl pro Verbindung beschr�nkt. �blicherweise w�re das ein Befehl mit ein paar Argumenten.
> `do-something "some-argument"`

Bis auf Befehle sollte jeglicher Datentransfer zwischen Server und Client im JSON Format erfolgen:
```json
{
  "message": { 
    "some": "data"
  }
  "error": "ErrorMessage"
}
```  
Bitte beachte, dass immer nur "message" oder "error" im der JSON existieren. Niemals beide gleichzeitig.
Hier ist eine Liste mit Befehlen die mit der aktuellen `server-hook.jar` m�glich sind:

#### convertxssf
* Dieser Befehl erwartet keine Argumente. Wenn eine best�tigung "ready" vom Server gesendet wurde
erwartet der Server eine Excel Datei. Wenn diese Verarbeitet wurde gibt der Server ein Array mit Zellenfarben zur�ck.

# Excel Server dependencies:

- <a>https://github.com/DevKevYT/devscript</a> version: '1.9.4'<br>
- <a>https://mvnrepository.com/artifact/org.apache.poi/poi</a> version: '5.1.0'<br>
- <a>https://mvnrepository.com/artifact/org.apache.poi/poi-ooxml</a> version: '5.1.0'<br>
- <a>https://mvnrepository.com/artifact/org.apache.xmlbeans/xmlbeans</a> version: '2.3.0'<br>
- <a>https://mvnrepository.com/artifact/dom4j/dom4j</a> version: '1.6.1'<br>
- <a>https://mvnrepository.com/artifact/org.apache.commons/commons-collections4</a> version: '4.3'<br>
- <a>https://mvnrepository.com/artifact/org.apache.commons/commons-compress</a> version: '1.18'<br>
- <a>https://mvnrepository.com/artifact/org.apache.poi/ooxml-schemas</a> version: '4.1'

