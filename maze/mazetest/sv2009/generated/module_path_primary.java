
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class module_path_primary extends Acceptor implements NonTerminal, ITokenCodes {

    public module_path_primary() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(LPAREN),
new module_path_mintypmax_expression(),
new Terminal(RPAREN)),
new module_path_multiple_concatenation(),
new module_path_concatenation(),
new subroutine_call(),
new Terminal(NUMBER),
new identifier()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public module_path_primary create() {
        return new module_path_primary();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


