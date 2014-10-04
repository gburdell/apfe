
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class state_dependent_path_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public state_dependent_path_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(IF_K),
new Terminal(LPAREN),
new module_path_expression(),
new Terminal(RPAREN),
new edge_sensitive_path_declaration()),
new Sequence(new Terminal(IF_K),
new Terminal(LPAREN),
new module_path_expression(),
new Terminal(RPAREN),
new simple_path_declaration()),
new Sequence(new Terminal(IFNONE_K),
new simple_path_declaration())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public state_dependent_path_declaration create() {
        return new state_dependent_path_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


