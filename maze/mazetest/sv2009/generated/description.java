
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class description extends Acceptor implements NonTerminal, ITokenCodes {

    public description() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new module_declaration(),
new udp_declaration(),
new interface_declaration(),
new program_declaration(),
new package_declaration(),
new Sequence(new Repetition(new attribute_instance()) ,
new package_item()),
new Sequence(new Repetition(new attribute_instance()) ,
new bind_directive()),
new config_declaration()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public description create() {
        return new description();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


