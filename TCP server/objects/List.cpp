#include "Node.cpp"
#include <iostream>
using std::endl;
using std::string;

class List {
public:
    Node* head;  // Pointer to the first node in the list
    Node* tail;
    // Constructor to initialize an empty list
    List() : head(nullptr),tail(nullptr) {}

    // Destructor to clean up the list
    ~List() {
        Node* current = head;
        while (current != nullptr) {
            Node* next = current->next;
            delete current;
            current = next;
        }
    }

    // Method to add a node to the end of the list
    Node* addNode(const std::string& id) {
        Node* newNode = new Node(id);
        if (head == nullptr) {
            head = newNode;
            tail = newNode;
        } else if(head == tail){
            tail = newNode;
            head->next = tail;
        } 
        else {
            tail->next = newNode;
            tail=newNode;
        }
        return newNode;
    }


    Node* findNode(std::string id){
        Node* current = head;
        while (current != nullptr) {
            if(current->id.compare(id)==0){
                return current;
            }
            current = current->next;
        }
        return nullptr;
    }

    Node* uniqueAdd(std::string id){
        Node* newNode = this->findNode(id);
        if(newNode==nullptr){
            newNode = this->addNode(id);
        }
        return newNode;
    }

    // Method to display the list
    string display()  {
        Node* current = head;
        string toSend="";
        if(current==nullptr){
            return "";
        }
        while (current->next != nullptr) {
            toSend += current->id+ ", ";
            current = current->next;
        }
        toSend+=current->id;
        return toSend;
    }
};