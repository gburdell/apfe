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

/**
 * Base class for a NonTerminal.
 *
 * @author gburdell
 */
public abstract class NonTerminal extends Edge implements ICreator {

    protected NonTerminal(ICreator ele) {
        m_ele = ele;
    }

    private final ICreator m_ele;
    
    @Override
    public MazeElement createMazeElement(Vertex start) {
        //Defer enter exit to canPassThrough()
        //The single outside edge is exposed.
        addVertices(start, new Vertex());
        return new MazeElement(getDest()) {
        };
    }

    @Override
    public boolean canPassThrough(Rat visitor) {
        Edge edge = new Edge.Enter(this.getClass());
        visitor.addEdge(edge);
        //We'll create the maze-element entry edge on the fly
        Vertex vx = 
        return true;
    }
    
}
