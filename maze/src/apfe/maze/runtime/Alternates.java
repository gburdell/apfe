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
import java.util.List;

/**
 * @author gburdell
 */
public class Alternates extends Edge implements ICreator {

    public Alternates(ICreator... eles) {
        m_eles = eles;
    }

    private final ICreator m_eles[];

    /**
     * The start Vertex of the internal sub-path which models the alternative
     * path.
     */
    private Vertex m_subPathStart;

    @Override
    public MazeElement createMazeElement(Vertex start) {
        //We create a subpath with alternates
        assert isNull(m_subPathStart);
        m_subPathStart = new Vertex();
        for (ICreator ele : m_eles) {
            ele.createMazeElement(m_subPathStart);
        }
        //The single/outside edge is exposed
        addVertices(start, new Vertex());
        return new MazeElement(getDest()) {
        };
    }

    @Override
    public boolean canPassThrough(Rat visitor) {
        Edge edge;
        boolean canPass;
        final List<Edge> edges = m_subPathStart.getOutGoingEdges();
        //explore alternatives with new rats
        for (int i = 1; i < edges.size(); i++) {
            Rat newRat = visitor.clone();
            edge = edges.get(i);
            canPass = edge.canPassThrough(newRat);
            if (canPass) {
                addRat(addPassThough(newRat));
            } else {
                newRat.destroy();   //get rid of path
            }
        }
        //do 1st alternate with original
        edge = edges.get(0);
        canPass = edge.canPassThrough(visitor);
        if (canPass) {
            visitor.addEdge(edge);
        }
        return canPass;
    }
}
