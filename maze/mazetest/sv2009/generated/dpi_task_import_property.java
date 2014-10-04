
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class dpi_task_import_property extends Acceptor implements NonTerminal, ITokenCodes {

    public dpi_task_import_property() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Terminal(CONTEXT_K) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public dpi_task_import_property create() {
        return new dpi_task_import_property();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


