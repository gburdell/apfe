
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class net_decl_assignment extends Acceptor implements NonTerminal, ITokenCodes {

    public net_decl_assignment() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new net_identifier(),
new Repetition(new unpacked_dimension()) ,
new Optional(new Sequence(new Terminal(EQ),
new expression())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public net_decl_assignment create() {
        return new net_decl_assignment();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


