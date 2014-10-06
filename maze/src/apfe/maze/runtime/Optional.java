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

import static apfe.maze.runtime.RunMaze.addRat;
import static apfe.maze.runtime.Util.isNull;

/**
 * @author gburdell
 */
public class Optional extends Edge implements ICreator {

    public Optional(ICreator opt) {
        m_opt = opt;
    }

    private final ICreator m_opt;

    /**
     * The start Vertex of the internal sub-path which models the optional/taken
     * path.
     */
    private Vertex m_subPathStart;

    @Override
    public MazeElement createMazeElement(Vertex start) {
        //We create a subpath
        assert isNull(m_subPathStart);
        m_subPathStart = new Vertex();
        MazeElement unused = m_opt.createMazeElement(m_subPathStart);
        //The single/outside edge is exposed
        addVertices(start, new Vertex());
        return new MazeElement(getDest()) {
        };
    }

    /**
     * Explore 2 paths: 1) where we take edge and 2) where we skip edge
     * altogether (with another rat).
     *
     * @param visitor our curious little rat.
     * @return true if edge can be taken. Side effect: we start another rat down
     * the bypass path.
     */
    @Override
    public boolean canPassThrough(Rat visitor) {
        {   //add bypass before we process (since visitor state could be updated)
           addRat(addPassThrough(visitor.clone()));
        }
        Edge edge = m_subPathStart.getOutGoingEdges().get(0);
        boolean canPass = edge.canPassThrough(visitor);
        if (canPass) {
            visitor.addEdge(edge);
        }
        return canPass;
    }
}
