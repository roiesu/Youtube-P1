#include "NestedNode.cpp"
#include <iostream>

class NestedList {
private:
    NestedNode* head;  // Pointer to the first node in the list
    NestedNode* tail;

public:
    // Constructor to initialize an empty list
    NestedList() : head(nullptr),tail(nullptr) {}

    // Destructor to clean up the list
    ~NestedList() {
        NestedNode* current = head;
        while (current != nullptr) {
            NestedNode* next = current->next;
            delete current;
            current = next;
        }
    }

    // Method to add a node to the end of the list
    NestedNode* addNode(const std::string& id) {
        NestedNode* newNode = new NestedNode(id);
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


    NestedNode* findNode(std::string id){
        NestedNode* current = head;
        while (current != nullptr) {
            if(current->id.compare(id)==0){
                return current;
            }
            current = current->next;
        }
        return nullptr;
    }

    NestedNode* uniqueAdd(std::string id){
        NestedNode* newNode = this->findNode(id);
        if(newNode==nullptr){
            newNode = this->addNode(id);
        }
        return newNode;
    }

    int deleteNode(std::string id){
        NestedNode* toDelete = findNode(id);
        if(toDelete==nullptr){
            return 0;
        }
        else if(toDelete->prev==nullptr){
            this->head = toDelete->next;
            return 1;
        }
        NestedNode* prev = toDelete->prev;
        prev->next=toDelete->next;
        return 1;
    }

    // Method to display the list
    void display() const {
        NestedNode* current = head;
        while (current != nullptr) {
            current->display();
            current = current->next;
        }
        std::cout << "null" << std::endl;
    }
};