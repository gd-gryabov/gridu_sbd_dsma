# The capstone for course "Distributed systems and microservices architecture"


## Task

    Download sample product data set https://www.kaggle.com/PromptCloudHQ/all-jc-penny-products
    1) Create the following Spring Boot applications:
        a) Catalog application: holds online store product data in-memory from the product data set above.
           The application exposes REST API for retrieving products by ‘uniq_id’ and list of products by ‘sku’.
           
        b) Inventory application: holds online store product availability data. 
           Generate random availability status for each product from the product data set above and keep it in an in-memory data structure. 
           The application exposes REST API for retrieving product availability by a list of ‘uniq_id’.
           
        c) Product application: returns product data to end-clients. 
           The application exposes REST API for retrieving available products data by ‘uniq_id’ and by ‘sku’ (multiple products are returned). 
           The REST service makes a REST call to catalog application to get product data by ‘uniq_id’ or by ‘sku’, 
           and make a call to the inventory application to get product availability and filter out the only available product before returning.

    2) Use Netflix Eureka for registering applications and discovering them for inter-component REST calls.
    3) Use Netflix Hystrix for protecting inter-component REST calls from the product application. The fallback behavior is supposed to 
           result in ‘503 service unavailable’ in case of unavailability of any dependent services. Use synthetic delays (sleep time) in 
           the inventory and catalog applications to increase response latency. Play with ‘execution.isolation.thread.timeoutInMilliseconds’, 
           ‘coreSize’, ‘circuitBreaker.requestVolumeThreshold’ and ‘circuitBreaker.sleepWindowInMilliseconds’ to simulate circuit breaker behavior.
    4) Use request tracing through the inter-component REST calls and the Zipkin server for monitoring request flow and latency.


## Subprojects
- **rawdata** - raw data and data.sql generator for catalog and inventory services
- **service-registry** - netflix eureka service registry
- **config-server**    - spring cloud configuration server
- **catalog-service** - catalog service
- **inventory-service** - inventory service
- **product-service** - product service
- **hystrix-dashboard** - hystrix dashboard
- **zipkin-server** - zipkin server

## How to build and run

### Local

**Build projects** `./mvnw clean package -DskipTests=true`\
**Run each project**  `./mvnw spring-boot:run`
**Run order**
1) config-server
2) service-registry
3) services
4) dashboards 


### Docker

**Rebuild images** `docker-compose build --no-cache --parallel`\
**Run** `docker-compose up -d <service>`


### Demo

Spin up services and dashboards locally, wait for services registered in eureka.

- **service-registry** - http://127.0.0.1:8761
- **config-server**    - http://127.0.0.1:8888
- **catalog-service** - http://127.0.0.1:8181
- **inventory-service** - http://127.0.0.1:8282
- **product-service** - http://127.0.0.1:8383
- **hystrix-dashboard** - http://127.0.0.1:8788/hystrix
- **zipkin-server** - http://127.0.0.1:9411/zipkin


**inventory**\
GET `/inventory/{idList}`

- http://127.0.0.1:8282/inventory/5d47b9864868033c585c198da6288a9e,e846c1991464ad83708c2ce6198cce9d


**catalog**\
GET `/catalog/id/{id}`

- http://127.0.0.1:8181/catalog/id/419f44d1d8ed5ca99af735eaa96d9e18

GET `/catalog/sku/{sku}`

- http://127.0.0.1:8181/catalog/sku/pp5003800572

**product**

GET `/catalog/id/{id}`

- http://127.0.0.1:8383/product/id/5d47b9864868033c585c198da6288a9e

GET `/catalog/sku/{sku}`

- http://127.0.0.1:8383/product/sku/pp5003800572

**hystrix**
1) open dashboard - http://127.0.0.1:8788/hystrix
2) set hhttp://127.0.0.1:8383/actuator/hystrix.stream
3) run monitoring
4) fire product request several time, for instance:
   http://127.0.0.1:8383/product/id/5d47b9864868033c585c198da6288a9e
   
InventoryServiceClient's getInventoryByProductIds function has random delay.\
If delay more than 2000 millis the fallback function will run instead and you\
will see in log the message `Returning default map for ids`  
   

**zipkin**
1) open dashboard - http://127.0.0.1:9411/zipkin
2) fire product request, for instance:
      http://127.0.0.1:8383/product/id/5d47b9864868033c585c198da6288a9e
