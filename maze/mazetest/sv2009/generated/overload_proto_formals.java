
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class overload_proto_formals extends Acceptor implements NonTerminal, ITokenCodes {

    public overload_proto_formals() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new data_type(),
new Repetition(new Sequence(new Terminal(COMMA),
new data_type())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public overload_proto_formals create() {
        return new overload_proto_formals();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


