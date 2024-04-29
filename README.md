# Elasticsearch demo
Example of how to use Elasticsearch and with Spring framework and Kotlin to add, search and suggest items.

The app uses UI to visualize elasticsearch functionality like suggesting product names based on user input

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

## UI
UI is made with JS, CSS and HTML. It has a simple search bar, product list and suggestions

[ui-preview.webm](https://github.com/mrozowski/elasticsearch-demo/assets/67066372/f0d8bbd7-ddd4-461b-8ec5-a47290f1d21e)


## API

The app provides several endpoints to add, search, filter and suggest products based on given fraze. 
You can find details in [API file](./docs/api.md).
