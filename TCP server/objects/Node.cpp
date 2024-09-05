
#include <string>

class Node {
public:
    std::string id;  // Field to store the ID
    Node* next;      // Pointer to the next node in the linked list

    // Constructor to initialize the node with an ID and optionally a next node
    Node(const std::string& idValue, Node* nextNode = nullptr) 
        : id(idValue), next(nextNode) {}

};

