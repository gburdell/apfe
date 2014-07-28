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
package apfe.peg.generate;

/**
 *
 * @author gburdell
 */
public class GenJava extends GenBase {
    
    public static interface IGen {

        public GenJava genJava(GenJava j);
    }

    /**
     * Generate class name from id.
     *
     * @param id id to convert to class name.
     * @return class name.
     */
    public static String getClsNm(String id) {
        String clsNm;
        switch (id) {
            case "EOL":
            case "EOF":
                clsNm = id;
                break;
            default:
                clsNm = (stGenClsAsCamelCase) ? toCamelCase(id) : id;
                clsNm = stClsNmPrefix + clsNm;
        }
        return clsNm;
    }

    @Override
    public GenJava append(String s) {
        return (GenJava) super.append(s);
    }

    @Override
    public GenJava append(char c) {
        return (GenJava) super.append(c);
    }

    @Override
    public GenJava template(String tmpl, Object... args) {
        return (GenJava) super.template(tmpl, args);
    }
    
    @Override
    public GenJava templateSpecd(String tmpl, Object... args) {
        return (GenJava) super.templateSpecd(tmpl, args);
    }

    /**
     * A convenience method to chain an object which supports IGen so one could
     * do
     * <pre>
     *   GenJava j;
     *   j.append(obj).append(" suffix").append(...)</pre>
     *
     * @param g object which supports IGen.
     * @return generator contents.
     */
    public GenJava append(IGen g) {
        return g.genJava(this);
    }

    public GenJava funcCall(String fn, Iterable<? extends IGen> args) {
        append(fn).append('(');
        boolean empty = true;
        for (IGen ele : args) {
            if (!empty) {
                append(", ");
            }
            ele.genJava(this);
            empty = false;
        }
        append(")");
        return this;
    }

    /**
     * A convenience method for creating PrioritizedChoice as a case statement.
     * This would appear more efficient since objects (Acceptors) are only
     * created as they are needed (as opposed to calling new for all objects to
     * populate at call time.
     *
     * @param alts sequence of choices.
     * @return switch form of PrioritizedChoice.
     */
    public GenJava genChoiceClause(Iterable<? extends IGen> alts) {
        append("new PrioritizedChoice(")
                .append("new PrioritizedChoice.Choices() {\n@Override\n")
                .append("public Acceptor getChoice(int ix) {\n")
                .append("Acceptor acc = null;\nswitch (ix) {\n");
        int i = 0;
        for (IGen g : alts) {
            append("case " + i)
                    .append(":\nacc = ")
                    .append(g)
                    .append(" ;\nbreak;\n");
            i++;
        }
        append("}\nreturn acc;\n}\n})");
        return this;
    }
}
