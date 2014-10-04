
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class jump_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public jump_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(RETURN_K),
new Optional(new expression()) ,
new Terminal(SEMI)),
new Sequence(new Terminal(BREAK_K),
new Terminal(SEMI)),
new Sequence(new Terminal(CONTINUE_K),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public jump_statement create() {
        return new jump_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


