# authorizes-transactions
A application that authoreizes a transaction for a specific account. This application has been developed to
the Nubank software engenieer test. 

## Architecture
The code was organized into some layers: 'logic', 'handler', 'core', 'controller', and 'db' layer with in memory databases of application 

### logic
Have all the business rules to validate a new transaction

### handler
Get the json string thats has been write by the user, tries covert to availables types fromats and redirect to the controller.

### core
The main file, thats makes the interface with the users.

### controller 
Orchestrating calls between the layers of aplication.

### db layer
Layer thats contains a in memory database operations.

## Usage
### To run the application.

    $ lein run 
So you can  wirte some json input in the command line

### To run the test
    $ lein midje :autotest


## Examples
    $ lein run
    - input:
    { "account": { "activeCard": true, "availableLimit": 100 } }
    = output:
    { "account": { "activeCard": true, "availableLimit": 100 }, "violations": [] }
    - input:
    { "transaction": { "merchant": "Burger King", "amount": 20, "time": "2019-02-13T10:00:00.000Z" } }
    - output:
    { "account": { "activeCard": true, "availableLimit": 80 }, "violations": [] }
    - input: 
    { "transaction": { "merchant": "Habbib's", "amount": 90, "time": "2019-02-13T11:00:00.000Z" } }
    - output:
    { "account": { "activeCard": true, "availableLimit": 80 }, "violations": [ "insufficient-limit" ] }


### Missing aspects
The application can be improved by implementing the option to load input from external files making data entry easier