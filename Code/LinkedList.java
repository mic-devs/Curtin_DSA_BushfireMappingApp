// FILE: LinkedList.java
// AUTHOR: Michael Chai Chon Yun
// UNIT: COMP1002
// PURPOSE: Linked list for Graph.java and GraphNode.java
// REFERENCE: Own work, Practical 4
// COMMENTS: n/a
// REQUIRES: ListNode.java
// Last Mod: 14 May 2023

class LinkedList
{
    ListNode head;
    ListNode tail;

    public boolean isEmpty()
    {
        boolean ans = false;
        if (head == null)
        {
            ans = true;
        }
        return ans;
    }

    public void insertFirst(Object a)
    {
        ListNode newNd = new ListNode(a);
        if (isEmpty())
        {
            head = newNd;
            tail = newNd;
        }
        else
        {
            newNd.setNext(head);
            head.setPrev(newNd);
            head = newNd;
        }
    }

    public void insertLast(Object a)
    {
        ListNode newNd = new ListNode(a);
        if (isEmpty())
        {
            head = newNd;
            tail = newNd;
        }
        else
        {
            newNd.setNext(null);
            tail.setNext(newNd);
            newNd.setPrev(tail);
            tail = newNd;
        }

    }

    public Object peekFirst()
    {
        Object ans;
        if (isEmpty())
        {   
            throw new RuntimeException("List is empty!");
        }
        else
        {
            ans = head.getValue();
        }
        return ans;
    }

    public Object peekLast()
    {
        Object ans;
        if (isEmpty())
        {   
            throw new RuntimeException("List is empty!");
        }
        else
        {
            ans = tail.getValue();
        }
        return ans;
    }

    public Object removeFirst()
    {
        Object ans;
        if (isEmpty())
        {
            throw new RuntimeException("List is empty!");
        }
        else
        {
            ans = head.getValue();
            head = head.getNext();
        }
        return ans;
    }
    
    public Object removeLast()
    {
        Object ans;
        if (isEmpty())
        {
            throw new RuntimeException("List is empty!");
        }
        else if (head.getNext() == null)
        {
            ans = head.getValue();
            head = null;
        }
        else 
        {
            ans = tail.getValue();
            tail = tail.getPrev();
            tail.setNext(null);
        }
        return ans;
    }
}