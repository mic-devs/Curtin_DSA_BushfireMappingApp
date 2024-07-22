/*
 * FILE: HeapEntry.java
   AUTHOR: Michael Chai Chon Yun
   UNIT: COMP1002
   PURPOSE: Heap Entry for Heap
   REFERENCE: Own work, Practical 9
   REQUIRES: n/a
   Last Mod: 27 May 2023
 */

public class HeapEntry 
{
    int priority;
    Object value;

    public HeapEntry(Object inValue)
    {
        this.value = inValue;
        this.priority = getRisk();
    }

    public int getPriority()
    {
        return this.priority;
    }

    public Object getValue()
    {
        return this.value;
    }

    public void setValue(Object inValue)
    {
        this.value = inValue;
        this.priority = getRisk();
    }

    private int getRisk()
    {
        int temp = ((GraphNode)value).getTemperature();
        int humid = ((GraphNode)value).getHumidity();
        int wind = ((GraphNode)value).getWindSpeed();

        int tempR, humidR, windR;

        if (temp <= 32)
        {
            tempR = 1;
        }
        else if (temp > 32 && temp < 41)
        {
            tempR = 2;
        }
        else 
        {
            tempR = 3;
        }

        if (humid >= 50)
        {
            humidR = 1;
        }
        else if (humid < 50 && humid >= 31)
        {
            humidR = 2;
        }
        else 
        {
            humidR = 3;
        }

        if (wind <= 40)
        {
            windR = 1;
        }
        else if (wind > 40 && wind <= 55)
        {
            windR = 2;
        }
        else 
        {
            windR = 3;
        }

        int risk = tempR + humidR + windR;
        return risk;
    }
}