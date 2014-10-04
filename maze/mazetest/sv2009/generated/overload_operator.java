
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class overload_operator extends Acceptor implements NonTerminal, ITokenCodes {

    public overload_operator() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(PLUS),
new Terminal(PLUS2),
new Terminal(MINUS),
new Terminal(MINUS2),
new Terminal(STAR),
new Terminal(STAR2),
new Terminal(DIV),
new Terminal(MOD),
new Terminal(EQ2),
new Terminal(NOT_EQ),
new Terminal(LT),
new Terminal(LT_EQ),
new Terminal(GT),
new Terminal(GT_EQ),
new Terminal(EQ)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public overload_operator create() {
        return new overload_operator();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


