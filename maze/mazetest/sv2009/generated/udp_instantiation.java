
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class udp_instantiation extends Acceptor implements NonTerminal, ITokenCodes {

    public udp_instantiation() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new udp_identifier(),
new Optional(new drive_strength()) ,
new Optional(new delay2()) ,
new udp_instance(),
new Repetition(new Sequence(new Terminal(COMMA),
new udp_instance())) ,
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public udp_instantiation create() {
        return new udp_instantiation();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


