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
    
    public static void start(Scanner start) {
        assert Util.isNull(stTheOne);
        stTheOne = new RunMaze(start);
    }
    
    public static RunMaze getTheOne() {
        return stTheOne;
    }
    
    public static void addRat(Rat rat) {
        getTheOne().getRunningQ().add(rat);
    }
    
    private RunMaze(Scanner start) {
        Rat rat0 = new Rat(start);
        addRat(rat0);
    }
    
    public final Queue getRunningQ() {
        return m_running;
    }

    public final Queue getFinishQ() {
        return m_finished;
    }
    
    private final RatQueue m_running = new RatQueue(), m_finished = new RatQueue();
}
