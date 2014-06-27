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
package apfe.dsl.vlogpp;

/**
 * Toplevel object/singleton for vlogpp system.
 * @author gburdell
 */
public class Main {
    private Main() {}
    
    public static Main getTheOne() {
        return stTheOne;
    }
    
    public MacroDefns getMacroDefns() {
        return m_macroDefns;
    }

    /**
     * Set current conditional allow state.
     * Reflects ability to change state when we are processing
     * conditional statement (like `ifdef).
     * @param m_conditionalAllow value of current allow state.
     */
    public void setConditionalAllow(boolean m_conditionalAllow) {
        this.m_conditionalAllow = m_conditionalAllow;
    }
    
    /**
     * Get current conditional allow state.
     * @return true if state can change; else we are in a blocked
     * statement.
     */
    public boolean getConditionalAllow() {
        return m_conditionalAllow;
    }
   
    private MacroDefns m_macroDefns = new MacroDefns();
    /**
     * State of current conditional clause to allow state changes.
     */
    private boolean m_conditionalAllow = true;
    
    private static Main stTheOne = new Main();
}
