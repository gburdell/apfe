
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class assert_property_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public assert_property_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(ASSERT_K),
new Terminal(PROPERTY_K),
new Terminal(LPAREN),
new property_spec(),
new Terminal(RPAREN),
new action_block()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public assert_property_statement create() {
        return new assert_property_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


