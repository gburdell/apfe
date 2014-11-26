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
package apfe.peg.generate;

import apfe.peg.Definition;
import apfe.peg.Grammar;
import apfe.runtime.CharBuffer;
import apfe.runtime.InputStream;
import apfe.runtime.ParseError;
import apfe.runtime.Util;
import apfe.runtime.CharBufState;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author gburdell
 */
public class Main {

    public static void main(String argv[]) {
        if ((1 > argv.length) || (2 < argv.length)) {
            String usage = "\n"
                    + "Usage: generate file.peg ?properties.txt?\n\n"
                    + "Process 'file.peg' description and generate parser using\n"
                    + "options defined in (optional) 'properties.txt'\n";
            System.err.println(usage);
            System.exit(1);
        }
        if (1 < argv.length) {
            loadProperties(new File(argv[1]));
        }
        try {
            InputStream istr = new InputStream(argv[0]);
            CharBuffer cb = istr.newCharBuffer();
            CharBufState.create(cb);
            Grammar gram = new Grammar();
            gram = Util.downCast(gram.accept());
            int numErrs = 0;
            if (null != gram) {
                System.out.println("grammar:");
                System.out.println(gram.toString());
                stAnyze = new Analyze(gram);
                numErrs = stAnyze.analyze();
            } else {
                ParseError.printTopMessage();
            }
            {
                //dump memoize stats
                long stats[] = CharBufState.getTheOne().getMemoizeStats();
                double pcnt = 0;
                if (0 < stats[1]) {
                    pcnt = (100.0 * stats[0]) / stats[1];
                }
                Util.info("APFE-STAT-1", stats[0], stats[1], pcnt);
            }
            if ((0 < numErrs) || (null == gram)) {
                System.exit(numErrs);
            } else {
                Generate.generate(stAnyze);
            }
        } catch (Exception ex) {
            Util.abnormalExit(ex);
        }
    }
    
    private static Analyze stAnyze;
    
    public static boolean isToken(String idName) {
        Definition defn = stAnyze.getDefnByName().get(idName);
        return ((null != defn) && defn.isToken());
    }
    
    /**
     * Get property value.
     *
     * @param key property name.
     * @return user-specified or default value.
     */
    public static String getProperty(String key) {
        if (! stProps.containsKey(key)) {
            Util.abnormalExit(key+": missing property definition");
        }
        String v = stProps.getProperty(key).trim();
        assert null != v;
        return v;
    }

    /**
     * Get property value as int.
     *
     * @param key property name.
     * @return user-specified or default value.
     */
    public static int getPropertyAsInt(String key) {
        return Integer.parseInt(getProperty(key));
    }

    /**
     * Get property value as boolean.
     *
     * @param key property name.
     * @return user-specified or default value.
     */
    public static boolean getPropertyAsBoolean(String key) {
        return Boolean.parseBoolean(getProperty(key));
    }

    /**
     * Property settings for generate.
     */
    private static final Properties stProps = new Properties();

    private static void loadProperties(File f) {
        try {
            stProps.load(new FileReader(f));
        } catch (IOException ex) {
            Util.abnormalExit(ex);
        }
    }

    static {
        loadProperties(new File(".", "generate.props.txt"));
    }
    
    public static final boolean stGenMaze = getPropertyAsBoolean("genMaze");
    public static final boolean stGenTerminal = getPropertyAsBoolean("genTerminal");
}
