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
 * `default_nettype `end_keywords `endcelldefine `nounconnected_drive `pragma
 * `resetall `timescale `unconnected_drive `undef `undefineall
 *
 * @author gburdell
 */
public class TicDirective {

    static final Pattern stFile = Pattern.compile("[ \t]*(`__FILE__)([ \t]+.*)?\\s");
    static final Pattern stLine = Pattern.compile("[ \t]*(`__LINE__)([ \t]+.*)?\\s");
    static final Pattern stUndef
            = Pattern.compile("[ \t]*((`undef)[ \t]+([_a-zA-Z][_a-zA-Z0-9]*))(.*)(\\s)");
    static final Pattern stProtect
            = Pattern.compile("[ \t]*(`protect(ed)?)([ \t]+.*)?\\s");
    static final Pattern stEndProtect
            = Pattern.compile("([ \t]*(`endprotect(ed)?))([ \t]+.*)?\\s");

    /**
     * Attempt to match line to a compiler directive.
     *
     * @param src source file.
     * @param line current line in source file.
     * @return true on match. If true, caller should rescan line.
     */
    static boolean process(final SourceFile src, String line) throws ParseError {
        boolean accepted = true;
        Matcher matcher;
        if (null != (matcher = match(stFile, line))) {
            //`__FILE__ expands to the name of the current input file, 
            //in the form of a string literal. This is the path by
            //which a tool opened the file, not the short name specified 
            //in `include or as a tool’s input file name argument.
            update(src, getSpan(matcher, 1),
                    "\"" + src.getFileLocation().getFile().getCanonicalPath() + "\"");
        } else if (null != (matcher = match(stLine, line))) {
            //`__LINE__ expands to the current input line number, 
            //in the form of a simple decimal number.
            update(src, getSpan(matcher, 1),
                    Integer.toString(src.getFileLocation().getLineNum()));
        } else if (null != (matcher = match(stUndef, line))) {
            if (src.getEchoOn()) {
                src.undef(matcher.group(3));
            }
            update(src, getSpan(matcher, 1), stEmpty);
        } else if (null != (matcher = match(stProtect, line))) {
            /**
             * NOTE: We unconditionally process the `protect block.
             */
            final int[] started = src.getStartMark();
            final String startTok = matcher.group(1);
            boolean loop = true;
            while (loop) {
                src.printNL();
                if (src.isEOF()) {
                    throw new ParseError("VPP-EOF-2", src.getLocation(), 
                            startTok, started[0], started[1]);
                }
                src.accept(line.length());
                line = src.remainder();
                loop = (null == (matcher = match(stEndProtect, line)));
            }
            assert !loop && (null != matcher);
            update(src, getSpan(matcher, 1), stEmpty);
        } else {
            accepted = false;
        }
        return accepted;
    }

    /**
     * Update source buffer with replacement text if source is doing echo. Else,
     * just update the source buffer directly to skip over this match.
     *
     * @param src source.
     * @param span span of match to replace (if applicable).
     * @param repl replacement text (if applicable).
     */
    private static void update(final SourceFile src, final int[] span,
            final String repl) {
        if (src.getEchoOn()) {
            //TODO: +1 throws off call from `endprotect.
            //since span[1] will be at next char after match end,
            //not sure why I had this +1.
            //The caller will do scan from this point anyway.
            //Test and see.
            src.accept(span[1]);// + 1);
        } else {
            src.replace(span, repl);
        }
    }

    private static final String stEmpty = " ";

    private static Matcher match(final Pattern patt, final String str) {
        final Matcher matcher = patt.matcher(str);
        return (matcher.matches()) ? matcher : null;
    }

    private static int[] getSpan(final Matcher matcher, final int grp) {
        return new int[]{matcher.start(grp), matcher.end(grp)};
    }
}
