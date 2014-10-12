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

    public Rat(Scanner scanner) {
        m_state = new State(scanner, 0);
        m_path = new Path();
    }

    /**
     * Peek at next available token.
     *
     * @return token next available token.
     */
    public Token peek() {
        return getState().peek();
    }

    public int peekCode() {
        return peek().getCode();
    }

    public State getState() {
        return m_state;
    }

    public Token advance() {
        return getState().advance();
    }

    @Override
    public Rat clone() {
        return new Rat(this);
    }

    public Rat(Rat r) {
        m_state = r.m_state.clone();
        m_path = r.m_path.clone();
    }

    public void destroy() {
        m_state = null;
        m_path.clear();
        m_path = null;
    }

    public Rat addAccepted(Path.Ele acc) {
        m_path.add(acc);
        return this;
    }

    @Override
    public String toString() {
        return m_state.toString() + ": " + m_path.toString();
    }

    private State m_state;
    private Path m_path;
}
