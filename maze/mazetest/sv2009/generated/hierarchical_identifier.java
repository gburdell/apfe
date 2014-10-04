
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class hierarchical_identifier extends Acceptor implements NonTerminal, ITokenCodes {

    public hierarchical_identifier() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Sequence(new Terminal(DS_ROOT_K),
new Terminal(DOT))) ,
new Repetition(new Sequence(new identifier(),
new constant_bit_select(),
new Terminal(DOT))) ,
new identifier()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public hierarchical_identifier create() {
        return new hierarchical_identifier();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


