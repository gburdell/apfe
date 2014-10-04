
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class assignment_operator extends Acceptor implements NonTerminal, ITokenCodes {

    public assignment_operator() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(EQ),
new Terminal(PLUS_EQ),
new Terminal(MINUS_EQ),
new Terminal(STAR_EQ),
new Terminal(DIV_EQ),
new Terminal(MOD_EQ),
new Terminal(AND_EQ),
new Terminal(OR_EQ),
new Terminal(XOR_EQ),
new Terminal(LT2_EQ),
new Terminal(GT2_EQ),
new Terminal(LT3_EQ),
new Terminal(GT3_EQ)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public assignment_operator create() {
        return new assignment_operator();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


