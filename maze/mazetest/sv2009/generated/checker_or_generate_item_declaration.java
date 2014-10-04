
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class checker_or_generate_item_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public checker_or_generate_item_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Optional(new Terminal(RAND_K)) ,
new data_declaration()),
new function_declaration(),
new assertion_item_declaration(),
new covergroup_declaration(),
new overload_declaration(),
new genvar_declaration(),
new clocking_declaration(),
new Sequence(new Terminal(DEFAULT_K),
new Terminal(CLOCKING_K),
new clocking_identifier(),
new Terminal(SEMI)),
new Sequence(new Terminal(DEFAULT_K),
new Terminal(DISABLE_K),
new Terminal(IFF_K),
new expression_or_dist(),
new Terminal(SEMI)),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public checker_or_generate_item_declaration create() {
        return new checker_or_generate_item_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


