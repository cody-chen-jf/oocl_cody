package com.oocl;

import java.util.Comparator;

/**
 * Created by CHENCO7 on 7/10/2017.
 */
public class AgeComparator implements Comparator<Customer> {
    public int compare(Customer o1, Customer o2) {
        return o1.age - o2.age;
    }
}
