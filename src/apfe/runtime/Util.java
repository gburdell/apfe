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
package apfe.runtime;

import gblib.Pair;
import static gblib.Util.downCast;
import java.util.LinkedList;
import java.util.List;
import java.util.Arrays;

public class Util {

    /**
     * Convert list of Acceptors to list of T
     *
     * @param <T> return type of list.
     * @param from list of Acceptor.
     * @param to list to add to or null (to create one).
     * @return list of T.
     */
    public static <T extends Acceptor> List<T> toList(List<Acceptor> from, List<T> to) {
        if ((null == from) || from.isEmpty()) {
            return null;
        }
        if (null == to) {
            to = new LinkedList<>();
        }
        T ele;
        for (Acceptor a : from) {
            ele = downCast(a);
            to.add(ele);
        }
        return to;
    }

    public static <T extends Acceptor> List<T> toList(List<Acceptor> from) {
        return toList(from, null);
    }

    /**
     * Get only element from Repetition "ele?".
     *
     * @param <T> type of element.
     * @param rep repetition.
     * @return null (if no element) or only element. Assert that size of
     * repetition is 0 or 1 (and no more).
     */
    public static <T extends Acceptor> T getOnlyElement(Repetition rep) {
        T ele = null;
        if ((null != rep) && (0 < rep.sizeofAccepted())) {
            assert (1 == rep.sizeofAccepted());
            List<T> eles = toList(rep.getAccepted());
            ele = eles.get(0);
        }
        return ele;
    }

    /**
     * Extract Repetition from position in Sequence.
     *
     * @param <T> return type of list.
     * @param from a Repetition of Sequence.
     * @param pos extract repetition from this position (in repeated Sequence).
     * @param to list to add to or null (to create one).
     * @return list of T.
     */
    public static <T extends Acceptor> List<T> extractList(Repetition from, int pos, List<T> to) {
        Sequence seq;
        List<Acceptor> eles = from.getAccepted();
        if (null != eles) {
            if (!eles.isEmpty() && (null == to)) {
                to = new LinkedList<>();
            }
            T ele;
            for (Acceptor a : from.getAccepted()) {
                seq = downCast(a);
                ele = downCast(seq.getAccepted()[pos]);
                to.add(ele);
            }
        }
        return to;
    }

    public static <T extends Acceptor> List<T> extractList(Repetition from, int pos) {
        return extractList(from, pos, null);
    }

    /**
     * Select element from Acceptor (as base instance of actual Sequence).
     *
     * @param <T> type of selected element.
     * @param from base instance of actual Sequence.
     * @param pos position of selected element.
     * @return selected element.
     */
    public static <T extends Acceptor> T extractEle(Acceptor from, int pos) {
        final Sequence asSeq = downCast(from);
        return downCast(asSeq.getAccepted()[pos]);
    }

    public static <T extends Acceptor> T extractEle(Sequence from, int pos) {
        return downCast(from.getAccepted()[pos]);
    }

    public static String extractEleAsString(Sequence from, int pos) {
        return extractEleAsString((Acceptor) from, pos);
    }

    public static String extractEleAsString(Acceptor from, int pos) {
        Acceptor acc = extractEle(from, pos);
        return (null == acc) ? null : acc.toString();
    }

    public static String extractEleAsString(final Sequence from, int begin, int end) {
        StringBuilder buf = new StringBuilder();
        for (int i = begin; i <= end; i++) {
            buf.append(from.getText(i));
        }
        return buf.toString();
    }

    public static Pair<Boolean, Integer> asInt(final Acceptor seq, int pos, boolean useBase) {
        String s = asString(seq, pos, useBase);
        return (null == s || s.isEmpty()) ? new Pair(false, 0) : new Pair(true, Integer.parseInt(s));
    }

    public static Pair<Boolean, Integer> asInt(final Acceptor seq, int pos) {
        return asInt(seq, pos, true);
    }

    public static String asString(final Acceptor seq, int pos, boolean useBase) {
        Acceptor acc = useBase ? seq.getBaseAccepted() : seq;
        return ((Sequence) acc).getText(pos);
    }

    public static String asString(final Acceptor seq, int pos) {
        return asString(seq, pos, true);
    }

    /**
     * Process acc as: acc->getBaseAcceptor->PrioritizedChoice.accepted->Sequence(pos)
     * and return as string.
     * 
     * @param acc acceptor to process.
     * @param pos position in referenced Sequence to get as string.
     * @return String.
     */
    public static String bpsString(final Acceptor acc, int pos) {
        final PrioritizedChoice pc = (PrioritizedChoice) acc.getBaseAccepted();
        return Util.asString(pc.getAccepted(), pos, false);
    }

    private static <T extends Acceptor> StringBuilder toStringIterable(StringBuilder sb, final Iterable<T> eles, final boolean doSfx) {
        if (null != eles) {
            for (Acceptor a : eles) {
                if (null != a) {
                    String s = a.toString();
                    if ((null != s) && !s.isEmpty()) {
                        sb.append(s);
                        if (doSfx && !s.endsWith(" ")) {
                            sb.append(' ');
                        }
                    }
                }
            }
        }
        return sb;
    }

    public static <T extends Acceptor> StringBuilder toString(StringBuilder sb, List<T> eles) {
        return toStringIterable(sb, eles, true);
    }

    public static <T extends Acceptor> StringBuilder toString(final boolean doSfx, StringBuilder sb, List<T> eles) {
        return toStringIterable(sb, eles, doSfx);
    }

    public static <T extends Acceptor> StringBuilder toString(List<T> eles) {
        return toStringIterable(new StringBuilder(), eles, true);
    }

    public static StringBuilder toString(StringBuilder sb, Acceptor... eles) {
        return toStringIterable(sb, Arrays.asList(eles), true);
    }

    public static StringBuilder toString(final boolean doSfx, StringBuilder sb, Acceptor... eles) {
        return toStringIterable(sb, Arrays.asList(eles), doSfx);
    }

    public static StringBuilder toString(Acceptor... eles) {
        return toString(new StringBuilder(), eles);
    }

}
