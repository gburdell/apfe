
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class attr_spec extends Acceptor implements NonTerminal, ITokenCodes {

    public attr_spec() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new attr_name(),
new Optional(new Sequence(new Terminal(EQ),
new constant_expression())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public attr_spec create() {
        return new attr_spec();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


