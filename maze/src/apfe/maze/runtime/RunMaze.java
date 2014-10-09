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

import java.util.Queue;

/**
 * Start with one rat which enters maze.
 *
 * @author gburdell
 */
public class RunMaze {
    private static RunMaze stTheOne;
    
    public static int run(Scanner scanner, Acceptor start) {
        assert Util.isNull(getTheOne());
        stTheOne = new RunMaze(scanner, start);
        return getTheOne().run();
    }
    
    public static RunMaze getTheOne() {
        return stTheOne;
    }
    
    private RunMaze(Scanner scanner, Acceptor start) {
        m_rat0 = new Rat(scanner);
        m_start = start;
    }
    
    private int run() {
        m_done = m_start.accept(m_rat0);
        return m_done.size();
    }
    
    private final Acceptor  m_start;
    private final Rat       m_rat0;
    private RatsNest        m_done;
}
