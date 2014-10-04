
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class pass_en_switchtype extends Acceptor implements NonTerminal, ITokenCodes {

    public pass_en_switchtype() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(TRANIF0_K),
new Terminal(TRANIF1_K),
new Terminal(RTRANIF1_K),
new Terminal(RTRANIF0_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public pass_en_switchtype create() {
        return new pass_en_switchtype();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


