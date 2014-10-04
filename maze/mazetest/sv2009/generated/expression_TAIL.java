
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class expression_TAIL extends Acceptor implements NonTerminal, ITokenCodes {

    public expression_TAIL() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Optional(new Sequence(new Terminal(MATCHES_K),
new pattern())) ,
new Repetition(new Sequence(new Terminal(AND3),
new expression(),
new Optional(new Sequence(new Terminal(MATCHES_K),
new pattern())) )) ,
new Terminal(QMARK),
new Repetition(new attribute_instance()) ,
new expression(),
new Terminal(COLON),
new expression()),
new Sequence(new binary_operator(),
new Repetition(new attribute_instance()) ,
new expression()),
new Sequence(new Terminal(INSIDE_K),
new Terminal(LCURLY),
new open_range_list(),
new Terminal(RCURLY))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public expression_TAIL create() {
        return new expression_TAIL();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


