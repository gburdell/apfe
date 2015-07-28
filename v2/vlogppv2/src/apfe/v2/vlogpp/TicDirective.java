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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Other compiler directives. `__FILE__ `__LINE__ `begin_keywords `celldefine
 * `default_nettype `define `else `elsif `end_keywords `endcelldefine `endif
 * `ifdef `ifndef `include `line `nounconnected_drive `pragma `resetall
 * `timescale `unconnected_drive `undef `undefineall
 *
 * @author gburdell
 */
public class TicDirective {

    static final Pattern stFile = Pattern.compile("[ \t]*(`__FILE__)([ \t]+.*)?\\s");

    /**
     * Attempt to match line to a compiler directive.
     * @param src source file.
     * @param line current line in source file.
     * @return true on match.  If true, caller should rescan line.
     */
    static boolean process(final SourceFile src, final String line) {
        boolean accepted = true;
        final Matcher matcher = stFile.matcher(line);
        if (matcher.matches()) {
            //`__FILE__ expands to the name of the current input file, 
            //in the form of a string literal. This is the path by
            //which a tool opened the file, not the short name specified 
            //in `include or as a tool’s input file name argument.
            //TODO: stuff
            final String repl = src.getFileLocation().getFile().getCanonicalPath();
            final int span[] = getSpan(matcher, 1);
            src.replace(span, repl);
        } else {
            accepted = false;
        }
        return accepted;
    }
    
    private static int[] getSpan(final Matcher matcher, final int grp) {
        return new int[] {matcher.start(grp), matcher.end(grp)};
    }
}
