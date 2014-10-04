
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class assume_property_statement extends Acceptor implements NonTerminal, ITokenCodes {

    public assume_property_statement() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(ASSUME_K),
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
    public assume_property_statement create() {
        return new assume_property_statement();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


