# Elasticsearch demo
Example of how to use Elasticsearch and with Spring framework and Kotlin to add, search and suggest items.

The app uses UI to visualise elasticsearch functionality like suggesting product name based on user input

## Technological stack
* Kotlin
* Spring 3.2.5
* Elasticsearch
* elasticsearch-rest-high-level-client 7.17
* Kibana
* Docker
* Kotest

### How to run
You will need docker installed to run elasticsearch.
1. Run `docker-compose up -d` command
2. Run the spring app. You can use your IDE
3. Feed app with some test data. More about it in this [document]()
4. Open `http://localhost:8080/ui/index.html`

## API

The app provides several endpoints to add, search, filter and suggest products based on given fraze. 
You can find details in [API file](./docs/api.md).