
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class let_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public let_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(LET_K),
new let_identifier(),
new Optional(new Sequence(new Terminal(LPAREN),
new Optional(new let_port_list()) ,
new Terminal(RPAREN))) ,
new Terminal(EQ),
new expression(),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public let_declaration create() {
        return new let_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


