
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class class_scope extends Acceptor implements NonTerminal, ITokenCodes {

    public class_scope() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new class_type(),
new Terminal(COLON2)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public class_scope create() {
        return new class_scope();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


