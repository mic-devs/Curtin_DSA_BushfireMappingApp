/*
 * FILE: FileRead.java
 * AUTHOR: Michael Chai Chon Yun
 * UNIT: COMP1002
 * PURPOSE: Reads in data from location.txt and UAVdata.txt
 * COMMENTS: Location data to load into a pre-existing Graph, UAVdata to load into a HashTable.
             Must load location data first since HashTable will refer to it.
 * REQUIRES: Graph.java & HashTable.java, location.txt & UAVdata.txt, and pre-existing Graph
 * Last Mod: 27 May 2023
 */

import java.io.*;

public class FileRead 
{
    static String line = "";  
    static String splitBy = " ";

    public static Graph inFileData(Graph graph, String filename)
    {
        //read in locations and edges
        try
        {  
            BufferedReader br = new BufferedReader(new FileReader(filename));  
            while ((line = br.readLine()) != null)
            {
                String[] row = line.split(splitBy);

                if (row.length == 3)
                {
                    if (!(graph.hasVertex(row[0])))
                    {
                        graph.addVertex(row[0]);
                    }
                    if (!(graph.hasVertex(row[1])))
                    {
                        graph.addVertex(row[1]);
                    }
                    if (!(graph.isAdjacent(row[0], row[1])))
                    {
                        graph.addEdge(row[0], row[1], row[2]);
                    }
                }
            }
            br.close();
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }

        return graph;
    }

    public static HashTable hashData(Graph graph, String filename)
    {
        HashTable hTab = new HashTable(1000);
        try   
        {  
            BufferedReader br = new BufferedReader(new FileReader(filename));  
            while ((line = br.readLine()) != null)
            {
                String[] row = line.split(splitBy);

                if (graph.hasVertex(row[0]))
                {
                    GraphNode node = new GraphNode(row[0]);
                    node.setTemperature(Integer.parseInt(row[1]));
                    node.setHumidity(Integer.parseInt(row[2]));
                    node.setWindSpeed(Integer.parseInt(row[3]));
                    hTab.put(row[0], node);
                }
            }
            br.close();
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }

        return hTab;
    }

}

/*
    //read in UAV data
    try   
    {  
        BufferedReader br = new BufferedReader(new FileReader("UAVdata.txt"));  
        while ((line = br.readLine()) != null)
        {
            String[] row = line.split(splitBy);

            char loc = (row[0]).charAt(0);
            int temp = Integer.parseInt(row[1]);
            int humid = Integer.parseInt(row[2]);
            int wind = Integer.parseInt(row[3]);

            if (graph.hasVertex(loc))
            {
                GraphNode node = graph.getVertex(loc);
                node.setTemperature(temp);
                node.setHumidity(humid);
                node.setWindSpeed(wind);
            }


        }
        br.close();
    }   
    catch (IOException e)   
    {  
        e.printStackTrace();  
    }
     */
