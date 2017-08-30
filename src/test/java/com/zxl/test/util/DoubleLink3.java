package com.zxl.test.util;

public class DoubleLink3<E>
{
    private Entity<E> header = new Entity<E>(null, null, null);
    
    public DoubleLink3()
    {
        header.next = header.previous = header;
    }
    
    public boolean add(E element)
    {
        addBefore(element, header);
        return true;
    }
    
    public boolean addFirst(E element)
    {
        addBefore(element, header.next);
        return true;
    }
    
    private void addBefore(E element, Entity<E> entity)
    {
        Entity<E> newEntry = new Entity<E>(element, entity, entity.previous);
        newEntry.previous.next = newEntry;
        newEntry.next.previous = newEntry;
    }
    
    private static class Entity<E>
    {
        E element;
        
        Entity<E> next;
        
        Entity<E> previous;
        
        public Entity(E element, Entity<E> next, Entity<E> previous)
        {
            this.element = element;
            this.next = next;
            this.previous = previous;
        }
    }
}
