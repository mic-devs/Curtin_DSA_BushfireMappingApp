/* FILE: GraphNode.java
   AUTHOR: Michael Chai Chon Yun
   UNIT: COMP1002
   PURPOSE: Graph Node for Graph
   REFERENCE: Own work, Practical 6
   REQUIRES: LinkedList.java
   Last Mod: 27 May 2023
*/

public class GraphNode 
{
    private String location;
    private int temperature;
    private int humidity;
    private int windSpeed;
    private LinkedList list = new LinkedList();
	
	public GraphNode(String inLocation){
		this.location = inLocation;
	}

    public String getLocation()
    {
        return this.location;
    }

    public void setTemperature(int temp)
    {
        this.temperature = temp;
    }

    public int getTemperature()
    {
        return this.temperature;
    }
    
    public void setHumidity(int humid)
    {
        this.humidity = humid;
    }

    public int getHumidity()
    {
        return this.humidity;
    }

    public void setWindSpeed(int ws)
    {
        this.windSpeed = ws;
    }

    public int getWindSpeed()
    {
        return this.windSpeed;
    }

    public void addEdge(GraphNode vertex)
    {
        this.list.insertLast(vertex);
    }

    public LinkedList getAdjacent()
    {
        return list;
    }
    

}
