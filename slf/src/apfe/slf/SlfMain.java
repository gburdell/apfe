/*
 * The MIT License
 *
 * Copyright 2015 gburdell.
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
package apfe.slf;

import apfe.runtime.Acceptor;
import apfe.runtime.CharBuffer;
import apfe.runtime.InputStream;
import apfe.runtime.CharBufState;
import apfe.slf.generated.CellName;
import apfe.slf.generated.Grammar;

public class SlfMain  {

    public static void main(String argv[]) {
        final String fn = argv[0];
        //final String fn = "/home/gburdell/projects/apfe/nldm1.lib";
        //final String fn = "/home/gburdell/projects/apfe/bug1.lib";
        //final String fn = "/home/gburdell/projects/apfe/ok1.lib";
        CellName.addListener(new Acceptor.Listener() {

            @Override
            public void onAccept(Acceptor accepted) {
                System.out.println("Cell="+accepted.toString());
            }
        });
        try {
            InputStream fis = new InputStream(fn);
            CharBuffer cbuf = fis.newCharBuffer();
            CharBufState st = CharBufState.create(cbuf);
            boolean result;
            Grammar gram = new Grammar();
            Acceptor acc = gram.accept();
            result = (null != acc);
            assert result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
