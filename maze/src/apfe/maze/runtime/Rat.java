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
 * The rat wanders through the maze carrying Scanner state and current Path.
 * 
 * @author gburdell
 */
public class Rat {
    public Rat(Scanner scanner, Acceptor start) {
        m_state = new State(scanner, 0);
        m_path = new Path();
        m_start = start;
    }
    
    /**
     * Peek at next available token.
     * @return token next available token.
     */
    public Token peek() {
        return m_state.peek();
    }
    
    public int peekCode() {
        return peek().getCode();
    }
    
    public Token advance() {
        return m_state.advance();
    }

    
    public Rat(Rat r, Acceptor start) {
        m_state = r.m_state.clone();
        m_path = r.m_path.clone();
        m_start = start;
    }
    
    public void destroy() {
        m_state = null;
        m_path.clear();
        m_path = null;
        m_start = null;
    }
    
    public Rat addAccepted(Path.Ele acc) {
        m_path.add(acc);
        return this;
    }
    
    private State       m_state;
    private Path        m_path;
    private Acceptor    m_start;
}