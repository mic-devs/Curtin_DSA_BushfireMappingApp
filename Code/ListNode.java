// FILE: ListNode.java
// AUTHOR: Michael Chai Chon Yun
// UNIT: COMP1002
// PURPOSE: List node for LinkedList.java
// REFERENCE: Own work, Practical 4
// COMMENTS: Includes distance field to hold distance from source location in an edge
// REQUIRES: n/a
// Last Mod: 14 May 2023

public class ListNode
{
    Object data;
    ListNode next;
    ListNode prev;
    Object distance; //distance from previous location in an edge

    public ListNode(Object a)
    {
        data = a;
        next = null;
        prev = null;
    }

    public Object getValue()
    {
        return data;
    }
        
    public ListNode getNext()
    {
        return next;
    }

    public void setNext(ListNode a)
    {
        next = a;
    }

    public ListNode getPrev()
    {
        return prev;
    }

    public void setPrev(ListNode a)
    {
        prev = a;
    }
        
    public void setDistance(Object a)
    {
        this.distance = a;
    }

    public Object getDistance()
    {
        return this.distance;
    }

}
