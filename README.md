Task

    Download sample product data set https://www.kaggle.com/PromptCloudHQ/all-jc-penny-products
    1) Create the following Spring Boot applications:
        a) Catalog application: holds online store product data in-memory from the product data set above. The application exposes REST API for retrieving products by ‘uniq_id’ and list of products by ‘sku’.
        b) Inventory application: holds online store product availability data. Generate random availability status for each product from the product data set above and keep it in an in-memory data structure. The application exposes REST API for retrieving product availability by a list of ‘uniq_id’.
        c) Product application: returns product data to end-clients. The application exposes REST API for retrieving available products data by ‘uniq_id’ and by ‘sku’ (multiple products are returned). The REST service makes REST call to catalog application to get product data by ‘uniq_id’ or by ‘sku’, and make a call to the inventory application to get product availability and filter out only available product before returning.

    2) Use Netflix Eureka for registering applications and discovering them for inter-component REST calls.
    3) Use Netflix Hystrix for protecting inter-component REST calls from the product application. The fallback behavior is supposed to result in ‘503 service unavailable’ in case of unavailability of any dependant services. Use synthetic delays (sleep time) in the inventory and catalog applications to increase response latency. Play with ‘execution.isolation.thread.timeoutInMilliseconds’, ‘coreSize’, ‘circuitBreaker.requestVolumeThreshold’ and ‘circuitBreaker.sleepWindowInMilliseconds’ to simulate circuit breaker behavior.
    4) Use request tracing through the inter-component REST calls and the Zipkin server for monitoring request flow and latency.
