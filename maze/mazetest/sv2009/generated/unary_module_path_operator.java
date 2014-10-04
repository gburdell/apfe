
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class unary_module_path_operator extends Acceptor implements NonTerminal, ITokenCodes {

    public unary_module_path_operator() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(TILDE_AND),
new Terminal(TILDE_OR),
new Terminal(TILDE_XOR),
new Terminal(XOR_TILDE),
new Terminal(NOT),
new Terminal(TILDE),
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
    public unary_module_path_operator create() {
        return new unary_module_path_operator();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


