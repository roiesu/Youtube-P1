# Initialize the environment

First, open the github repository, download it and extract the files into an empty directory.
![](./images/Screenshot_1.png)
![](./images/Screenshot_2.png)

## Init mongodb and the NodeJS server and client

Now, open a powershell terminal, go to the server directory inside your root directory, and write "npm i".
This will allow you to run the NodeJS server.
![](./images/Screenshot_3.png)

Create a mongodb connection. For this demonstration, we will use a local mongodb server.
Inside that connection create a new database and name it as you like. For this demonstration we will use "Demo". Then, create 3 collections named "users","videos" and "comments".
![](./images/Screenshot_4.png)
![](./images/Screenshot_5.png)

Go back to the server's directory. In there, you can find a db directory. For each collection, there is a built data json file that you can manually seed into your database.
For each collection, press the button import data and choose the json file that corresponds to the collection you are in. For example, for the users collection we will import the
data from the BishBash.users.json file.
![](./images/Screenshot_6.png)

Now, create a file named .env inside your server directory. In it, enter the following text:
JWT_SECRET="(a random text sequence)"
MONGODB_CONNECTION_URL="(The connection string for your mongodb server)"
MONGODB_DATABASE="(Your database name)"
Notice that you replace localhost with 127.0.0.1. For our demonstration it will look like this:
![](./images/Screenshot_7.png)

## Init the TCP server

For this part, you will need to open a linux environment. For this demonstration we will use a VM with ubuntu installed in it.
In your linux environment, open the TCP server directory and open a terminal in it. In the terminal write "g++ -o server.out server.cpp" to compile the server file.
![](./images/Screenshot_8.png)

Now, in that terminal write hostname -I to get the linux environment's ip address.
Go back to the .env file in the server directory. Add the following lines to it:
TCP_PORT=5555
TCP_IP="(The linux environment's ip address)"
It should look like this:
![](./images/Screenshot_9.png)

## Init the Android application
