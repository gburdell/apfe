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
package apfe.v2.vlogpp;

import gblib.FileCharReader;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;

/**
 *
 * @author gburdell
 */
public class FileCharReaderTest {
    
    @Test
    public void testAll() {
        try {
            //final String fname = "/home/gburdell/projects/./vlog/s1_core/hdl/s1_top.flat.v";
            final String fname = "/home/gburdell/projects/privData/top.all.v";
            System.out.println("testAll: " + fname);
            FileCharReader instance = new FileCharReader(fname);
            int c, nchar = 0;
            while (FileCharReader.EOF != (c = instance.next())) {
                nchar++;
            }
            // TODO review the generated test code and remove the default call to fail.
            //fail("The test case is a prototype.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FileCharReaderTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
