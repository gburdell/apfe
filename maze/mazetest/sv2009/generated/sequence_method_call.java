
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class sequence_method_call extends Acceptor implements NonTerminal, ITokenCodes {

    public sequence_method_call() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new sequence_instance(),
new Terminal(DOT),
new method_identifier()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public sequence_method_call create() {
        return new sequence_method_call();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


