/*
 * The MIT License
 *
 * Copyright 2013 gburdell.
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
package v2.apfe.runtime;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Encapsulate memoized data: the acceptor and next start after this accepted
 * sequence.
 */
public class Memoize {

    public Memoize() {
        init();
    }

    private void init() {
        stMemos.add(this);
    }
    
    /**
     * Memoize data.
     *
     * @param start start/key of this (matched) Acceptor.
     * @param nonterm encapsulate accepted sequence.
     * @param mark position <b>after</b> accepted sequence.
     */
    public void add(Marker start, Acceptor nonterm, Marker mark) {
        Data dat = new Data(nonterm, mark);
        m_memoized.put(start, dat);
    }

    /**
     * Check if this mark has memoized data for this Acceptor.
     *
     * @param mark start position of Acceptor.
     * @return memoized Data or null (if none exists).
     */
    public Data memoized(Marker mark) {
        return m_memoized.get(mark);
    }

    /**
     * Keep track of all the Memoize created.
     * Can then reset these when necessary.
     */
    private static List<Memoize> stMemos = new LinkedList<>();
    
    public static void reset() {
        for (Memoize item : stMemos) {
            item.m_memoized.clear();
        }
        stBar++;
    }
    
    public static class MaxSizeHashMap<K, V> extends LinkedHashMap<K, V> {

        private final int maxSize;

        public MaxSizeHashMap(int maxSize) {
            this.maxSize = maxSize;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
            return size() > maxSize;
        }
    }

    public static class Data extends gblib.Util.Pair<Acceptor, Marker> {

        public Data(Acceptor accepted, Marker endMark) {
            super(accepted, endMark);
        }

        public Acceptor getAcceptor() {
            return this.e1;
        }

        public Marker getMark() {
            return this.e2;
        }
    }
    
    private final static int stMemoizeSize;
    public static boolean stEnableMemoize;
    /**
     * When memoize is reset, we raise the bar to prevent Acceptors
     * in process from adding stale memo.
     */
    private static long stBar = 1;
    
    public static long getTheBar() {
        return stBar;
    }

    static {
        stEnableMemoize = "true".equals(System.getProperty("apfe.runtime.EnableMemoize", "true"));
        stMemoizeSize = Integer.parseInt(System.getProperty("apfe.runtime.MemoizeSize", "10"));
    }
    
    private Map<Marker, Data> m_memoized = new MaxSizeHashMap<>(stMemoizeSize);
}
