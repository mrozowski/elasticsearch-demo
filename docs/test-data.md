# Feed App with test data

### Prepare data. 
I already prepared json file with list of products. Copy the json content from [this file](./products.json).

### Call endpoint
Use postman or curl to call this POST endpoint. Use above JSON as request body
```
localhost:8080/v1/product/addAll
```

**cURL**
```
curl -H "Content-Type: application/json" -X POST 'localhost:8080/v1/product/addAll' -d "@products.json" 
```