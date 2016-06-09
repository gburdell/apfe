package apfe.laol;

import apfe.runtime.*;
import apfe.laol.generated.Spacing;

public class UCIDENT extends Acceptor implements IDENT.Ident {

    public UCIDENT() {
    }

    @Override
    protected boolean accepti() {
        final Marker start = CharBufState.asMe().getCurrentMark();
        Sequence matcher = new Sequence(new CharClass(CharClass.matchRange('A', 'Z')),
                new Repetition(new CharClass(CharClass.matchRange('A', 'Z'),
                        CharClass.matchRange('a', 'z'),
                        CharClass.matchRange('0', '9'),
                        CharClass.matchOneOf('_')), Repetition.ERepeat.eZeroOrMore),
                new Spacing());
        m_baseAccepted = match(matcher);
        boolean match = (null != m_baseAccepted);
        if (match) {
            m_ident = matcher.getTexts(0, 1);
        }

        return match;
    }

    @Override
    public String getIdent() {
        return m_ident;
    }
    
    private String m_ident;
    
    @Override
    public UCIDENT create() {
        return new UCIDENT();
    }

}
