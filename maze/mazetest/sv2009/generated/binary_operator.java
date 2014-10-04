
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class binary_operator extends Acceptor implements NonTerminal, ITokenCodes {

    public binary_operator() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(LT_MINUS_GT),
new Terminal(NOT_EQ_QMARK),
new Terminal(MINUS_GT),
new Terminal(GT3),
new Terminal(LT3),
new Terminal(GT2),
new Terminal(LT2),
new Terminal(EQ3),
new Terminal(EQ2_QMARK),
new Terminal(EQ2),
new Terminal(NOT_EQ2),
new Terminal(NOT_EQ),
new Terminal(LT_EQ),
new Terminal(GT_EQ),
new Terminal(AND2),
new Terminal(OR2),
new Terminal(STAR2),
new Terminal(XOR_TILDE),
new Terminal(TILDE_XOR),
new Terminal(PLUS),
new Terminal(MINUS),
new Terminal(STAR),
new Terminal(DIV),
new Terminal(MOD),
new Terminal(LT),
new Terminal(GT),
new Terminal(AND),
new Terminal(OR),
new Terminal(XOR)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public binary_operator create() {
        return new binary_operator();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


