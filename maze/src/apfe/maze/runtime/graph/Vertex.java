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
package apfe.maze.runtime.graph;

import java.util.LinkedList;
import java.util.List;
import static apfe.maze.runtime.Util.*;

/**
 *
 * @author gburdell
 */
public class Vertex {

    public Vertex() {
    }

    public void addIncoming(Edge ele) {
        m_incoming = add(m_incoming, ele);
    }

    public void addOutgoing(Edge ele) {
        m_outgoing = add(m_incoming, ele);
    }

    public int getInDegree() {
        return getDegree(m_incoming);
    }
    
   public int getOutDegree() {
        return getDegree(m_outgoing);
    }
    
    private static int getDegree(List<Edge> coll) {
        return (isNull(coll)) ? 0 : coll.size();
    }
    
    private static List<Edge> add(List<Edge> to, Edge ele) {
        if (isNull(to)) {
            to = new LinkedList<>();
        }
        to.add(ele);
        return to;
    }

    private List<Edge> m_incoming, m_outgoing;
}
