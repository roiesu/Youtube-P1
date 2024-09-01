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
    void add(const std::string& id) {
        NestedNode* newNestedNode = new NestedNode(id);
        if (head == nullptr) {
            head = newNestedNode;
            tail = newNestedNode;
        } else if(head == tail){
            tail = newNestedNode;
            head->next = tail;
        } 
        else {
            tail->next = newNestedNode;
        }
    }

    // Method to display the list
    void display() const {
        NestedNode* current = head;
        while (current != nullptr) {
            std::cout << current->id << " -> ";
            current = current->next;
        }
        std::cout << "null" << std::endl;
    }
};