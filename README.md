# Elasticsearch demo
Example of how to use Elasticsearch with Spring and Kotlin to add and search items.

work in progress...

## Technological stack
* Kotlin
* Spring 3.2.5
* Elasticsearch
* Kibana
* Docker
* Kotest


## API

### Add
```
localhost:8080/v1/product/add
```
Request body
``` json
{
  "name": "Headphones",
  "description": "Noise-canceling wireless headphones",
  "category": "ELECTRONICS",
  "quantity": 15,
  "priceInCents": 7999
}
```
returns string id of new product


### Find by id
```
localhost:8080/v1/product/find?id=hgwyAI8BYAC9b5e1NgGx
```

returns
``` json
{
    "id": "jAhfAI8BiWNNqzLKBS7a",
    "name": "Coffee Maker",
    "description": "Programmable coffee machine",
    "category": "APPLIANCES",
    "quantity": 3,
    "priceInCents": 49900
}
```

### Find all
```
localhost:8080/v1/product/findAll?fraze=shirt
```

returns list of products that match given fraze
``` json
[{
    "id": "jQhfAI8BiWNNqzLKoi6n",
    "name": "T-shirt",
    "description": "Cotton T-shirt with logo",
    "category": "CLOTHES",
    "quantity": 50,
    "priceInCents": 1999
}]
```

### Suggestions
```
localhost:8080/v1/product/suggestions?fraze=
```

returns list of products name (max 5)
``` json
[
    "Coffee Maker",
    "T-shirt",
    "Headphones"
]
```

### Search
```
localhost:8080/v1/product/findAll?fraze=shirt
```

Request body
``` json
{
    "page": 0,
    "size": 10,
    "fraze": "shirt"
    "priceFilter": {
       "priceRange": {
          "start": 0,
          "end": 10000
       },
       "descending": false
    },
    "category": "CLOTHES"
}
```

returns list of products that match given fraze
``` json
[{
    "id": "jQhfAI8BiWNNqzLKoi6n",
    "name": "T-shirt",
    "description": "Cotton T-shirt with logo",
    "category": "CLOTHES",
    "quantity": 50,
    "priceInCents": 1999
}]
```
