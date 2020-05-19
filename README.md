# boggle
boggle is one of the most popular word search games. here we support 4 * 4 and 5 * 5 board to play this game


## Running App
### 1. Making database ready:
https://postgresapp.com/downloads.html

1. initialize postgres server
2. close the current terminal and reopen it(only for first time)
3. login as postgres user
```
psql -U postgres
```

4.to list all the databases 
```
postgres=# \list
```
create user role for the project
  
```
 CREATE USER boggle WITH PASSWORD 'boggleabc';
```
 to switch to boggle user
```
psql -U boggle
```

### 2. Clone and Build the project:
must have IDE any ide having java support/or use terminal to build project

#### 2.1.For Backend (**java and kotlin needed):
go to boggle

##### Build 
```
gradlew build
```

##### Run
run main function present in main.kt


#### 2.2 For boggle-ui (**node and npm needed):
go to boggle/src/main/resources/boggle-ui/

###### Build
```
 npm install
 ```
 
######Run

```
 npm start
 ```
 it will open page in browser
