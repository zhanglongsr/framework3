package com.zxl.algorithm;

import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 二叉树
 * 
 * @author zhangxl
 * @version 1.0.0
 * @param <E>
 */
public class BinaryTree<E extends Comparable<E>>
{
    private static final Logger logger = Logger.getLogger(BinaryTree.class);
    
    private Node<E> root;
    
    public void insert(E e)
    {
        if(e == null)
        {
            throw new IllegalArgumentException("BinaryTree's data cann't be null!");
        }
        
        /* 不存在根节点，首先创建根节点 */
        if(root == null)
        {
            root = new Node<E>(null, e);
        }
        else
        {
            Node<E> current = root;
            Node<E> parent;
            while(true)
            {
                parent = current;
                if(e.compareTo(parent.getData()) == 0)
                {
                    throw new IllegalArgumentException("Node[" + e.toString() + "] to build has already existed!existing obj [" + parent.getData().toString() + "]");
                }
                else if(e.compareTo(parent.getData()) < 0)
                {
                    current = current.leftChild;
                    if(current == null)
                    {
                        Node<E> newNode = new Node<E>(parent, e);
                        parent.leftChild = newNode;
                        return;
                    }
                }
                else
                {
                    current = current.rightChild;
                    if(current == null)
                    {
                        Node<E> newNode = new Node<E>(parent, e);
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }
    
    /**
     * 中序遍历(LDR)
     * 
     * @return
     */
    public List<E> inOrder()
    {
        List<E> list = new LinkedList<E>();
        inOrderTraverse(root.leftChild, list);
        list.add(root.data);
        inOrderTraverse(root.rightChild, list);
        
        return list;
    }
    
    private void inOrderTraverse(Node<E> node, List<E> list)
    {
        if(node != null)
        {
            inOrderTraverse(node.leftChild, list);
            list.add(node.data);
            inOrderTraverse(node.rightChild, list);
        }
    }
    
    /**
     * 前序遍历(DRL)
     * 
     * @return
     */
    public List<E> preOrder()
    {
        List<E> list = new LinkedList<E>();
        if(root == null)
        {
            return list;
        }
        
        list.add(root.data);
        preOrderTraverse(root.leftChild, list);
        preOrderTraverse(root.rightChild, list);
        
        return list;
        
    }
    
    private void preOrderTraverse(Node<E> node, List<E> list)
    {
        if(node != null)
        {
            list.add(node.getData());
            preOrderTraverse(node.leftChild, list);
            preOrderTraverse(node.rightChild, list);
        }
    }
    
    /**
     * 后序遍历(LRD)
     * 
     * @return
     */
    public List<E> postOrder()
    {
        List<E> list = new LinkedList<E>();
        if(root == null)
        {
            return list;
        }
        
        postOrderTraverse(root.leftChild, list);
        postOrderTraverse(root.rightChild, list);
        list.add(root.getData());
        
        return list;
    }
    
    private void postOrderTraverse(Node<E> node, List<E> list)
    {
        if(node != null)
        {
            postOrderTraverse(node.leftChild, list);
            postOrderTraverse(node.rightChild, list);
            list.add(node.getData());
        }
    }
    
    /**
     * 删除节点
     * 
     * @param e
     */
    public boolean delete(E e)
    {
        if(e == null)
        {
            return true;
        }
        
        Node<E> current = root;
        Node<E> parent = root;
        boolean isLeftChild = true; // 当前节点是否为父亲节点的左节点
        
        /* 首先定位节点 */
        while(!e.equals(current.data))
        {
            parent = current;
            if(e.compareTo(current.data) < 0)
            {
                isLeftChild = true;
                current = current.leftChild;
            }
            else
            {
                isLeftChild = false;
                current = current.rightChild;
            }
            
            if(current == null)
            {
                return false;
            }
        }
        
        /* 如果要删除的节点，是叶子节点 */
        if(current.leftChild == null && current.rightChild == null)
        {
            if(current.parent == null)
            {// 如果是根节点，则清空二叉树
                root = null;
            }
            else
            {// 否则，切断当前节点与父亲节点的联系
                if(isLeftChild)
                {
                    parent.leftChild = null;
                }
                else
                {
                    parent.rightChild = null;
                }
            }
        }
        else if(current.leftChild == null)
        {// 要删除的节点只有右节点的时候
            if(current.parent == null)
            {// 如果是根节点，则清空二叉树
                root = null;
            }
            else
            {// 否则，根据当前节点是否为父节点的左节点，把当前节点的子节点挂载到父亲的左、右上，并切断当前节点与子节点的关联
                if(isLeftChild)
                {
                    parent.leftChild = current.rightChild;
                }
                else
                {
                    parent.rightChild = current.rightChild;
                }
                current.rightChild.parent = parent;
            }
        }
        else if(current.rightChild == null)
        {
            if(current.parent == null)
            {
                root = null;
            }
            else
            {
                if(isLeftChild)
                {
                    parent.leftChild = current.leftChild;
                }
                else
                {
                    parent.rightChild = current.leftChild;
                }
                current.leftChild.parent = parent;
            }
        }
        else
        {
            Node<E> minInRightNodes = this.getMinInRightNodes(current);
            if(current.parent == null)
            {
                root = minInRightNodes;
            }
            else
            {
                if(isLeftChild)
                {
                    parent.leftChild = minInRightNodes;
                }
                else
                {
                    parent.rightChild = minInRightNodes;
                }
            }
        }
        
        return true;
    }
    
    /**
     * 从一个节点的右分支中，获得一个最小节点
     * 
     * @param node
     * @return
     */
    private Node<E> getMinInRightNodes(Node<E> node)
    {
        if(logger.isInfoEnabled())
        {
            
        }
        Node<E> minNode = node; // 最小节点
        Node<E> minNodeParent = node; // 最小节点的父节点
        Node<E> current = node.rightChild; // 当前节点
        while(current != null)
        {
            minNodeParent = minNode;
            minNode = current;
            current = current.leftChild;
        }
        
        /* 如果最小节点不是要删除节点的右节点，说明最小节点是要删除节点的右节点左分支下的节点 */
        if(!minNode.equals(node.rightChild))
        {
            minNodeParent.leftChild = minNode.rightChild; // 由于最小节点，需要迁走，那么需要把最小节点的右节点，作为最小节点父节点的左节点关联
            minNode.rightChild.parent = minNodeParent;
            
            minNode.rightChild = node.rightChild;
            minNode.leftChild = node.leftChild;
            minNode.parent = node.parent;
        }
        
        return minNode;
    }
    
    /**
     * 查找节点
     * 
     * @param e
     * @return
     */
    public BinaryTree<E>.Node<E> find(E e)
    {
        Node<E> current = root;
        while(e.equals(current.data))
        {
            if(e.compareTo(current.data) < 0)
            {
                current = current.leftChild;
            }
            else
            {
                current = current.rightChild;
            }
            
            if(current == null)
            {
                return null;
            }
        }
        return current;
    }
    
    /**
     * 二叉树Node节点
     * 
     * @author Administrator
     * 
     * @param <E>
     */
    class Node<E>
    {
        private Node<E> parent;
        
        private Node<E> leftChild;
        
        private Node<E> rightChild;
        
        private E data;
        
        public Node(Node<E> parent, E data)
        {
            this.parent = parent;
            this.data = data;
        }
        
        public Node<E> getParent()
        {
            return parent;
        }
        
        public void setParent(Node<E> parent)
        {
            this.parent = parent;
        }
        
        public Node<E> getLeftChild()
        {
            return leftChild;
        }
        
        public void setLeftChild(Node<E> leftChild)
        {
            this.leftChild = leftChild;
        }
        
        public Node<E> getRightChild()
        {
            return rightChild;
        }
        
        public void setRightChild(Node<E> rightChild)
        {
            this.rightChild = rightChild;
        }
        
        public E getData()
        {
            return data;
        }
        
        public void setData(E data)
        {
            this.data = data;
        }
        
    }
    
    public static void main(String... args)
    {
        BinaryTree<Integer> bt = new BinaryTree<Integer>();
        bt.insert(new Integer(66));
        bt.insert(Integer.valueOf(50));
        bt.insert(Integer.valueOf(6));
        bt.insert(Integer.valueOf(14));
        bt.insert(Integer.valueOf(88));
        bt.insert(Integer.valueOf(52));
        bt.insert(Integer.valueOf(108));
        bt.insert(Integer.valueOf(76));
        
        List<Integer> list = bt.inOrder();
        StringBuffer buffer = new StringBuffer();
        for(int i = 0; i < list.size(); i++)
        {
            if(buffer.length() > 0)
            {
                buffer.append(",");
            }
            buffer.append(list.get(i));
        }
        
        System.out.println("中序遍历：" + buffer.toString());
        
        list = bt.preOrder();
        buffer = new StringBuffer();
        for(int i = 0; i < list.size(); i++)
        {
            if(buffer.length() > 0)
            {
                buffer.append(",");
            }
            buffer.append(list.get(i));
        }
        
        System.out.println("前序遍历：" + buffer.toString());
        
        list = bt.postOrder();
        buffer = new StringBuffer();
        for(int i = 0; i < list.size(); i++)
        {
            if(buffer.length() > 0)
            {
                buffer.append(",");
            }
            buffer.append(list.get(i));
        }
        
        System.out.println("后序遍历：" + buffer.toString());
    }
    
}
