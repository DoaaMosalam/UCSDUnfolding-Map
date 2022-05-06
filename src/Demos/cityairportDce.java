package Demos;

import java.util.Comparator;

public class cityairportDce implements Comparator<Airport> {
    @Override
    public int compare(Airport o1, Airport o2) {
        return o1.getCity().compareTo(o2.getCity()) ;
    }



}
