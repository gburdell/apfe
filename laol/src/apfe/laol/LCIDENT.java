package apfe.laol;

import apfe.laol.generated.Spacing;
import apfe.runtime.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class LCIDENT extends Acceptor implements IDENT.Ident {

    public LCIDENT() {
    }

    @Override
    // [a-z] [a-z0-9_]* Spacing
    protected boolean accepti() {
        Sequence matcher = new Sequence(new CharClass(CharClass.matchRange('a', 'z')),
                new Repetition(new CharClass(CharClass.matchRange('a', 'z'),
                        CharClass.matchRange('0', '9'),
                        CharClass.matchOneOf('_')), Repetition.ERepeat.eZeroOrMore),
                new Spacing());
        m_baseAccepted = match(matcher);
        boolean match = (null != m_baseAccepted);
        if (match) {
            m_ident = matcher.getTexts(0, 1);
            match &= !KEYWORD_MAP.contains(m_ident);
        }
        return match;
    }

    private String m_ident;
    
    @Override
    public String getIdent() {
        return m_ident;
    }
    
    @Override
    public LCIDENT create() {
        return new LCIDENT();
    }

    private static final Set<String> KEYWORD_MAP = new HashSet<>(
            Arrays.asList(
                    "abstract",
                    "break",
                    "case", "catch", "class",
                    "def",
                    "else", "elsif",
                    "extends",
                    "false", "finally", "for",
                    "if", "implements", "in",
                    "module", "next", "new", "nil",
                    "private", "protected", "public",
                    "require", "return",
                    "super",
                    "this", "throw", "true", "try",
                    "unless", "until",
                    "val", "var",
                    "when", "while"
            ));
}
