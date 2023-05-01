package com.example.demo.extension;

import java.util.Comparator;

import static java.lang.Integer.compare;

/**
 * @author RL475
 * 优先级
 */
public interface Prioritize extends Comparable<Prioritize> {

    int MAX_PRIORITY = Integer.MIN_VALUE;
    int MIN_PRIORITY = Integer.MAX_VALUE;
    int DEFAULT_PRIORITY = 0;
    Comparator COMPARATOR = ((o1, o2) -> {
        boolean b1 = o1 instanceof Prioritize;
        boolean b2 = o2 instanceof Prioritize;
        if (b1 && !b2) {        // one is Prioritized, two is not
            return -1;
        } else if (b2 && !b1) { // two is Prioritized, one is not
            return 1;
        } else if (b1 && b2) {  //  one and two both are Prioritized
            return ((Prioritize) o1).compareTo((Prioritize) o2);
        } else {                // no different
            return 0;
        }
    });

    default int getPriority() {
        return DEFAULT_PRIORITY;
    }

    @Override
    default int compareTo(Prioritize that) {
        return compare(this.getPriority(), that.getPriority());
    }
}
