# API

The app provides several endpoints to add, search, filter and suggest products based on given fraze.

### Add
POST
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

### Add All
POST
```
localhost:8080/v1/product/addAll
```

Request body - array of products
``` json
[{
  "name": "Headphones",
  "description": "Noise-canceling wireless headphones",
  "category": "ELECTRONICS",
  "quantity": 15,
  "priceInCents": 7999
}]
```

### Find by id
GET
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
GET
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
GET
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
GET 
```
localhost:8080/v1/product/search?page=0&size=10&fraze=shirt
```

returns page of products that match given fraze
``` json
{
    "content": [
        {
            "id": "jQhfAI8BiWNNqzLKoi6n",
            "name": "T-shirt",
            "description": "Cotton T-shirt with logo",
            "category": "CLOTHES",
            "quantity": 50,
            "priceInCents": 1999
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "sorted": false,
            "empty": true,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "first": true,
    "size": 10,
    "number": 0,
    "sort": {
        "sorted": false,
        "empty": true,
        "unsorted": true
    },
```

[Go back to main file](../README.md)