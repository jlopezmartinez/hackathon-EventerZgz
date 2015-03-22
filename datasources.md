http://www.zaragoza.es/api/recurso/cultura-ocio/evento-zaragoza.xml?srsname=wgs84&q=startDate%3Dle%3D2015-03-22T00%3A00%3A00Z%3BendDate%3Dle%3D2015-03-22T00%3A00%3A00Z%3B%28temas.id%3D%3D20%29

#SPARQL queries

## Categories

### Query
```sparql
PREFIX tema: <http://vocab.linkeddata.es/kos/cultura-ocio/tema/>
SELECT DISTINCT ?id ?tema WHERE {	
?uriCont a tema:evento-zaragoza;
   skos:notation ?id;
   skos:prefLabel ?tema.		
}
```
### URI
[Gimme the categories](http://datos.zaragoza.es/sparql?query=PREFIX+tema%3A+%3Chttp%3A%2F%2Fvocab.linkeddata.es%2Fkos%2Fcultura-ocio%2Ftema%2F%3E%0D%0ASELECT+DISTINCT+%3Fid+%3Ftema+WHERE+%7B%09%0D%0A%3FuriCont+a+tema%3Aevento-zaragoza%3B%0D%0A+++skos%3Anotation+%3Fid%3B%0D%0A+++skos%3AprefLabel+%3Ftema.%09%09%0D%0A%7D+ORDER+BY+%3Ftema&format=application%2Fsparql-results%2Bjson)

### Sample results

```json
{
   "head": {
      "link": [],
      "vars": [
         "id",
         "tema"
      ]
   },
   "results": {
      "distinct": false,
      "ordered": true,
      "bindings": [
         {
            "id": {
               "type": "literal",
               "value": "79"
            },
            "tema": {
               "type": "literal",
               "value": "Actos Religiosos"
            }
         },
         {
            "id": {
               "type": "literal",
               "value": "11"
            },
            "tema": {
               "type": "literal",
               "value": "Aire Libre y Excursiones"
            }
         }
      ]
   }
}
```
### Single Element parsing

```javascript
category = {
	id : json.results.bindings[0].id.value,
	description: json.results.bindings[0].tema.value
}
```
