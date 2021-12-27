# Proof IT - tech task

## Technologies
- Java 17 (I simply downloaded latest version)
- Gradle 7.3.2
- Spring Boot framework 


## Task description

Bus charter company wants to provide new service for travel agencies – draft ticket price.
To receive draft ticket price, following data must be provided:
- List of passengers
- List of luggage items of each passenger

Draft ticket price is calculated as following:
1. Base price for an adult is provided by already existing service returning number from
database based on given route (bus terminal name).
2. Child passengers receive 50% discount.
3. Luggage price is 30% of base price.
4. Tax rates are provided by already existing service, which provides list of percentage
rates on given day of purchase.
The result of calculation should contain both total price and prices for each individual item.

## Implementation description

API implemented as web service

### 1. GET VAT API
> Tax rates are provided by already existing service, which provides list of percentage rates on given day of purchase.

In current implementation VAT value is simply hardcoded as final static variable VAT
```java
private static final int VAT = 21;
```

API:
- route `/vat`
- method `GET`
- return current VAT value used for ticket price calculation

Example:
```
$ curl http://localhost:8080/vat

VAT: 21
```


### 2. GET BASE PRICE API
> Base price for an adult is provided by already existing service returning number from database based on given route (bus terminal name).

There is no database used in current implementation, so base price value is also simply hardcoded as final static variable BASE_PRICE
```java
private static final int BASE_PRICE = 10;
```

API:
- route `/base_price`
- method `GET`
- parameters:
    * `terminal` - name of the terminal (not required, default name of terminal is Vilnius) 
- return current base price value used for ticket price calculation 

Base price for every terminal will be the same cause it is hardcoded... Yeah, yeah, I was a little bit lazy during Christmas :)

Example:
```
$ curl http://localhost:8080/base_price
Base price to terminal Vilnius: 10 EUR

$ curl http://localhost:8080/base_price?terminal=Tallinn
Base price to terminal Tallinn: 10 EUR
```

### 3. POST TICKET PRICE API
> To receive draft ticket price, following data must be provided: list of passengers, list of luggage items of each passenger

There is no database used in current implementation, so base price value is also simply hardcoded as final static variable BASE_PRICE
```java
private static final int BASE_PRICE = 10;
```

API:
- route `/price`
- method `POST`
- data sent to request is JSON array of objects. Data provided to JSON object:
    * `child` boolean, indicates type of ticket (passanger is child or not), if not provided default is false
    * `bags` integer, indicates count of luggage items of passanger, if not provided default is 0
- return JSON object with total price and prices for each individual item (`ticketPrice` - passanger's ticket price, `luggagePrice` - passanger's luggage ticket price).
```json
{
    "totalPrice": "29.04",
    "tickets": [
        {
            "child": false,
            "bags": 2,
            "ticketPrice": "12.10",
            "luggagePrice": "7.26"
        },
        {
            "child": true,
            "bags": 1,
            "ticketPrice": "6.05",
            "luggagePrice": "3.63"
        }
    ]
}
```


Example:
```
$ curl -d '[]' -H "Content-Type: application/json" -X POST http://localhost:8080/price
{"totalPrice":"0.00","tickets":[]}


$ curl -d '[{}]' -H "Content-Type: application/json" -X POST http://localhost:8080/price
{"totalPrice":"12.10","tickets":[{"child":false,"bags":0,"ticketPrice":"12.10","luggagePrice":"0.00"}]}


$ curl -d '[{"bags": 1}]' -H "Content-Type: application/json" -X POST http://localhost:8080/price
{"totalPrice":"15.73","tickets":[{"child":false,"bags":1,"ticketPrice":"12.10","luggagePrice":"3.63"}]}

$ curl -d '[{"child": false, "bags": 2}, {"child": true, "bags": 1}]' -H "Content-Type: application/json" -X POST http://localhost:8080/price
{"totalPrice":"29.04","tickets":[{"child":false,"bags":2,"ticketPrice":"12.10","luggagePrice":"7.26"},{"child":true,"bags":1,"ticketPrice":"6.05","luggagePrice":"3.63"}]}

```

## Unit tests

There is written couple tests, you can check them :)

