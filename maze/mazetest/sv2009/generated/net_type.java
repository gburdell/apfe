
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class net_type extends Acceptor implements NonTerminal, ITokenCodes {

    public net_type() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(SUPPLY0_K),
new Terminal(SUPPLY1_K),
new Terminal(TRI_K),
new Terminal(TRIAND_K),
new Terminal(TRIOR_K),
new Terminal(TRIREG_K),
new Terminal(TRI0_K),
new Terminal(TRI1_K),
new Terminal(UWIRE_K),
new Terminal(WIRE_K),
new Terminal(WAND_K),
new Terminal(WOR_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public net_type create() {
        return new net_type();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


