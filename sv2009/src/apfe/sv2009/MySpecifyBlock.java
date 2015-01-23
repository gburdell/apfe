package apfe.sv2009;

import java.util.List;
import java.util.LinkedList;

import apfe.runtime.*;

public class MySpecifyBlock extends Acceptor implements ITokenCodes {

    public MySpecifyBlock() {
    }

    @Override
    protected boolean accepti() {
        Acceptor matcher = new Terminal(SPECIFY_K);
        m_baseAccepted = match(matcher);
        boolean match = (null != m_baseAccepted);
        if (match) {
            //borrow from apfe.runtime.Terminal.accepti
            final ScannerState st = ScannerState.asMe();
            Token la;
            while (true) {
                la = st.la();
                if (la.getCode() == ENDSPECIFY_K || la.getCode() == EOF) {
                    break;
                }
                st.accept();
            }
            matcher = new Terminal(ENDSPECIFY_K);
            m_baseAccepted = match(matcher);
            match = (null != m_baseAccepted);
            if (match && (null != getListeners())) {
                for (Listener cb : getListeners()) {
                    cb.onAccept(m_baseAccepted);
                }
            }
        }

        return match;
    }

    @Override
    public MySpecifyBlock create() {
        return new MySpecifyBlock();
    }

    //Begin memoize
    @Override
    protected void memoize(Marker mark, Marker endMark) {
        stMemo.add(mark, this, endMark);
    }

    @Override
    protected Memoize.Data hasMemoized(Marker mark) {
        return stMemo.memoized(mark);
    }
    /**
     * Memoize for all instances of specify_block.
     */
    private static final Memoize stMemo = new Memoize();
	//End memoize

    //Begin Listener
    static //@Override
    public void addListener(final Listener listener) {
        if (null == stListeners) {
            stListeners = new LinkedList<>();
        }
        stListeners.add(listener);
    }

    @Override
    protected Iterable<Listener> getListeners() {
        return stListeners;
    }

    private static List<Listener> stListeners;
    //End Listener

}
