public class UnitTestHashTable
{
    public static void main(String[] args) 
    {
        /*
        String locationFile = "location.txt"; //Assign the .txt file with location data here
        String uavDataFile = "UAVdata.txt"; //Assign the .txt file with UAV data here

        Graph m1 = new Graph(); //create an empty graph
        m1 = FileRead.inFileData(m1, locationFile); //load locations into the graph
        HashTable uavData = FileRead.hashData(m1, uavDataFile); //load uavData into hashtable
        uavData.export("UnitTestHashTable.csv");
        */

        //Above to test HashTable with location.txt

        //Below to test HashTable with alternate values (referenced from own Practical 8)
        //*
        boolean actual;
        HashTable m1 = new HashTable(100);
        actual = (m1.size == 101); //size 100 will seek the next prime, 101
        System.out.println("Constructor, nextPrime(): " + actual);
        
        m1.put("si", 0);
        actual = (m1.get("si").equals(0));
        System.out.println("put(), hash(), get(), find(): " + actual);

        actual = (m1.size == 53); //table has been resized once now, from 101 to 53
        System.out.println("resize() & iPut(): " + actual);

        m1.put("ra", 1); // "ra","sB","t#", all share the same first hash
        m1.put("sB", 2);
        m1.put("t#",3);

        double loFac = m1.getLoadFactor();
        actual = (loFac > 0.23 && loFac < 0.24 ); //load factor now should be 0.23529*
        System.out.println("getLoadFactor(): " + actual);

        m1.put("oP",4);
        m1.remove("oP"); //uncomment the line below to test 
        actual = !(m1.hasKey("oP"));
        System.out.println("remove() & hasKey(): " + actual);
        
        m1.put("ds", 5);
        //Check the export file to verify stepHash (double hashing should produce close to no chunks)
        m1.export("UnitTestHashTable.csv");
        //*/
    }
}
