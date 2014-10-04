
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class let_expression extends Acceptor implements NonTerminal, ITokenCodes {

    public let_expression() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new package_scope()) ,
new let_identifier(),
new Optional(new Sequence(new Terminal(LPAREN),
new Optional(new let_list_of_arguments()) ,
new Terminal(RPAREN))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public let_expression create() {
        return new let_expression();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


