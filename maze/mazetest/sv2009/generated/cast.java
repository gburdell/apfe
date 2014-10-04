
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class cast extends Acceptor implements NonTerminal, ITokenCodes {

    public cast() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Alternates(new simple_type(),
new constant_primary(),
new signing(),
new Terminal(STRING_K),
new Terminal(CONST_K)),
new Terminal(SQUOTE),
new Terminal(LPAREN),
new expression(),
new Terminal(RPAREN)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public cast create() {
        return new cast();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


