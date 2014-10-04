
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class dpi_spec_string extends Acceptor implements NonTerminal, ITokenCodes {

    public dpi_spec_string() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Terminal(STRING_LITERAL) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public dpi_spec_string create() {
        return new dpi_spec_string();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


