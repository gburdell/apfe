
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class net_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public net_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new net_type(),
new Optional(new Alternates(new drive_strength(),
new charge_strength())) ,
new Optional(new Alternates(new Terminal(VECTORED_K),
new Terminal(SCALARED_K))) ,
new Alternates(new list_of_net_decl_assignments(),
new Sequence(new data_type_or_implicit(),
new Optional(new delay3()) ,
new list_of_net_decl_assignments())),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public net_declaration create() {
        return new net_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


