
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class case_generate_item extends Acceptor implements NonTerminal, ITokenCodes {

    public case_generate_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(DEFAULT_K),
new Optional(new Terminal(COLON)) ,
new generate_block()),
new Sequence(new constant_expression(),
new Repetition(new Sequence(new Terminal(COMMA),
new constant_expression())) ,
new Terminal(COLON),
new generate_block())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public case_generate_item create() {
        return new case_generate_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


