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
package apfe.maze.sv2009;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gburdell
 */
public class ScannerTest {

    private static final String stFname = "/home/gburdell/projects/apfe/maze/test/apfe/maze/sv2009/m1.v";

    private static void print(final Token tok) {
        System.out.println(tok.toString());
    }

    private static Path[] getFiles() {
        final String dir = "/home/gburdell/projects/apfe/maze/test/apfe/maze/sv2009";
        final String fns[] = {
            "m1.v", "or1200_top.flat.v", "s1_top.flat.v" /*, "test.flat.v"*/
            //"m1.v"
            //"m2.v"
        };
        Path paths[] = new Path[fns.length];
        for (int i = 0; i < paths.length; i++) {
            paths[i] = FileSystems.getDefault().getPath(dir, fns[i]);
        }
        return paths;
    }

    /**
     * Test of nextToken method, of class Scanner.
     */
    @Test
    public void testNextToken() throws Exception {
        Token.stLocationHasFileName = false;
        for (Path path : getFiles()) {
            String fn = path.toString();
            System.out.println("Info: " + fn + " ...");
            Scanner instance = new Scanner(fn);
            Token tok = null;
            while (!instance.isEOF()) {
                tok = instance.nextToken();
                assertNotNull(tok);
                print(tok);
            }
        }
    }

}
