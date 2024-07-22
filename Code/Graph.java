/* FILE: Graph.java
 * AUTHOR: Michael Chai Chon Yun
 * UNIT: COMP1002
 * PURPOSE: Store locations and their data in the form of GraphNode Objects
 *          Store graph edges via adjacency lists
 * REFERENCE: Own work, Practical 6
 * COMMENTS: Stores GraphNode objects using a graph
 * REQUIRES: GraphNode.java, LinkedList.java, ListNode.java
 * Last Mod: 27 May 2023
 */

public class Graph
{
    public LinkedList vertices = new LinkedList();
    int vertexCount = 0;
    int edgeCount = 0;

    public void addVertex(String inLocation)
    {
        boolean vertexExists = false;

        for (ListNode curVert=vertices.head; curVert!=null; curVert=curVert.getNext())
        {   //iterating through the vertices
            GraphNode node1 = (GraphNode) ((LinkedList) curVert.getValue()).peekFirst();
            if ( node1.getLocation().equals(inLocation))
            {   //retrieve the destination node
                vertexExists = true;
            }
        }

        if (!vertexExists)
        {
            LinkedList currentList = new LinkedList();
            currentList.insertLast(new GraphNode(inLocation));
            vertices.insertLast(currentList);
            vertexCount++;
        }
        else
        {
            //System.out.println("Vertex with same label '" + label + "'' already exists!");
        }
    }

    public void addEdge(String src, String dst, Object distance)
    {
        if (!isAdjacent(src, dst))
        {
            addEdgeWrap(src, dst, distance);
        }
        if (!isAdjacent(dst, src))
        {
            addEdgeWrap(dst, src, distance);
        }
        edgeCount++;
    }

    private void addEdgeWrap(String src, String dst, Object distance)
    {
        GraphNode dstNode = getVertex(dst); //retrieve destination node
        LinkedList curList = getAdjacent(src); //retrieve adjacency list of source node

        if (dstNode != null) 
        {   //append destination node to adjacency list of source node
            ((GraphNode) curList.head.getValue()).addEdge(dstNode);
            curList.insertLast(dstNode);
            curList.tail.setDistance(distance);
        }
        else
        {
            System.out.println("Cannot create edge " + src + dst + " as vertex " + dst + " does not exist!");
        }
    }

    public boolean hasVertex(String inLocation)
    {
        boolean exists = true;
        if (getVertex(inLocation) == null)
        {
            exists = false;
        }
        return exists;
    }

    public int getVertexCount()
    {
        return vertexCount;
    }

    public int getEdgeCount()
    {
        return edgeCount;
    }

    public GraphNode getVertex(String inLocation)
    {
        GraphNode ans = null;
        for (ListNode curVert=vertices.head; curVert!=null; curVert=curVert.getNext())
        {
            GraphNode node1 = (GraphNode) ((LinkedList) curVert.getValue()).peekFirst();
            if ( node1.getLocation().equals(inLocation))
            {
                ans = node1;
            }
        }
        return ans;
    }

    public LinkedList getAdjacent(String inLocation)
    {
        LinkedList ans = new LinkedList();
        for (ListNode curVert=vertices.head; curVert!=null; curVert=curVert.getNext())
        {
            GraphNode node1 = (GraphNode) ((LinkedList) curVert.getValue()).peekFirst();
            if ( node1.getLocation().equals(inLocation))
            {
                ans = (LinkedList) curVert.getValue();
            }
        }
        return ans;
    }

    public boolean isAdjacent(String location1, String location2)
    {
        boolean ans = false;
        LinkedList adjList = getAdjacent(location1);
        for (ListNode cur = adjList.head.getNext(); cur!=null ; cur=cur.getNext())
        {
            GraphNode node = (GraphNode) cur.getValue();
			if (node.getLocation().equals(location2))
            {
                ans = true;
            }
        }
        return ans;
    }
    
    public void displayLocations() 
    {
        System.out.println("\nGraph displayed as Adjacency List:");
		for (ListNode curVert=vertices.head; curVert!=null; curVert=curVert.getNext())
        { //iterate through every vertex
            LinkedList curList = (LinkedList) curVert.getValue();
            //extract the list from the vertex

            System.out.print(((GraphNode) (curList.head.getValue())).getLocation() + " | ");
            //print the starting vertex

			for(ListNode curNode=curList.head.getNext(); curNode!=null; curNode=curNode.getNext()) 
            { //iterate through each destination vertex in the list
                GraphNode node = (GraphNode) curNode.getValue(); //extract the destination vertex
				System.out.print(curNode.getDistance() + "km -> " + node.getLocation() + " | ");
			}
			System.out.println();
		}
	}

    public void remove(String loc)
    {
        //Step 1: Remove loc from vertices list
        for (ListNode curVert=vertices.head; curVert!=null; curVert=curVert.getNext())
        { //iterate through every vertex
            GraphNode node = (GraphNode) ((LinkedList) curVert.getValue()).peekFirst();
            
            if (node.getLocation().equals(loc))
            {
                if (curVert == vertices.head)
                {
                    vertices.removeFirst();
                }
                else if (curVert == vertices.tail)
                {
                    vertices.removeLast();
                }
                else 
                {
                    curVert.getPrev().setNext(curVert.getNext());
                    curVert.getNext().setPrev(curVert.getPrev());
                }
            }
        }
        vertexCount--;

        //Step 2: Remove loc where other vertices are adjacent to it
        for (ListNode curVert=vertices.head; curVert!=null; curVert=curVert.getNext())
        { //iterate through every vertex, checking if it is adjacent to loc
            GraphNode curNode = (GraphNode) ((LinkedList) curVert.getValue()).peekFirst();
            String sLoc = curNode.getLocation();
            if (isAdjacent(sLoc,loc))
            { // if vertex is adjacent to loc
                LinkedList list1 = getAdjacent(sLoc); //extract the vertex's adjacency list
                for (ListNode cur = list1.head.getNext(); cur!=null ; cur=cur.getNext())
                { //iterate through the adjacency list to find and delete loc
                    if (((GraphNode) cur.getValue()).getLocation().equals(loc))
                    {
                        if (cur == list1.tail)
                        {
                            list1.removeLast();
                        }
                        else 
                        {
                            cur.getPrev().setNext(cur.getNext());
                            cur.getNext().setPrev(cur.getPrev());
                        }
                        edgeCount--;
                    }
                }
            }
        }

    }

    

}

/*
    private void DFSUtil(int v,boolean visited[])
    {
        // Mark the current node as visited and print it
        visited[v] = true;
        System.out.print(v+" ");
 
        // Recur for all the vertices adjacent to this vertex
        Iterator<Integer> i = vertices[v].listIterator();
        while (i.hasNext())
        {
            int n = i.next();
            if (!visited[n])
                DFSUtil(n,visited);
        }
    }
 
    // The function to do DFS traversal. It uses recursive DFSUtil()
    public void DFS()
    {
        // Mark all the vertices as not visited(set as
        // false by default in java)
        boolean visited[] = new boolean[vertexCount];
 
        // Call the recursive helper function to print DFS traversal
        // starting from all vertices one by one
        for (int i=0; i<vertexCount; ++i)
            if (visited[i] == false)
                DFSUtil(i, visited);
    }
*/