#include <string>
#include "List.cpp"
using std::endl;
using std::string;

class NestedNode{
    public:
    std::string id;  // Field to store the ID
    NestedNode* next;      // Pointer to the next node in the linked list
    NestedNode* prev;
    List* inner;

    // Constructor to initialize the node with an ID and optionally a next node
    NestedNode(const std::string& idValue, NestedNode* nextNode = nullptr,NestedNode* prev=nullptr, List* inner = new List()) 
        : id(idValue), next(nextNode), prev(prev), inner(inner){}
    
    string display(){
        return this->id + ": "+ this->inner->display();
    }
};
