
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class property_case_item extends Acceptor implements NonTerminal, ITokenCodes {

    public property_case_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(DEFAULT_K),
new Optional(new Terminal(COLON)) ,
new property_statement()),
new Sequence(new expression_or_dist(),
new Repetition(new Sequence(new Terminal(COMMA),
new expression_or_dist())) ,
new Terminal(COLON),
new property_statement())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public property_case_item create() {
        return new property_case_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


