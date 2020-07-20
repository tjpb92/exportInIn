# exportInIn
Programme Java permettant d'exporter dans un fichier Excel la configuration d’InIn

## Utilisation:
```
java exportInIn [-i ifile] [-o ofile] [-d] [-t] 
```
où :
* ```-i ifile``` fichier contenant la configuration complète d’InIn, par défaut *IAExport.csv*, (*paramètre optionnel*),
* ```-o ofile``` fichier Excel recevant les résultats, par défaut *IAExport.xlsx*, (*paramètre optionnel*),
* ```-d``` le programme s'exécute en mode débug, il est beaucoup plus verbeux. Désactivé par défaut (*paramètre optionnel*),
* ```-t``` le programme s'exécute en mode test. Désactivé par défaut (*paramètre optionnel*).

## Pré-requis :
- Java 6 ou supérieur.

## Références:

- [API Java Exel POI](http://poi.apache.org/download.html)
- [Tuto Java POI Excel](http://thierry-leriche-dessirier.developpez.com/tutoriels/java/charger-modifier-donnees-excel-2010-5-minutes/)
- [Tuto Java POI Excel](http://jmdoudoux.developpez.com/cours/developpons/java/chap-generation-documents.php)
- [Codes pour en-tête et pied de page](https://poi.apache.org/apidocs/dev/org/apache/poi/xssf/usermodel/extensions/XSSFHeaderFooter.html)
