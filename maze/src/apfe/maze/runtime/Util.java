/*
 * The MIT License
 *
 * Copyright 2014 gburdell.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package apfe.maze.runtime;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gburdell
 */
public class Util {
    public static class Triplet<T1, T2, T3> {
        public Triplet() {
        }

        public Triplet(T1 a1, T2 a2, T3 a3) {
            e1 = a1;
            e2 = a2;
            e3 = a3;
        }
        public T1 e1;
        public T2 e2;
        public T3 e3;
    }

 
    public static <T> Queue<T> arrayAsQueue(T ar[]) {
        Queue<T> asQ = new ArrayDeque<>(Arrays.asList(ar));
        return asQ;
    }
    
    public static <T> Collection<T> asCollection(T ele) {
        List<T> list = new LinkedList<>();
        list.add(ele);
        return list;
    }
    
    public static <T> List<T> addToList(List<T> to, Collection<T> items) {
        if ((null != items) && (null == to)) {
            to = new LinkedList<>();
        }
        if (null != items) {
            to.addAll(items);
        }
        return to;
    }
    
    /**
     * Sort list and return as array.
     * @param <T> array type.
     * @param l list to sort.
     * @param comp comparator which imposes ordering over T.
     * @return sorted list as array.
     */
    public static <T> T[] sort(List<T> l, Comparator<T> comp) {
        assert null != l;
        T ar[] = (T[])l.toArray();
        Arrays.sort(ar, comp);
        return ar;
    }
}
