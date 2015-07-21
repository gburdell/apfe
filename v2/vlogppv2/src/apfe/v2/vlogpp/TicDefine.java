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

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author gburdell
 */
class TicDefine {

    static final Pattern stPatt1 = Pattern.compile("[ \t]*(`define)[ \t]+([_a-zA-Z][_a-zA-Z0-9]*)(.*)(\\s*)");

    static void parse(final SourceFile src, Matcher matcher) {
        int n = matcher.start(1);
        src.accept(n);
        final int[] started = src.getStartMark();
        final String macroName = matcher.group(2);
        int m = matcher.start(3);
        src.accept(m - n);
        //we are just past macroNm
        //The left parenthesis shall follow the text macro name immediately
        //The `define macro text can also include `", `\`", and ``
        if ('(' == src.la()) {

        }
    }
    
    /**
     * Split (...) contents at comma.
     * Return list of formal arguments.
     * @param src character source starting at '('.
     * @return list of formal arguments.
     */
    private static List<String> formalArguments(final SourceFile src) {
        List<String> formalArgs = new LinkedList<>();
        return formalArgs;
    }
}
