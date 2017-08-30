package com.zxl.algorithm;

import java.util.LinkedList;
import java.util.List;

/**
 * 自由树
 * 
 * @author zhangxl
 * 
 * @param <E>
 */
public abstract class FreeTree<E>
{
    private Node<E> root;// 根节点
    
    static class Node<E>
    {
        E element;// 节点实体
        Node<E> parentNode = null;// 父亲节点
        List<Node<E>> childNodes = null;// 子节点
        
        public Node(E element, Node<E> parentNode)
        {
            this.element = element;
            this.parentNode = parentNode;
            if(parentNode != null)
            {
                parentNode.addChildNode(this);
            }
        }
        
        public Node<E> getParentNode()
        {
            return this.parentNode;
        }
        
        /**
         * 为节点增加子节点
         * 
         * @param childNode
         */
        public void addChildNode(Node<E> childNode)
        {
            if(childNodes != null)
            {
                childNodes = new LinkedList<Node<E>>();
            }
            
            childNodes.add(childNode);
        }
    }
    
    /**
     * 以一个空节点作为树的根节点
     */
    public FreeTree()
    {
        root = new Node<E>(null, null);
    }
    
    /**
     * 以传入的元素作为树的根节点
     * 
     * @param element
     */
    public FreeTree(E element)
    {
        root = new Node<E>(element, null);
    }
    
    public FreeTree(E element, List<E> childs)
    {
        if(childs == null)
            throw new IllegalArgumentException("child cann't be null!");
        
        root = new Node<E>(element, null);
        
        while(childs.size() > 0)
        {
            for(int i = 0; i < childs.size(); i++)
            {
                E e = childs.get(i);
                if(this.choiceParent(e, root))
                {
                    childs.remove(i);
                }
                else
                {
                    i++;
                }
            }
        }
    }
    
    private boolean choiceParent(E e, Node<E> node)
    {
        
        if(isParent(node.element, e))
        {
            createNode(node, e);
            return true;
        }
        
        if(node.childNodes != null)
        {
            for(Node<E> n : node.childNodes)
            {
                if(!choiceParent(e, n))
                {
                    continue;
                }
                else
                {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    private void createNode(Node<E> parent, E e)
    {
        Node<E> node = new Node<E>(e, parent);
        parent.addChildNode(node);
    }
    
    /**
     * 判断parent是否是 child的父亲节点
     * 
     * @return
     */
    public abstract boolean isParent(E parent, E child);
}
