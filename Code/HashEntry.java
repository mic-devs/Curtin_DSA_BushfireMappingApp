/*
 * FILE: HashEntry.java
   AUTHOR: Michael Chai Chon Yun
   UNIT: COMP1002
   PURPOSE: Hash Entry for Hash Table
   REFERENCE: Own work, Practical 8
   REQUIRES: n/a
   Last Mod: 27 May 2023
 */

public class HashEntry 
{
    String key;
    Object value;
    int state; //0 for empty, 1 for occupied, 2 for used-previously

    public HashEntry()
    {
        this.state = 0;
    }

    public HashEntry(String key, Object value)
    {
        this.key = key;
        this.value = value;
        this.state = 1;
    }

    public Object getValue()
    {
        return value;
    }

    public String getKey()
    {
        return key;
    }
    
    public void used()
    {
        this.key = null;
        this.value = null;
        this.state = 2;
    }
}
