package apfe.laol;

import apfe.runtime.*;
import apfe.laol.generated.IDENT;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HereDoc extends Acceptor {

    public HereDoc() {
    }

    @Override
    protected boolean accepti() {
        Sequence matcher = new Sequence(new CharSeq("<<"),
                new IDENT(),
                new Repetition(new Sequence(new NotPredicate(new EOL()),
                        new CharClass(ICharClass.IS_ANY)), Repetition.ERepeat.eZeroOrMore),
                new EOL());
        m_baseAccepted = match(matcher);
        boolean match = (null != m_baseAccepted);
        if (match) {
            //FIXME: need to get into IDENT deeper...
            m_ident = Util.extractEleAsString(matcher, 1);
            Pattern patt = Pattern.compile("(.*\\W*)" + m_ident + "\\W*;\\s+");
            String restOfLine = Util.extractEleAsString(matcher, 2);
            Matcher rexMatcher = patt.matcher(restOfLine);
            match = rexMatcher.matches();
            if (match) {
                m_buf = new StringBuffer(rexMatcher.group(1));
            } else {
                m_buf = new StringBuffer(restOfLine + "\n");
                //for subsequent use
                patt = Pattern.compile("(\\s)*" + m_ident + "\\W*;\\s+");
            }
            while (!match) {
                matcher = new Sequence(new Repetition(new Sequence(new NotPredicate(new EOL()),
                        new CharClass(ICharClass.IS_ANY)), Repetition.ERepeat.eZeroOrMore),
                        new EOL());
                restOfLine = matcher.toString();
                rexMatcher = patt.matcher(restOfLine);
                match = rexMatcher.matches();
                if (match) {
                    restOfLine = rexMatcher.group(1);
                } else if (isEOF()) {
                    return false;
                }
                m_buf.append(restOfLine);
            }
        }

        return match;
    }

    @Override
    public HereDoc create() {
        return new HereDoc();
    }

    private StringBuffer m_buf = null;
    private String m_ident = null;

    @Override
    public String toString() {
        return m_buf.toString();
    }

}
