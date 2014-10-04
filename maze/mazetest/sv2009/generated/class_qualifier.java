
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class class_qualifier extends Acceptor implements NonTerminal, ITokenCodes {

    public class_qualifier() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Sequence(new Terminal(IDENT),
new Terminal(COLON2))) ,
new Optional(new Alternates(new Sequence(new implicit_class_handle(),
new Terminal(DOT)),
new class_scope())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public class_qualifier create() {
        return new class_qualifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


