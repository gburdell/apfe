//The MIT License
//
//Copyright (c) 2014-      George P. Burdell
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in
//all copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//THE SOFTWARE.
package apfe.dsl.vlogpp;

import java.io.IOException;
import java.io.PipedWriter;
import java.io.PrintWriter;

/**
 *
 * @author gburdell
 */
public class WriterThread extends Thread {

    public WriterThread(String[] argv) {
        m_argv = argv;
        m_pipedWriter = new PipedWriter();
        m_os = new PrintWriter(m_pipedWriter, true);
    }

    public PipedWriter getWriter() {
        return m_pipedWriter;
    }

    @Override
    public void run() {
        try {
            VppMain notUsed = new VppMain(m_argv, m_os);
            m_pipedWriter.close();
            m_os.close();
        } catch (IOException ex) {
            gblib.Util.abnormalExit(ex);
        }
    }
    private final String[] m_argv;
    private final PipedWriter m_pipedWriter;
    private final PrintWriter m_os;
    
}
