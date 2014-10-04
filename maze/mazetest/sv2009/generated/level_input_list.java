
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class level_input_list extends Acceptor implements NonTerminal, ITokenCodes {

    public level_input_list() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new level_symbol(),
new Repetition(new level_symbol()) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public level_input_list create() {
        return new level_input_list();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


