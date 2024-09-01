#include <iostream>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
#include <thread>
#include "objects/NestedList.cpp"
using std::cout;
using std::endl;

void handleClient(int clientSocket, NestedList* users, NestedList* videos){
    char buffer[4096];
    int expected_data_len= sizeof(buffer);
    int read_bytes;
    int i=0;
    while((read_bytes=recv(clientSocket, buffer, expected_data_len,0))>0){
        users->add("asd");
        int sent_bytes= send(clientSocket, buffer, read_bytes,0);
        if (sent_bytes<0){
            perror("error sending to client");
        }
        users->display();
    }
    close(clientSocket);
}

int main() {
    const int server_port= 5555;
    int sock= socket(AF_INET, SOCK_STREAM,0);
    if (sock<0){
        perror("error creating socket");   
    }
    struct sockaddr_in sin;
    memset(&sin,0, sizeof(sin));
    sin.sin_family= AF_INET;
    sin.sin_addr.s_addr= INADDR_ANY;
    sin.sin_port= htons(server_port);
    if (bind(sock,(struct sockaddr*)&sin, sizeof(sin))<0){
        perror("error binding socket");
    }
    if (listen(sock,2)<0){
        perror("error listening to a socket");
    }
    NestedList users= NestedList();
    NestedList videos = NestedList();
    while (true) {
        //  cout << "Received message from client:3 " << endl;
        struct sockaddr_in client_sin;
        unsigned int addr_len = sizeof(client_sin);
        int client_sock = accept(sock, (struct sockaddr*)&client_sin, &addr_len);
        if (client_sock < 0) {
            perror("error acceptisng client");
            continue;
        }
        // Create a new thread to handle communication with the client
        std::thread client_thread(handleClient, client_sock , &users, &videos);
        client_thread.detach(); // Detach the thread to run independently
    }
    close(sock);
    return 0;
}