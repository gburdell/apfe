package apfe.laol;

import apfe.laol.generated.QMARK;
import apfe.laol.generated.Spacing;
import apfe.runtime.*;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LCIDENT extends Acceptor {

    public LCIDENT() {
    }

    @Override
    // [a-z] [a-z0-9_]* QMARK? Spacing
    protected boolean accepti() {
        Acceptor matcher = new Sequence(new CharClass(CharClass.matchRange('a', 'z')),
                new Repetition(new CharClass(CharClass.matchRange('a', 'z'),
                        CharClass.matchRange('0', '9'),
                        CharClass.matchOneOf('_')), Repetition.ERepeat.eZeroOrMore),
                new Repetition(new QMARK(), Repetition.ERepeat.eOptional),
                new Spacing());
        m_baseAccepted = match(matcher);
        boolean match = (null != m_baseAccepted);
        if (match) {
            String s = super.toString();
            Matcher m = KWRD_PATT.matcher(s);
            if (m.matches()) {
                s = m.group(1);
                match &= !KEYWORD_MAP.containsKey(s);
            }
        }
        return match;
    }

    @Override
    public LCIDENT create() {
        return new LCIDENT();
    }

    private static final Pattern KWRD_PATT = Pattern.compile("([a-z]+)\\??\\s*");
    private static final Map<String,Boolean> KEYWORD_MAP = new HashMap<>();
    private static final String KEYWORDS[] = new String[] {
      "begin", "case", "catch", "class", 
        "def", "default", "do", 
        "each", "else", "elsif", "end",
        "extends",
        "finally", "for", "if", "implements", "in", 
        "module", "mutable", "nil", 
        "private", "protected", "public",
        "require", "return", 
        "super", "this",
        "throw", "try", "unless", "when", "while"
    };
    static {
        for (String k : KEYWORDS) {
            KEYWORD_MAP.put(k, true);
        }
    }
}
