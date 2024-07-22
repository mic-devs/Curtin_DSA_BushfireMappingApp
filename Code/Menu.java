/*
 * FILE: Menu.java
   AUTHOR: Michael Chai Chon Yun
   UNIT: COMP1002
   PURPOSE: User Interface for overall Bushfire Monitoring Program
   REQUIRES: Graph.java, HashTable.java, Heap.java, FileRead.java, and their associated classes
             Data files: location.txt & UAVdata.txt
   Last Mod: 27 May 2023
 */

import java.util.Scanner;

public class Menu 
{
    public static void main(String[] args) 
    {
        String locationFile = "location.txt"; //Assign the .txt file with location data here
        String uavDataFile = "UAVdata.txt"; //Assign the .txt file with UAV data here

        Graph m1 = new Graph(); //create an empty Graph
        m1 = FileRead.inFileData(m1, locationFile); //load the Graph with locations and their edges
        HashTable uavData = FileRead.hashData(m1, uavDataFile); //create and load a HashTable with uavData

        Object choice = 0; //default value
        while (!choice.equals("7"))
        {
            System.out.println("\n***Bushfire Monitoring Program***");
            System.out.println("What would you like to do?\n");
            System.out.println("(1) Insert a new location");
            System.out.println("(2) Delete a location");
            System.out.println("(3) Search for a location");
            System.out.println("(4) Update location data");
            System.out.println("(5) Display locations with risk value");
            System.out.println("(6) Display locations graph");
            System.out.println("(7) Exit the program\n");
            System.out.print("Your choice: ");
            Scanner myObj = new Scanner(System.in);
            choice = myObj.nextLine();

            if (choice.equals("1"))
            { //insert
                m1 = insert(m1,myObj);
            }
            else if (choice.equals("2"))
            { //delete
                String delLoc = "filler";
                boolean accepted = false;

                while (!accepted)
                {
                    System.out.print("Enter Location ID: ");
                    String input = myObj.nextLine();

                    delLoc = input;
                    if (!m1.hasVertex(delLoc))
                    {
                        System.out.println("\nERROR: Location does not exists!");
                    }
                    else
                    {
                        accepted = true;
                    }
                }

                m1.remove(delLoc); //delete the location from the graph
                if (uavData.hasKey(delLoc))
                {    //delete the location's UAV data from the hashtable, if any
                    uavData.remove(delLoc);
                }
                System.out.println("\nLocation " + delLoc + " has been deleted!");
            }
            else if (choice.equals("3"))
            { //search
                search(uavData, myObj);
            }
            else if (choice.equals("4"))
            { //update
                update(m1, uavData, myObj);
            }
            else if (choice.equals("5"))
            { //display risks
                hashToHeap(m1, uavData);
            }
            else if (choice.equals("6"))
            { //display graph
                m1.displayLocations();
            }
            else if (choice.equals("7"))
            { //exit
                System.out.println("\nGoodbye!");
                myObj.close();
            }
            else 
            { //choice error
                System.out.println("\n[ERROR] Enter an integer from 1-6 ONLY!");
            }
        }
    }

    private static Graph insert(Graph m2, Scanner myObj)
    {
        String newLoc = "filler";
        boolean accepted = false;

        while (!accepted)
        { //receive valid location ID from user
            System.out.print("Enter new Location ID: ");
            String input = myObj.nextLine();

            newLoc = input;
            if (m2.hasVertex(newLoc))
            {
                System.out.println("\nERROR: Location with same ID already exists!");
            }
            else
            {
                accepted = true;
            }
        }

        m2.addVertex(newLoc); //add the new location to the Graph
        return m2;
    }

    private static void search(HashTable m2, Scanner myObj)
    {
        String searchLoc = "filler";
        boolean accepted = false;

        while (!accepted)
        { //receive a valid location ID from user
            System.out.print("Enter search Location ID: ");
            String input = myObj.nextLine();

            searchLoc = input;
            if (!m2.hasKey(input))
            {
                System.out.println("\nERROR: No associated data with location " + input + "!\n");
            }
            else
            {
                accepted = true;
            }
        }

        GraphNode found = (GraphNode) m2.get(searchLoc); //retrieve the desired location (GraphNode)
        System.out.println("\nLocation " + searchLoc + " info:");
        System.out.println("Temperature: " + found.getTemperature() + " degrees Celsius");
        System.out.println("Humidity: " + found.getHumidity() + "%");
        System.out.println("Wind Speed: " + found.getWindSpeed() + " km/h");
    }

    private static HashTable update(Graph m2, HashTable h1, Scanner myObj)
    {
        String upLoc = "filler";
        boolean accepted = false;
        while (!accepted)
        { //get a valid location ID from user
            System.out.print("Enter Location ID: ");
            String input = myObj.nextLine();

            upLoc = input;
            if (!m2.hasVertex(input))
            {
                System.out.println("\nERROR: No such location exists!");
            }
            else
            {
                accepted = true;
            }
        }

        int temp = 0;
        while (temp < 25 || temp > 48)
        { //validate temperature input
            System.out.println("\nNOTE: Temperature must be an integer from 25 to 48 only!");
            System.out.print("New Temperature (degree celsius): ");
            
            try 
            {
                String inputT = myObj.nextLine();
                temp = Integer.parseInt(inputT);
            } catch (Exception e) {
                System.out.println("ERROR: Input invalid data type!");
            }
        }

        int humid = 0;
        while (humid < 15 || humid > 60)
        { //validate humidity input
            System.out.println("\nNOTE: Humidity must be an integer from 15 to 60 only!");
            System.out.print("New Humidity (%): ");
            
            try 
            {
                String inputH = myObj.nextLine();
                humid = Integer.parseInt(inputH);
            } catch (Exception e) {
                System.out.println("ERROR: Input invalid data type!");
            }
        }

        int wind = 0;
        while (wind < 30 || wind > 100)
        { // validate wind speed input
            System.out.println("\nNOTE: Wind Speed must be an integer from 30 to 100 only!");
            System.out.print("New Wind Speed (km/h): ");
            
            try 
            {
                String inputW = myObj.nextLine();
                wind = Integer.parseInt(inputW);
            } catch (Exception e) {
                System.out.println("ERROR: Input invalid data type!");
            }
        }

        if (h1.hasKey(upLoc))
        {   //removes pre-existing HashEntry if any
            h1.remove(upLoc);
        }

        GraphNode newLoc = new GraphNode(upLoc); //create the update location
        newLoc.setTemperature(temp);
        newLoc.setHumidity(humid);
        newLoc.setWindSpeed(wind);
        h1.put(upLoc, newLoc); //add the updated location to the hash table
        return h1;
    }

    private static void hashToHeap(Graph m1, HashTable uav)
    {
        Heap riskData = new Heap(m1.getVertexCount());

        for (int i=0; i<uav.size; i++)
        { //add all UAVdata from HashTable to riskData heap
            if (uav.hashArray[i].state == 1)
            {
                GraphNode node = (GraphNode) uav.hashArray[i].getValue();
                riskData.add(node);
            }
        }

        // create a HeapEntry array to store locations sorted via risk value
        HeapEntry[] sorted = new HeapEntry[m1.getVertexCount()];
        sorted = HeapSort.heapSort(riskData.heap, m1.getVertexCount());
        // pass riskData heap to heapSort to sort the HeapEntry array

        System.out.println("\n[Location : Risk]");
        for (int i=0; i < sorted.length; i++)
        { //Print out all the sorted values
            if (sorted[i] != null)
            {
            GraphNode node = (GraphNode) sorted[i].getValue();
            System.out.println(node.getLocation() + " : " + sorted[i].getPriority());
            }
        }
    }
}


