package apfe.laol;

import apfe.runtime.*;

public class IDENT extends Acceptor {

    static interface Ident {
        public String getIdent();
    }
    
    public String getIdent() {
        return ((Ident)((PrioritizedChoice)m_baseAccepted).getAccepted()).getIdent();
    }
    
    public IDENT() {
    }

    @Override
    protected boolean accepti() {
        Acceptor matcher = new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor acc = null;
                switch (ix) {
                    case 0:
                        acc = new LCIDENT();
                        break;
                    case 1:
                        acc = new UCIDENT();
                        break;
                }
                return acc;
            }
        });
        m_baseAccepted = match(matcher);
        boolean match = (null != m_baseAccepted);

        return match;
    }

    @Override
    public IDENT create() {
        return new IDENT();
    }

}
