
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class specify_input_terminal_descriptor extends Acceptor implements NonTerminal, ITokenCodes {

    public specify_input_terminal_descriptor() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new input_identifier(),
new Optional(new Sequence(new Terminal(LBRACK),
new constant_range_expression(),
new Terminal(RBRACK))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public specify_input_terminal_descriptor create() {
        return new specify_input_terminal_descriptor();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


