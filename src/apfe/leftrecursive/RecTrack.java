/*
 * The MIT License
 *
 * Copyright 2013 gburdell.
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
package apfe.leftrecursive;

import apfe.runtime.Acceptor;
import java.util.Stack;

/**
 * Track recursion.
 *
 * @author gburdell
 */
public class RecTrack {

    /**
     * Get next slot for non-terminal.
     *
     * @return next slot.
     */
    public static int getNextSlot() {
        return m_nextSlot++;
    }

    public static void push(int slot) {
        m_stack.push(new Data(slot));
    }

    public static Data pop() {
        return m_stack.pop();
    }

    public static boolean isLR() {
        boolean b = false;
        Data parent = getParent();
        if (null != parent) {
            b = (parent.m_slot == m_stack.peek().m_slot);
            if (b && (EState.eNull == parent.m_state)) {
                parent.m_state = EState.e1stLR;
            } //if we're growing seed, then let slide through
            else if (b && (EState.eGrowSeed == parent.m_state)) {
                //pass seed here
                m_stack.peek().m_seed = parent.m_seed;
                m_stack.peek().m_cnt = parent.m_cnt + 1;
                b = false;
            }
        }
        return b;
    }

    public static Data getParent() {
        int n = m_stack.size();
        Data parent = (1 < n) ? m_stack.elementAt(n - 2) : null;
        return parent;
    }

    public static <T extends Acceptor> T setSeed(int slot, T grown) {
        Data d = m_stack.peek();
        assert (d.m_slot == slot);
        d.m_seed = grown;
        return grown;
    }

    public static <T extends Acceptor> T getSeed() {
        Data d = m_stack.peek();
        return (T) d.m_seed;
    }

    public static boolean detectedLR(int slot, Acceptor grown) {
        Data d = m_stack.peek();
        boolean b = (slot == d.m_slot) && (EState.e1stLR == d.m_state);
        if (b) {
            //Will only iterate on 1st one
            b &= (1 == d.m_cnt);
            if (b) {
                d.m_seed = grown;
                d.m_state = EState.eGrowSeed;
            }
        }
        return b;
    }

    public static enum EState {

        eNull,
        e1stLR, //detected left recursion
        eGrowSeed,
        eStop
    }

    public static class Data {

        public Data(int slot) {
            m_slot = slot;
        }
        public final int m_slot;
        public Acceptor m_seed;
        public EState m_state = EState.eNull;
        public int m_cnt = 1;
    };
    private static Stack<Data> m_stack = new Stack<>();
    private static int m_nextSlot = 0;
}
