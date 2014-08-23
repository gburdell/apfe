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

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * The queue of work (bits) attempting to accept tokens through the maze
 * of possible paths/acceptors.
 * 
 * @author gburdell
 */
public class WorkQueue {
    public static class Work {
        
    }
    
    public boolean isEmpty() {
        return m_fifo.isEmpty();
    }
    
    public int length() {
        return m_fifo.size();
    }
    
    public Work peek() {
        return m_fifo.get(0);
    }
    
    public Work pop() {
        return m_fifo.remove(0);
    }
    
    public void push(Work item) {
        m_fifo.add(item);
    }
    
    private final List<Work> m_fifo = Collections.synchronizedList(new LinkedList());
}
