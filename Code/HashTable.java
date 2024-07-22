/*
 * FILE: HashTable.java
 * AUTHOR: Michael Chai Chon Yun
 * UNIT: COMP1002
 * PURPOSE: Store and retrieve data gathered by UAVs
 * REFERENCE: Own work, Practical 8
 * COMMENTS: Each HashEntry stores data in the form of GraphNodes
 * REQUIRES: HashEntry.java
 * Last Mod: 27 May 2023
 */

import java.io.*;
import java.lang.Math;

public class HashTable
{
    HashEntry[] hashArray;
    int size;

    public HashTable(int size)
    {
        this.size = nextPrime(size);
        hashArray = new HashEntry[size];
        for (int i=0; i<size; i++)
        {   //fill up new hashArray with empty hashEntries
            hashArray[i] = new HashEntry();
        }
    }

    public void put(String key, Object val)
    {
        if (getLoadFactor() < 0.125)    //check load factor and resize if needed
        {
            resize(nextPrime(size/2));
        }
        if (getLoadFactor() > 0.6)
        {
            resize(nextPrime(size*2));
        }

        int spot = hash(key);
        int hashStep = stepHash(key);
        HashEntry entry = new HashEntry(key, val);
        boolean duplicate = false;
        int count = 0;

        while (!duplicate && count<hashArray.length) //check for duplicates
        {
            if (hashArray[count].state == 1)
            {
                if (hashArray[count].getKey().equals(key))
                {
                    duplicate = true;
                }
            }
            count++;
        }

        if (!duplicate) //only insert to hashArray when no duplicate found
        { //increment a step-hash until empty slot found before inserting
            while (hashArray[spot].state == 1) 
            {
                spot = (spot + hashStep) % size;
            }
            hashArray[spot] = entry;
        }
    }

    public Object get(String key)
    {
        Object val = hashArray[find(key)].getValue();
        return val;
    }
    
    public void remove(String key)
    {
        hashArray[find(key)].used(); 
        if (getLoadFactor() < 0.125) //check load factor and resize if necessary
        {
            resize(nextPrime(size/2));
        }
        else if (getLoadFactor() > 0.6)
        {
            resize(nextPrime(size*2));
        }
    }

    public double getLoadFactor()
    {
        int count = 0;

        for (int i = 0; i < hashArray.length; i++)
        {
            if (hashArray[i].state == 1)
            {
                count++;
            }
        }

        double loFac = (double) count/ (double)size;
        return loFac;
    }

    public void export(String fileName)
    {
        try
        {
            File csvFile = new File(fileName);
            FileWriter fileWriter = new FileWriter(csvFile);
            for (int i2=0; i2 < hashArray.length; i2++) 
            {
                StringBuilder line2 = new StringBuilder();
                line2.append(hashArray[i2].getKey());
                line2.append(',');
                line2.append(hashArray[i2].getValue());
                line2.append("\n");
                fileWriter.write(line2.toString());
            }
            fileWriter.close();
        }
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }
    }

    public boolean hasKey(String key)
    {
        int spot = hash(key);
        int origIdx = spot;
        int hashStep = stepHash(key);
        boolean found = false;
        boolean giveUp = false;

        while ((!found) && (!giveUp))
        {
            if (hashArray[spot].state != 2)
            {
                if (hashArray[spot].state == 0)
                {
                    giveUp = true;
                }
                else if (hashArray[spot].getKey().equals(key))
                {
                    found = true;
                }
                else 
                {
                    spot = (spot + hashStep) % size;
                    if (spot == origIdx)
                    {
                        giveUp = true;
                    }
                }
            }
            else
            {
                spot = (spot + hashStep) % size;
                if (spot == origIdx)
                {
                    giveUp = true;
                }
                
            }
        }

        return found;
    }

    private void resize(int newSize)
    {
        HashEntry[] oldArr = hashArray;
        hashArray = new HashEntry[newSize];
        size = newSize;

        for (int i=0; i<size; i++)
        {
            hashArray[i] = new HashEntry();
        }

        for (int i=0; i<oldArr.length; i++)
        {
            if (oldArr[i].state == 1)
            {
                String key = oldArr[i].getKey();
                Object value = oldArr[i].getValue();
                iPut(key, value);
            } 
        }
    }

    private void iPut(String key, Object val)
    { // put() without resizing
        int spot = hash(key);
        int hashStep = stepHash(key);
        HashEntry entry = new HashEntry(key, val);

        while (hashArray[spot].state == 1)
        {
            spot = (spot + hashStep) % size;
        }

        hashArray[spot] = entry;
        
    }

    private int hash(String key)
    {
        long hashIdx = 0;
        for (int ii = 0; ii < key.length(); ii++) 
        {
            hashIdx = (31 * hashIdx) + key.charAt(ii);
        }

        int h1 = (int) (hashIdx % size);
        if (h1 < 0)
        {
            h1 = (int) (hashIdx % size);
            h1 = h1*-2;
        }

        return h1;
    }
    
    private int stepHash(String key)
    {
        long hashIdx = 0;
        for (int ii = 0; ii < key.length(); ii++) 
        {
            hashIdx = (33 * hashIdx) + key.charAt(ii);
        }

        int h2 = (int) (hashIdx % size);
        if (h2 < 0)
        {
            h2 = (int) (hashIdx % size);
            h2 = h2*-2;
        }

        return h2;
    }

    private int nextPrime(int num)
    {
        int prime;

        if (num % 2 == 0)
        {
            prime = num - 1;
        }
        else
        {
            prime = num;
        }

        boolean isPrime = false;

        do
        {
            prime += 2;
            int ii = 3;
            isPrime = true;
            double rootVal = Math.sqrt(prime);
            do
            {
                if (prime % ii == 0)
                {
                    isPrime = false;
                }
                else
                {
                    ii += 2;
                }
            }
            while (ii <= rootVal && isPrime);
        }
        while (!isPrime);

        return prime;
    }
    
    private int find(String key)
    {
        int spot = hash(key);
        int origIdx = spot;
        int hashStep = stepHash(key);
        boolean found = false;
        boolean giveUp = false;

        while ((!found) && (!giveUp))
        {
            if (hashArray[spot].state != 2)
            {
                if (hashArray[spot].state == 0)
                {
                    giveUp = true;
                }
                else if (hashArray[spot].getKey().equals(key))
                {
                    found = true;
                }
                else 
                {
                    spot = (spot + hashStep) % size;
                    if (spot == origIdx)
                    {
                        giveUp = true;
                    }
                }
            }
            else
            {
                spot = (spot + hashStep) % size;
                if (spot == origIdx)
                {
                    giveUp = true;
                }
                
            }
        }

        if (!found)
        {
            throw new RuntimeException("Key '" + key + "' does not exist!");
        }

        return spot;
    }
    
}