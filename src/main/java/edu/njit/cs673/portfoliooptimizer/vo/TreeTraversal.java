package edu.njit.cs673.portfoliooptimizer.vo;

import java.util.ArrayList;

import java.util.List;

public class TreeTraversal {

static List<Integer> orderedList = new ArrayList<Integer>();

public static void preOrderTraversal(TreeNode root){

if(root == null)

return;

orderedList.add(root.val);

preOrderTraversal(root.left);

preOrderTraversal(root.right);

}

public static void inOrderTraversal(TreeNode root){

if(root == null)

return;

inOrderTraversal(root.left);

orderedList.add(root.val);

inOrderTraversal(root.right);

}

public static void postOrderTraversal(TreeNode root){

if(root == null)

return;

postOrderTraversal(root.left);

postOrderTraversal(root.right);

orderedList.add(root.val);

}

public static void main(String[] args) {

TreeNode root = new TreeNode(2);

TreeNode left = new TreeNode(1);

TreeNode right = new TreeNode(3);

root.left = left;

root.right = right;

System.out.print("\nPreorder traversal of 2 1 3: ");

preOrderTraversal(root);

for(Integer i:orderedList)

System.out.print(i + " ");

orderedList.clear();

System.out.print("\nIneorder traversal of 2 1 3: ");

inOrderTraversal(root);

for(Integer i:orderedList)

System.out.print(i + " ");

orderedList.clear();

System.out.print("\nPostorder traversal of 2 1 3: ");

postOrderTraversal(root);

for(Integer i:orderedList)

System.out.print(i + " ");

}
}


class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}
/*
OUTPUT:
Preorder traversal of 2 1 3: 2 1 3 
Ineorder traversal of 2 1 3: 1 2 3 
Postorder traversal of 2 1 3: 1 3 2 
*/