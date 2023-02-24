# Avatar Microservice

## Description
Micro that serves user avatar's based on the email's MD5 hash.  
It is able to provide:
- Different default avatars.
- Possibility of uploading 1 custom avatar per user.
- Get a new avatar from the defaults randomly for each call.
- Get the same default or custom avatar for a given user.

## Endpoints

> Swagger UI definitions at /swagger-ui/index.html

### `GET '/avatar/all'`
This endpoint will return all default avatars and, in case of have given a `hash`, also the current avatar assigned if any.

#### Query params

- hash: Email's MD5 hash. **This query param is optional**

#### Response
```
{
  "hash": "string",
  "default": [
    {
      "name": "string",
      "content": "string",
      "default": true
    }
  ],
  "usr": {
    "name": "string",
    "content": "string",
    "default": false
  }
}
```

### `GET '/avatar/{hash}'`
This endpoint will return the user's avatar stored if it's any or, in case of have the query param `random` set as `"true"`, random avatar from default will be assigned and returned.

#### Path params

- hash: Email's MD5 hash of the user.

#### Query params

- random: Email's MD5 hash. **This query params is optional. The default value is false**

#### Response
```
{
  "name": "string",
  "content": "string",
  "default": boolean
}
```
### `POST '/avatar/{hash}'`
This endpoint will assign the `name` provided by `Request body` as the avatar of the given user.

#### Path params

- hash: Email's MD5 hash of the user.

#### Request body
```
"String of the avatar's name"
```
#### Response
```
{
  "name": "string",
  "content": "string",
  "default": boolean
}
```

### `POST '/avatar/{hash}/upload'`
This endpoint will upload the provided image and assign it to the given user. **Only 1 per user can be uploaded** so, in case of already have one, the old one will be overridden.

#### Path params

- hash: Email's MD5 hash of the user.

#### Request body
```
{
  "name": "string",
  "content": "string"
}
```
#### Response
```
{
  "name": "string",
  "content": "string",
  "default": boolean
}
```

## Stack

The tech stack used is:

- Spring boot
- Webflux API Rest
- MariaDB with R2DB
- Google Firebase Storage
- Gradle
- Kotlin
- Gradle Kotlin DSL
