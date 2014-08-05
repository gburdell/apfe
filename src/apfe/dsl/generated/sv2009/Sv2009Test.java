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
    package apfe.dsl.generated.sv2009;

import apfe.dsl.generated.sv2009.*;
import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.InputStream;
import apfe.runtime.ParseError;
import apfe.runtime.State;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author gburdell
 */
public class Sv2009Test {

    static String stFname = "/home/gburdell/projects/apfe/test/apfe/dsl/generated/sv2009/m1.v";

    public static void main(String argv[]) {
        if (1 == argv.length) {
            stFname = argv[0];
        }
        (new Sv2009Test()).testAccepti();
    }

    /**
     * Test of accepti method, of class Grammar.
     */
     public void testAccepti() {
        try {
            final String fn = stFname;
            InputStream ins = new InputStream(fn);
            CharBuffer buf = ins.newCharBuffer();
            State st = State.create(buf);
            System.out.println("accepti");
            Grammar gram = new Grammar();
            Acceptor acc = gram.accept();
            if (null != acc) {
                String ss = acc.toString();
                System.out.println("returns:\n========\n" + ss);
            }
            boolean result = (null != acc) && State.getTheOne().isEOF();
            if (!result) {
                ParseError.printTopMessage();
            }
            assert(result);

        } catch (Exception ex) {
            Logger.getLogger(Sv2009Test.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}
