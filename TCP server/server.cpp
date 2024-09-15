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
using std::string;
using std::thread;

std::pair<string, string> extractIds(string buffer,int read_bytes){
    string delimiter = " ";
    int index= buffer.find(delimiter);
    string userId = buffer.substr(0, index);
    string videoId = buffer.substr(index+1,read_bytes);
    return std::make_pair(userId,videoId);
}
string getRecommendations(NestedList* videos, string videoId){
    NestedNode* videoNode = videos->findNode(videoId);
    if(videoNode!=nullptr){
        return videoNode->inner->display();
    }
    return "empty";
}
void updateReccomendations(NestedList* users, NestedList* videos,string userId,string videoId){
    NestedNode* userNode = users->uniqueAdd(userId);
    NestedNode* videoNode = videos->uniqueAdd(videoId);
    if(userNode->inner->findNode(videoId)){
        // If the video is already inside the user's watching list, no need to update it's friend list.
        return;
    }

    Node* watchedVideo = userNode->inner->head;
    while(watchedVideo!=nullptr){
        // Add every video from the user's watch list into the video's firends list
        videoNode->inner->uniqueAdd(watchedVideo->id);
        NestedNode* watchVideoNested = videos->findNode(watchedVideo->id);
        // Add the new video to any other video in the user's watch list
        watchVideoNested->inner->uniqueAdd(videoId);
        watchedVideo= watchedVideo->next;
    }
    // Adding the new video to the user's watch list
    userNode->inner->addNode(videoId);
}

void handleClient(int clientSocket, NestedList* users, NestedList* videos){
    char buffer[60];
    int expected_data_len= sizeof(buffer);
    int read_bytes;
    while((read_bytes=recv(clientSocket, buffer, expected_data_len,0))>0){
        std::pair<string,string> idPair= extractIds(buffer,read_bytes);    
        string recVideos = getRecommendations(videos,idPair.second);
        int sent_bytes= send(clientSocket, recVideos.c_str(), recVideos.size(),0);
        if (sent_bytes<0){
            perror("error sending to client");
        }
        if(idPair.first.compare("undefined")!=0 && idPair.first.compare("null")){
            updateReccomendations(users,videos,idPair.first,idPair.second);
        }
        memset(buffer,0, read_bytes);
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
    if (listen(sock,5)<0){
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
        thread client_thread(handleClient, client_sock , &users, &videos);
        client_thread.detach(); // Detach the thread to run independently
    }
    close(sock);
    return 0;
}