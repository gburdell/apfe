package apfe.laol;

import apfe.laol.generated.QMARK;
import apfe.laol.generated.Spacing;
import apfe.runtime.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
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
                match &= !KEYWORD_MAP.contains(s);
            }
        }
        return match;
    }

    @Override
    public LCIDENT create() {
        return new LCIDENT();
    }

    private static final Pattern KWRD_PATT = Pattern.compile("([a-z]+)\\??\\s*");
    private static final Set<String> KEYWORD_MAP = new HashSet<>(
            Arrays.asList(
					"abstract",
                    "case", "catch", "class",
                    "def", "default", "do",
                    "each", "else", "elsif",
                    "extends",
                    "finally", "for", "if", "implements", "in",
                    "module", "new", "nil",
                    "private", "protected", "public",
                    "require", "return",
                    "super", "this",
                    "throw", "try", "unless", "until", 
                    "val", "var",
                    "when", "while"
            ));
}
