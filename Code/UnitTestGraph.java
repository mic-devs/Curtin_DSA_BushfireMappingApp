public class UnitTestGraph 
{
    public static void main(String[] args) 
    {
        String locationFile = "location.txt"; //Assign the .txt file with location data here
        
        Graph m1 = new Graph(); //create an empty graph
        m1 = FileRead.inFileData(m1, locationFile); //load locations into the graph

        //still need to work on removing edges of removed vertex
        //m1.remove('C');
        m1.displayLocations();

        System.out.println("\nNew graph below should have location 'C' removed");

        m1.remove("C");
        m1.displayLocations();
        
        System.out.println("\nOther methods:");

        boolean actual = m1.hasVertex("A");
        System.out.println("hasVertex(): " + actual);

        actual = m1.getEdgeCount() == 11;
        System.out.println("getEdgeCount(): " + actual);

        actual = m1.getVertexCount() == 9;
        System.out.println("getVertexCount(): " + actual);

    }    
}
