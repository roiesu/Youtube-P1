#include <iostream>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <string.h>
using std::cout;
using std::endl;
int main() {
    const int server_port= 5555;
    int sock= socket(AF_INET, SOCK_STREAM,0);
    if (sock<0){
        perror("error creating socket");   
    }
    struct sockaddr_in sin;memset(&sin,0, sizeof(sin));
    sin.sin_family= AF_INET;
    sin.sin_addr.s_addr= INADDR_ANY;
    sin.sin_port= htons(server_port);
    if (bind(sock,(struct sockaddr*)&sin, sizeof(sin))<0){
        perror("error binding socket");
    }
    if (listen(sock,2)<0){
        perror("error listening to a socket");
    }
    struct sockaddr_in client_sin;
    unsigned int addr_len= sizeof(client_sin);
    int client_sock= accept(sock,(struct sockaddr*)&client_sin,&addr_len);
    if (client_sock<0){
        perror("error accepting client");
    }
    char buffer[4096];
    int expected_data_len= sizeof(buffer);
    while (true)
    {    
        int read_bytes= recv(client_sock, buffer, expected_data_len,0);
        if (read_bytes ==0){
            cout<<"closed"<<endl;
            break;
        // connection is closed 
        }
        else if (read_bytes<0){
            cout<<"error"<<endl;
            break;
        }
        else{
            cout<<buffer<<endl;
        }
        int sent_bytes= send(client_sock, buffer, read_bytes,0);
        if (sent_bytes<0){
            perror("error sending to client");
        }
    }
    close(sock);
    return 0;
}