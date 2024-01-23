# Google Keep 

## Overview 

## Demo
https://github.com/tanmesh/google-keep/assets/20218124/4559fa8e-79dc-4db6-a370-1afb409ac559

## Project setup

```bash
# clone the project 
git clone https://github.com/tanmesh/google-keep.git

# setting up frontend
cd backend-google-keep

mvn clean install

java -jar ./target/backend-google-keep-1.0-SNAPSHOT.jar server ./src/main/resources/keep.config.local.yaml

# setting up frontend
cd frontend-google-keep

npm install .

npx expo start --clear

```


## REST APIs

Creating  a new note 
- POST
- Endpoint: `/note/add`
- Request Header:
  ```
    Content-Type application/json
    x-access-token 66cda64d-1daf-4d25-9b07-a9dd10dfef18
  ```
- Request Body:
  ```
    {
      "title": "travel destinations",
      "content": "{\"type\": \"numbered\", \"content\": [\"Paris, France\", \"Tokyo, Japan\", \"Rio de Janeiro, Brazil\"]}"
    }
  ```

Deleting existing note
- DELETE
- Endpoint: `/note/delete`
- Request Header:
  ```
    Content-Type application/json
    x-access-token 66cda64d-1daf-4d25-9b07-a9dd10dfef18
  ```
- Request Body:
  ```
  {
      "noteId": "658f2d62df2110ab46b90169"
  }
  ```

Editing existing note
- POST
- Endpoint: `/note/edit`
- Request Header:
  ```
    Content-Type application/json
    x-access-token 66cda64d-1daf-4d25-9b07-a9dd10dfef18
  ```
- Request Body:
  ```
  {
      "noteId": "6ab6891d-ae48-4a22-afa0-a976ce26a5b2",
      "title": "abcd",
      "content": "abcd abcd abcd abcd"
  }
  ```

Sign up a new user
- POST
- Endpoint: `/user/signup`
- Request Header:
  ```
    Content-Type application/json
  ```
- Request Body:
  ```
  {
    "emailId" : "tanmeshnm@gmail.com",
    "password": "admin"
  }
  ```

Login exiting user 
- POST
- Endpoint: `/user/login`
- Request Header:
  ```
    Content-Type application/json
  ```
- Request Body:
  ```
  {
    "emailId" : "tanmeshnm@gmail.com",
    "password": "admin"
  }
  ```

Listing down all existing notes
- GET
- Endpoint: `/user/getAllNotes`
- Request Header:
  ```
    Content-Type application/json
    x-access-token 66cda64d-1daf-4d25-9b07-a9dd10dfef18
  ```
- Request Body:
  ```
  {
    "emailId" : "admin"
  }
  ``` 

For more detailed -- look into https://documenter.getpostman.com/view/6625237/2s9YymFPny#intro 

## Future tasks
1. Adding a UI for the web.
2. Adding search functionality through the notes.
3. Adding ability to record and save voice notes.
4. Adding ability to order and pin notes in UI.


## Future tasks

