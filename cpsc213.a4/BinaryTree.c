#include <stdlib.h>
#include <stdio.h>

//
// A binary tree node with value and left and right children
struct Node {
  // TODO
    int  value;
    struct Node* left;
    struct Node* right;
};

//
// Insert node n into tree rooted at toNode
//
void insertNode (struct Node* toNode, struct Node* n) {
  // TODO
    
        if (n->value <= toNode->value) {
            if (toNode->left == NULL)
            {toNode->left = n;}
            else
            {insertNode (toNode->left, n);}
        } else {
            if (toNode->right == NULL)
            {toNode->right = n;}
            else
            {insertNode (toNode->right, n);}
        }
    
}

//
// Insert new node with specified value into tree rooted at toNode
//
void insert (struct Node* toNode, int value) {
  // TODO
        struct Node* p = malloc (sizeof(struct Node));
        p->value = value;
        p->left = NULL;
        p->right = NULL;
        insertNode (toNode, p);
    
}


//
// Print values of tree rooted at node in ascending order
//
void printInOrder (struct Node* node) {
  // TODO
    if (node->left != NULL){
     printInOrder(node->left);
    }
     printf("%d\n", node->value);
    if (node->right != NULL)
      printInOrder(node->right);
}

//
// Create root node, insert some values, and print tree in order
//
int main (int argc, char* argv[]) {
  // TODO
    
   
        struct Node* root = malloc (sizeof (struct Node));
        root->value = 100;
        root->left = NULL;
        root->right = NULL;
    
        insert (root,10);
        insert (root,120);
        insert (root,130);
        insert (root,90);
        insert (root,5);
        insert (root,95);
        insert (root,121);
        insert (root,131);
        insert (root,1);
        printInOrder(root);
}