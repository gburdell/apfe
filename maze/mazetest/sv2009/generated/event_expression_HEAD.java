
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class event_expression_HEAD extends Acceptor implements NonTerminal, ITokenCodes {

    public event_expression_HEAD() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(LPAREN),
new event_expression(),
new Terminal(RPAREN)),
new Sequence(new Optional(new edge_identifier()) ,
new expression(),
new Optional(new Sequence(new Terminal(IFF_K),
new expression())) ),
new Sequence(new sequence_instance(),
new Optional(new Sequence(new Terminal(IFF_K),
new expression())) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public event_expression_HEAD create() {
        return new event_expression_HEAD();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


