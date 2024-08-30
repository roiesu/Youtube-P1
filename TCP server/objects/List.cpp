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
    void add(const std::string& id) {
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
        }
    }

    // Method to display the list
    void display() const {
        Node* current = head;
        while (current != nullptr) {
            std::cout << current->id << " -> ";
            current = current->next;
        }
        std::cout << "null" << std::endl;
    }
};
