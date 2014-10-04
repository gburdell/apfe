
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class primary_literal extends Acceptor implements NonTerminal, ITokenCodes {

    public primary_literal() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(NUMBER),
new Terminal(TIME_LITERAL),
new Terminal(UNBASED_UNSIZED_LITERAL),
new Terminal(STRING_LITERAL)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public primary_literal create() {
        return new primary_literal();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


