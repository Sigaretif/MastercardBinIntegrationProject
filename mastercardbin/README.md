## Mastercard Bin App

This QUARKUS application processes and enriches transaction data using the Mastercard BIN Lookup API, stores it for
analysis, and provides REST endpoints with built-in alerting, caching, and filtering capabilities.

### App features:

- An endpoint for storing a new transaction enriched with metadata retrieved from the Mastercard BIN Lookup API.

- An endpoint that returns aggregated analytical insights about transactions, with support for filtering by specific
  criteria.

- Caching of Mastercard API responses using Caffeine to improve performance and reduce external calls.

- Propagation of a request ID header to facilitate log traceability across the system.

- Security implemented using JWT authentication to protect all endpoints.

- An alerting system that notifies when a specific BIN number is accessed unusually frequently within a short period.

### Runing the app:

To correctly run the application you need to create mastercard account and bin lookup api project. Follow the
instructions [HERE](https://developer.mastercard.com/bin-lookup/documentation/quick-start-guide/)

After creating the project, you will need to set up the environment variables in the `.env.sample` file.
Fill in required mastercard variables. Additionally provide database connection credentials. Currently application uses
postgresql database, so if you don't want to change properties, you need to provide postgresql database connection
credentials.
After that just change name of the file to `.env` and run the application.

#### Additional configuation

The application uses an alerting system to detect unusually frequent access to a specific BIN number. The alerting
feature operates in two modes. The first mode logs a warning message when a defined threshold is exceeded. The second
mode completely blocks further requests once the threshold is reached. To configure this feature, as well as other
application settings, you need to adjust the corresponding values in the `application.properties`.

### Endpoints:

The application uses test data from the Mastercard BIN Lookup API. In order to create a new transaction, you must use
BIN numbers available at the following [LINK](https://developer.mastercard.com/bin-lookup/documentation/testing/)