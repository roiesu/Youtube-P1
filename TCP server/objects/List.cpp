#include "Node.cpp"
#include <iostream>

class List {
private:
    Node* head;  // Pointer to the first node in the list
    Node* tail;

public:
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
            tail->prev=head;
        } 
        else {
            tail->next = newNode;
            newNode->prev = tail;
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

    int deleteNode(std::string id){
        Node* toDelete = findNode(id);
        if(toDelete==nullptr){
            return 0;
        }
        else if(toDelete->prev==nullptr){
            this->head = toDelete->next;
            return 1;
        }
        Node* prev = toDelete->prev;
        prev->next=toDelete->next;
        return 1;
    }



    // Method to display the list
    void display() const {
        Node* current = head;
        while (current != nullptr) {
            std::cout << current->id << ", ";
            current = current->next;
        }
        std::cout << "null" << std::endl;           
    }    
};
