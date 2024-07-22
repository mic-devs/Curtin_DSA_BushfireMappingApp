/*
 * FILE: Heap.java
 * AUTHOR: Michael Chai Chon Yun
 * UNIT: COMP1002
 * PURPOSE: Store risk values of locations in a heap
 * REFERENCE: Own work, Practical 9
 * COMMENTS: Stores GraphNode objects using a graph
 * REQUIRES: HeapEntry.java
 * Last Mod: 27 May 2023
 */

public class Heap
{
    HeapEntry[] heap;
    int count = 1;

    public Heap(int size) 
    {
        heap = new HeapEntry[size+1];
    }

    public void add(Object value)
    {
        heap[count] = new HeapEntry(value);
        trickleUp(count);
        count++;
    }

    public HeapEntry remove() 
    {
        HeapEntry ans = heap[1];
        if (count != 1)
        {
            count--;
        }
        heap[1] = heap[count];
        heap[count] = null;
        trickleDown(1);
        
        return ans;
    }

    public void display()
    {
        System.out.println("Priority : Value");
        for (int i=1; i<count; i++)
        {
            int priority = heap[i].getPriority();
            Object value = heap[i].getValue();
            System.out.println("[ " +priority+ " : " + value + " ]");
        }
    }

    public void trickleUp(int entry)
    {
        while (entry != 1 && heap[entry].getPriority() > heap[parent(entry)].getPriority())
        {
            int newPos = parent(entry);
            swap(newPos, entry);
            entry = newPos;
        }
    }

    public void trickleDown(int index)
    {
        int left = leftChild(index);
        int right = left+1;
        boolean go = true;

        while (go && left < count)
        {
            go = false;
            int larger = left;
            if (right < count)
            {
                if (heap[left].getPriority() < heap[right].getPriority())
                {
                    larger = right;
                }
            }

            if (heap[larger].getPriority() > heap[index].getPriority())
            {
                swap(index, larger);
                go = true;
            }

            index = larger;
            left = leftChild(index);
            right = left + 1;
        }
    }

    private int parent(int pos) 
    {
        double ans = ( ((double) pos) - 1 ) / 2;
        return (int) Math.round(ans);
    }
 
    private int leftChild(int pos) 
    { 
        return (2 * pos); 
    }
 
    private void swap(int pos1, int pos2)
    {
        HeapEntry temp;
        temp = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = temp;
    }

}