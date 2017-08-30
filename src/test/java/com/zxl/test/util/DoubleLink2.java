package com.zxl.test.util;

/**
 * 双向链表,参考LinkedList
 * 
 * @author Administrator
 * 
 * @param <E>
 */
public class DoubleLink2<E>
{
    private transient Entry<E> header = new Entry<E>(null, null, null);
    
    public DoubleLink2()
    {
        header.next = header.previous = header;
    }
    
    public boolean add(E e)
    {
        addBefore(e, header);
        return true;
    }
    
    public boolean addFirst(E e)
    {
        addBefore(e, header.next);
        return true;
    }
    
    public String toString()
    {
        
        StringBuffer buffer = new StringBuffer();
        Entry<E> node = header.previous;
        while(node != header)
        {
            buffer.append(node.element.toString());
            node = node.previous;
        }
        return buffer.toString();
    }
    
    private Entry<E> addBefore(E e, Entry<E> entry)
    {
        Entry<E> newEntry = new Entry<E>(e, entry, entry.previous);
        newEntry.previous.next = newEntry;
        newEntry.next.previous = newEntry;
        // size++;
        // modCount++;
        return newEntry;
    }
    
    private static class Entry<E>
    {
        E element;
        
        Entry<E> next;
        
        Entry<E> previous;
        
        Entry(E element, Entry<E> next, Entry<E> previous)
        {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
        
    }
    
    public static void main(String... strings)
    {
        DoubleLink2<String> dl2 = new DoubleLink2<String>();
        dl2.addFirst("4");
        dl2.addFirst("3");
        dl2.addFirst("2");
        
        System.out.println(dl2.toString());
        //
        // LinkedList<String> ll = new LinkedList<String>();
        // ll.addFirst("1");
        // ll.addFirst("3");
    }
}
