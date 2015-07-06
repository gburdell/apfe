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
package v2.apfe.peg.generate;

import gblib.Util;
import java.util.BitSet;
import v2.apfe.peg.Definition;
import v2.apfe.peg.Literal;
import static gblib.Util.invariant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import v2.apfe.peg.Expression;
import v2.apfe.peg.Identifier;
import v2.apfe.peg.PegClass;
import v2.apfe.peg.PegSequence;
import v2.apfe.peg.Prefix;
import v2.apfe.peg.Primary;
import v2.apfe.peg.Primary.EType;
import v2.apfe.peg.Range;
import v2.apfe.peg.Suffix;
import v2.apfe.runtime.Char;
import v2.apfe.runtime.Repetition.ERepeat;

/**
 *
 * @author gburdell
 */
public class FirstSet {

    private FirstSet() {
        m_bitSet = new BitSet(stSize);
    }

    public String toString() {
        List<Character> chars = new LinkedList<>();
        for (int i = 0; i < stSize; i++) {
            if (m_bitSet.get(i)) {
                chars.add(new Character((char) i));
            }
        }
        return chars.toString();
    }

    private static Map<String, Definition> stDefnByName;
    private static Set<String> stProcessing = new HashSet<>();
    private final BitSet m_bitSet;

    public static void setDefinitions(final Map<String, Definition> defns) {
        invariant(null == stDefnByName);
        stDefnByName = defns;
    }

    /**
     * Get map of FirstSet by definition name.
     *
     * @param map map of Definition by name.
     * @return map of FirstSet by definition name.
     */
    public static Map<String, FirstSet> getFirstSets(final Map<String, Definition> map) {
        setDefinitions(map);
        Map<String, FirstSet> firstSets = new HashMap<>();
        for (final String key : stDefnByName.keySet()) {
            final Definition defn = stDefnByName.get(key);
            if (null != defn) {
                final FirstSet set = create(defn);
                final String dbg = set.toString();
                firstSets.put(key, set);
            }
        }
        return firstSets;
    }

    public boolean isSet(int ix) {
        return m_bitSet.get(ix);
    }

    public static FirstSet create(final Definition defn) {
        FirstSet firstSet = new FirstSet();
        update(firstSet, defn);
        return firstSet;
    }

    private void set(int ix) {
        m_bitSet.set(ix);
    }

    private static boolean update(final FirstSet set, final Literal item) {
        String str = item.toString();
        invariant(str.charAt(0) == '\'' || str.charAt(0) == '"');
        invariant(3 <= str.length());
        int c = str.charAt(1);
        if ('\\' == c) {
            c = Util.unescape(str.substring(1, 3));
        }
        set.set(c);
        return false;
    }

    private static boolean update(final FirstSet set, final PegClass item) {
        for (final Range range : item.getRanges()) {
            final Char[] rng = range.getRange();
            final int lo = rng[0].getRawChar(),
                    hi = rng[(1 < rng.length) ? 1 : 0].getRawChar();
            invariant(lo <= hi);
            for (int i = lo; i <= hi; i++) {
                set.set(i);
            }
        }
        return false;
    }

    private static boolean update(final FirstSet set, final Suffix item) {
        final ERepeat rep = item.getOpAsRep();
        final Primary primary = item.getPrimary();
        boolean doFollow = (rep != null) && (ERepeat.eOneOrMore != rep);
        doFollow |= update(set, primary);
        return doFollow;
    }

    private static boolean update(final FirstSet set, final Primary item) {
        final EType type = item.getType();
        boolean doFollow = false;
        switch (type) {
            case eClass:
                final PegClass asCls = Util.downCast(item.getEle());
                doFollow = update(set, asCls);
                break;
            case eDot:
                break;
            case eLiteral:
                final Literal asLit = Util.downCast(item.getEle());
                doFollow = update(set, asLit);
                break;
            case eExpression:
                final Expression asExpr = Util.downCast(item.getEle());
                doFollow = update(set, asExpr);
                break;
            case eIdentifier:
                final Identifier asIdent = Util.downCast(item.getEle());
                doFollow = update(set, asIdent);
                break;
        }
        return doFollow;
    }

    private static boolean update(final FirstSet set, final Expression item) {
        boolean doFollow = false;
        //We're processing alternates, so do them all.
        for (final PegSequence seq : item.getSequences()) {
            doFollow |= update(set, seq);
        }
        return doFollow;
    }

    private static boolean update(final FirstSet set, final Identifier item) {
        String nm = item.toString();
        boolean doFollow = false;
        if (stDefnByName.containsKey(nm)) {
            doFollow = update(set, stDefnByName.get(nm));
        }
        return doFollow;
    }

    private static boolean update(final FirstSet set, final Definition item) {
        boolean doFollow = false;
        if (null != item) {
            final String nm = item.getId().toString();
            if (!stProcessing.contains(nm)) {
                stProcessing.add(nm);
                final Expression expr = item.getExpr();
                if (null != expr) {
                    doFollow = update(set, expr);
                }
                stProcessing.remove(nm);
            }
        }
        return doFollow;
    }

    private static boolean update(final FirstSet set, final PegSequence item) {
        for (final Prefix prefix : item.getPrefixes()) {
            if (false == update(set, prefix)) {
                return false;
            }
        }
        return true;
    }

    private static boolean update(final FirstSet set, final Prefix item) {
        return (!item.hasOp()) && update(set, item.getSuffix());
    }

    public static final int stSize = 256;
}
