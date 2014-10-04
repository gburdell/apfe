
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class wait_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public wait_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(WAIT_ORDER_K),
new Terminal(LPAREN),
new hierarchical_identifier(),
new Repetition(new Sequence(new Terminal(COMMA),
new hierarchical_identifier())) ,
new Terminal(RPAREN),
new action_block()),
new Sequence(new Terminal(WAIT_K),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN),
new statement_or_null()),
new Sequence(new Terminal(WAIT_K),
new Terminal(FORK_K),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public wait_statement create() {
        return new wait_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


