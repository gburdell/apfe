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
import apfe.peg.Expression;
import apfe.peg.Grammar;
import apfe.peg.Identifier;
import apfe.peg.PegSequence;
import apfe.peg.Prefix;
import apfe.peg.Primary;
import apfe.runtime.MessageMgr;
import apfe.runtime.Util;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gburdell
 */
public class Analyze {

    public Analyze(Grammar gram) {
        m_gram = gram;
    }

    /**
     * Analyze grammar.
     *
     * @return 0 on success, else error count.
     */
    public int analyze() {
        createMap();
        checkDefined("Grammar");
        m_errCnt += MessageMgr.getErrorCnt();
        if (0 == m_errCnt) {
            reportUnused();
            checkLeftRecursive();
        }
        return m_errCnt;
    }

    /**
     * Check for left-recursion by (simply) looking for if 1st ident in any
     * expression (prioritized choice) matches the definition name. Maybe not so
     * simple, since also have to follow through every 1st to catch indirect, as
     * in: a: b b: c c: a <--- left-recursive: a->b->c->a
     */
    private void checkLeftRecursive() {
        for (String defnm : m_processed.keySet()) {
            if (EStatus.eProcessed == m_processed.get(defnm)) {
                //only work on those used
                Definition defn = m_defnByName.get(defnm);
                if (null != defn) {
                    Expression expr = defn.getExpr();
                    HopCheck hopChk = new HopCheck(defnm);
                    if (checkLeftRecursive(defnm, hopChk, expr)) {
                        continue;   //just get 1st path
                    }
                }
            }
        }
    }
    /**
     * Use to mark left-recursive paths already found.
     */
    private Map<String, Boolean> m_didLR = new HashMap<>();

    /**
     * Track Hops during left recursion check so we dont redux previous hops.
     */
    private static class HopCheck extends LinkedList<String> {

        private HopCheck(String rootNm) {
            add(rootNm);
        }

        private HopCheck(HopCheck curr) {
            super(curr);
        }

        private HopCheck(String hopNm, HopCheck curr) {
            super(curr);
            add(hopNm);
        }

        @Override
        public final boolean add(String hopNm) {
            assert !super.contains(hopNm);
            return super.add(hopNm);
        }

        @Override
        public String toString() {
            StringBuilder path = new StringBuilder();
            for (String e : this) {
                if (0 != path.length()) {
                    path.append("->");
                }
                path.append(e);
            }
            return path.toString();
        }
    }

    private static class CntType {
        public boolean notZero() {
            return 0 < (m_direct + m_indirect);
        }
        public int m_direct = 0, m_indirect = 0;
    }

    private void leftRecursionMsg(HopCheck hopChk, String from, CntType ct) {
        final int n = hopChk.size();
        final String indirect = "indirect";
        String kind;
        int cnt;
        if (2 > n) {
            kind = "direct";
            cnt = ++ct.m_direct;
        } else {
            kind = indirect;
            cnt = ++ct.m_indirect;
        }
        if (kind.equals(indirect)) {
            Util.error("LR-2", from, hopChk.toString() + "->" + from);
        } else if (2 > cnt) {
            Util.warn("LR-1", from, hopChk.toString() + "->" + from);
        }
        m_didLR.put(from, Boolean.TRUE);
    }

    private boolean checkLeftRecursive(String rootNm, String hopNm, HopCheck hopChk, CntType ct) {
        assert EStatus.eProcessed == m_processed.get(hopNm);
        Definition defn = m_defnByName.get(hopNm);
        if (null != defn) {
            Expression expr = defn.getExpr();
            //hopChk.add(hopNm);
            if (checkLeftRecursive(rootNm, hopChk, expr) && !m_didLR.containsKey(rootNm)) {
                leftRecursionMsg(hopChk, rootNm, ct);
                return true;
            }
        }
        return false;
    }

    /**
     * Check if 1st ident in expr matches defnm.
     *
     * @param defNm root definition name to check/match for recursion.
     * @param expr expression (choice) of Definition named by defnm.
     * @return true if match (left recursion); else false.
     */
    private boolean checkLeftRecursive(String defNm, HopCheck hopChk, Expression expr) {
        CntType ct = new CntType();
        for (PegSequence seq : expr.getSequences()) {
            List<Prefix> pfxs = seq.getPrefixes();
            if ((null != pfxs) && (0 < pfxs.size())) {
                //Only look at first
                Primary prim = pfxs.get(0).getSuffix().getPrimary();
                if (prim.getType() == Primary.EType.eIdentifier) {
                    Identifier id = Util.downCast(prim.getEle());
                    String hopNm = id.getId();
                    if (hopNm.equals(defNm)) {
                        leftRecursionMsg(hopChk, defNm, ct);
                        if (2 > hopChk.size()) {
                            updateDirectLR(defNm);
                        }
                    } else if (!hopChk.contains(hopNm)) {
                        //Take the hop if not done already.
                        //Start new HopChk with previous marked,
                        checkLeftRecursive(defNm, hopNm, new HopCheck(hopNm, hopChk), ct);
                    }
                } else if (prim.getType() == Primary.EType.eExpression) {
                    Expression e2 = Util.downCast(prim.getEle());
                    checkLeftRecursive(defNm, new HopCheck(hopChk), e2);
                }
            }
        }
        return ct.notZero();
    }

    /**
     * Update direct left recursion count.
     * @param nm name of non-terminal to update.
     */
    private void updateDirectLR(String nm) {
        int cnt = m_dlrByName.containsKey(nm) ? m_dlrByName.get(nm) : 0;
        m_dlrByName.put(nm, cnt+1);
    }
    
    private void reportUnused() {
        for (String nm : m_processed.keySet()) {
            if (EStatus.eDefined == m_processed.get(nm)) {
                Util.warn("LINK-3", nm);
            }
        }
    }

    private void createMap() {
        //Add in special ones
        for (String spec : new String[]{"EOL", "EOF"}) {
            m_defnByName.put(spec, null);
            m_processed.put(spec, EStatus.eDefined);
        }
        for (Definition defn : m_gram.getDefns()) {
            String defnNm = defn.getId().getId();
            if (m_defnByName.containsKey(defnNm)) {
                Util.error("DEFN-1", defnNm);
            } else {
                m_defnByName.put(defnNm, defn);
                m_processed.put(defnNm, EStatus.eDefined); //mark defined
            }
        }
    }

    private static enum EStatus {

        eDefined, eProcessed, eNotFound
    };

    private void checkDefined(String defnNm) {
        if (m_processed.containsKey(defnNm)) {
            EStatus v = m_processed.get(defnNm);
            if (EStatus.eDefined == v) {
                //mark done at beginning so we dont redux it during recursion
                m_processed.put(defnNm, EStatus.eProcessed);
                Definition defn = m_defnByName.get(defnNm);
                if (null != defn) {
                    Expression expr = defn.getExpr();
                    checkDefined(expr);
                }
            }
            //else: eProcessed || eNotFound
        } else {
            Util.error("LINK-1", defnNm);
            m_processed.put(defnNm, EStatus.eNotFound); //mark not found
        }
    }

    private void checkDefined(Expression expr) {
        for (PegSequence seq : expr.getSequences()) {
            List<Prefix> pfxs = seq.getPrefixes();
            if (null != pfxs) {
                for (Prefix pfx : pfxs) {
                    Primary prim = pfx.getSuffix().getPrimary();
                    if (prim.getType() == Primary.EType.eIdentifier) {
                        Identifier id = Util.downCast(prim.getEle());
                        checkDefined(id.getId());
                    } else if (prim.getType() == Primary.EType.eExpression) {
                        Expression e2 = Util.downCast(prim.getEle());
                        checkDefined(e2);
                    }
                }
            }
        }
    }
    private int m_errCnt = 0;
    private final Grammar m_gram;
    private Map<String, Definition> m_defnByName = new HashMap<>();
    private Map<String, EStatus> m_processed = new HashMap<>();
    /**
     * Map of direct left recursion count by name.
     * Value is number of consecutive direct left recursions.
     */
    private Map<String,Integer> m_dlrByName = new HashMap<>();
}
