
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class bind_directive extends Acceptor implements NonTerminal, ITokenCodes {

    public bind_directive() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(BIND_K),
new bind_target_scope(),
new Optional(new Sequence(new Terminal(COLON),
new bind_target_instance_list())) ,
new bind_instantiation(),
new Terminal(SEMI)),
new Sequence(new Terminal(BIND_K),
new bind_target_instance(),
new bind_instantiation(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public bind_directive create() {
        return new bind_directive();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


