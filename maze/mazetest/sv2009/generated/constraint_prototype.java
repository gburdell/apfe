
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class constraint_prototype extends Acceptor implements NonTerminal, ITokenCodes {

    public constraint_prototype() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new constraint_prototype_qualifier()) ,
new Optional(new Terminal(STATIC_K)) ,
new Terminal(CONSTRAINT_K),
new constraint_identifier(),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public constraint_prototype create() {
        return new constraint_prototype();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


