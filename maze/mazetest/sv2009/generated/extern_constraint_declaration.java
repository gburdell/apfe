
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class extern_constraint_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public extern_constraint_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Terminal(STATIC_K)) ,
new Terminal(CONSTRAINT_K),
new class_scope(),
new constraint_identifier(),
new constraint_block()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public extern_constraint_declaration create() {
        return new extern_constraint_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


