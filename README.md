# phone-booking-system
Too lazy to introspect openapi? No worries, basic CURLs below; however, one needs to start the docker-compose file located in the root of the project

Get list of existing phones
```
curl -X GET -H 'Content-Type: application/json' -H 'Accept: application/json' http://localhost:8080/booking/phone/list
```

Book phone, in the URL id `13` is used, one shall be using the id existing in their DB
```
curl -v -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' http://localhost:8080/booking/phone/13/book\?bookedBy\=chuchma 
```

Unbook (return is a keyword in JVM world, thus, unbook is used) phone, in the URL id `12` is used, one shall be using the id existing in their DB
```
curl -v -X POST -H 'Content-Type: application/json' -H 'Accept: application/json' http://localhost:8080/booking/phone/12/unbook
```

Get phone representation by its manufacturer and model
```
curl -X GET -H 'Content-Type: application/json' -H 'Accept: application/json' http://localhost:8080/booking/phone\?manufacturer\=samsung\&model\=galaxy+s9               
```

