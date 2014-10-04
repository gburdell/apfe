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

import static apfe.maze.runtime.Util.isNull;

/**
 *
 * @author gburdell
 */
public class Sequence extends Edge implements ICreator {

    public Sequence(ICreator... eles) {
        m_eles = eles;
    }

    private final ICreator m_eles[];
    /**
     * The start Vertex of the internal sub-path which models
     * the sequence path.
     */
    private Vertex m_subPathStart;

    @Override
    public MazeElement createMazeElement(Vertex start) {
        //We create a subpath
        assert isNull(m_subPathStart);
        m_subPathStart = new Vertex();
        Vertex vx = m_subPathStart;
        MazeElement last;
        //chain in series
        for (ICreator ele : m_eles) {
            last = ele.createMazeElement(vx);
            vx = last.getEndpoint();
        }
        //The single/outside edge is exposed
        addVertices(start, new Vertex());
        return new MazeElement(getDest()) {
        };
    }

    @Override
    public boolean canPassThrough(Rat visitor) {
        Edge edge;
        assert (1 > m_subPathStart.getInDegree());
        Vertex vx = m_subPathStart;
        while (0 < vx.getOutDegree()) {
            assert (1 == vx.getOutDegree());
            edge = vx.getOutGoingEdges().get(0);
            if (! edge.canPassThrough(visitor)) {
                return false;
            }
            vx = edge.getDest();
            assert (1 == vx.getInDegree());
        }
        return true;
    }
}
