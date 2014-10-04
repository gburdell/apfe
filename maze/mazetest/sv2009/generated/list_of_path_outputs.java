
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class list_of_path_outputs extends Acceptor implements NonTerminal, ITokenCodes {

    public list_of_path_outputs() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new specify_output_terminal_descriptor(),
new Repetition(new Sequence(new Terminal(COMMA),
new specify_output_terminal_descriptor())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public list_of_path_outputs create() {
        return new list_of_path_outputs();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


