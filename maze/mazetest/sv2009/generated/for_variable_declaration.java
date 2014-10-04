
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class for_variable_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public for_variable_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new data_type(),
new variable_identifier(),
new Terminal(EQ),
new expression(),
new Repetition(new Sequence(new Terminal(COMMA),
new variable_identifier(),
new Terminal(EQ),
new expression())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public for_variable_declaration create() {
        return new for_variable_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


