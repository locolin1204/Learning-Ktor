# Learning Ktor

This repository mainly focusing on understanding the basics of `routing` and `websocket` in `Ktor`.

GET `/` 
> print Hello World

GET `/{name}` 
> echo {name}

GET `/user/firstname` 
> implement nested route

GET `/user/lastname`
> implement nested route

GET `/list` 
> implement rout extension function

GET `/articles?sort={new/old}`
> implement type-safe routing [GET]

POST `/articles/new`
> implement type-safe routing [POST]


WS `/echo`
> echo received message

WS `/chat`
> create pool of users in the chat and echo message to other users

WS `/customer`
> implement receive and send serialized objects

