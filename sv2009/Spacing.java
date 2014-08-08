package apfe.sv2009.generated;

import apfe.runtime.*;
import static apfe.runtime.CharBuffer.EOF;

import apfe.runtime.CharBuffer.Marker;

public class Spacing extends Acceptor {

    public Spacing() {
    }

    @Override
    protected boolean accepti() {
        /*
        Acceptor matcher = new Repetition(new PrioritizedChoice(new PrioritizedChoice.Choices() {
            @Override
            public Acceptor getChoice(int ix) {
                Acceptor acc = null;
                switch (ix) {
                    case 0:
                        acc = new Space();
                        break;
                    case 1:
                        acc = new Comment();
                        break;
                    case 2:
                        acc = new TicLine();
                        break;
                    case 3:
                        acc = new TimeScale();
                        break;
                }
                return acc;
            }
        }), Repetition.ERepeat.eZeroOrMore);
        */
        CharBuffer cbuf = State.getTheOne().getBuf();
        Acceptor accepted = null, matcher = null;
        for (boolean stay = true; stay; /*nil*/) {
            char c = cbuf.la();
            switch (c) {
                case ' ':
                case '\t':
                case '\n':
                    cbuf.accept();  //NOTE: dont keep text
                    break;
                case '`':
                    matcher = new PrioritizedChoice(new TicLine(), new TimeScale());
                    accepted = match(matcher);
                    stay = (null != accepted);
                    break;
                case '/':
                    switch (cbuf.la(1)) {
                        case '*':
                            matcher = new ML_COMMENT();
                            break;
                        case '/':
                            matcher = new SL_COMMENT();
                            break;
                        default:
                            return true;
                    }

                    accepted = match(matcher);
                    stay = (null != accepted);
                    break;
                case EOF:
                    return true;
                default:
                    return true;
            }
        }
        return true;
    }

    @Override
    public Spacing create() {
        return new Spacing();
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
     * Memoize for all instances of Spacing.
     */
    private static final Memoize stMemo = new Memoize();
    //End memoize

}
