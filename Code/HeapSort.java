/*
 * FILE: HeapSort.java
   AUTHOR: Michael Chai Chon Yun
   UNIT: COMP1002
   PURPOSE: Receive a HeapEntry array and return it sorted in descending order
   REFERENCE: Own work, Practical 9
   REQUIRES: HeapEntry.java, Heap.java
   Last Mod: 27 May 2023
 */

public class HeapSort 
{
    private static Heap heapify(HeapEntry[] input)
    {
        Heap heapTab = new Heap(input.length+1);
        for (int i= 1; i<input.length; i++)
        { //add all the entries from the input array into a heap
            if (input[i] != null)
            {
                heapTab.add(input[i].getValue());
            }
        }

        return heapTab;
    }

    public static HeapEntry[] heapSort(HeapEntry[] input, int len)
    {
        HeapEntry[] sorted = new HeapEntry[len];
        Heap h1 = heapify(input);

        for (int i=1; i<len+1; i++)
        { //transfer all the entries from the heap into the sorted array
          sorted[i-1] = h1.remove();
        }

        return sorted;
    }    
}
