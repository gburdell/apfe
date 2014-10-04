
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class dpi_task_proto extends Acceptor implements NonTerminal, ITokenCodes {

    public dpi_task_proto() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new task_prototype() ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public dpi_task_proto create() {
        return new dpi_task_proto();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


