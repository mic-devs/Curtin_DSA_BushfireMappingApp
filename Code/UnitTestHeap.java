public class UnitTestHeap 
{
    public static void main(String[] args) 
    {
        String locationFile = "location.txt"; //Assign the .txt file with location data here
        String uavDataFile = "UAVdata.txt"; //Assign the .txt file with UAV data here

        Graph m1 = new Graph(); //create an empty graph
        m1 = FileRead.inFileData(m1, locationFile); //load locations into the graph
        HashTable uavData = FileRead.hashData(m1, uavDataFile); //load uavData into hashtable
        boolean actual;

        Heap riskData = new Heap(m1.getVertexCount()); //create a heap for uavData
        for (int i=0; i<uavData.size; i++)
        {   //load uavData into heap
            if (uavData.hashArray[i].state == 1)
            {
                GraphNode node = (GraphNode) uavData.hashArray[i].getValue();
                riskData.add(node);
            }
        }

        // Below to test HeapSort ************************************************************

        //create a heapEntry array to store locations sorted via risk index
        HeapEntry[] sorted = new HeapEntry[m1.getVertexCount()];  
        //sort the locations via risk index
        sorted = HeapSort.heapSort(riskData.heap, m1.getVertexCount()); 

        System.out.println("\n[Location : Risk]");
        for (int i=0; i < sorted.length; i++)
        { //print out all sorted locations with risk index
            GraphNode node = (GraphNode) sorted[i].getValue();
            System.out.println(node.getLocation() + " : " + sorted[i].getPriority());
        }

        System.out.println("Data above should be sorted in descending order\n");
        
        //Below to test other methods *******************************************************

        HeapEntry testEntry = riskData.heap[1];
        String testEntryLoc = ((GraphNode) testEntry.getValue()).getLocation();
        actual = (testEntry.getPriority()==9 && testEntryLoc.equals("D"));
        System.out.println("add() & trickleUp(): " + actual);

        HeapEntry rem = riskData.remove();
        testEntryLoc = ((GraphNode) rem.getValue()).getLocation();
        actual = (rem.getPriority()==9 && testEntryLoc.equals("D"));
        System.out.println("remove(): " + actual);

        HeapEntry testEntry2 = riskData.heap[1];
        String testEntryLoc2 = ((GraphNode) testEntry2.getValue()).getLocation();
        actual = (testEntry2.getPriority()==8 && testEntryLoc2.equals("H"));
        System.out.println("trickleDown(): " + actual);
        
        
    }    
}
