
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class binary_module_path_operator extends Acceptor implements NonTerminal, ITokenCodes {

    public binary_module_path_operator() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(EQ2),
new Terminal(NOT_EQ),
new Terminal(AND2),
new Terminal(OR2),
new Terminal(XOR_TILDE),
new Terminal(TILDE_XOR),
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
    public binary_module_path_operator create() {
        return new binary_module_path_operator();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


