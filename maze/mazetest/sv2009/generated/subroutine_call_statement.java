
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class subroutine_call_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public subroutine_call_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(VOID_K),
new Terminal(SQUOTE),
new Terminal(LPAREN),
new subroutine_call(),
new Terminal(RPAREN),
new Terminal(SEMI)),
new Sequence(new subroutine_call(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public subroutine_call_statement create() {
        return new subroutine_call_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


