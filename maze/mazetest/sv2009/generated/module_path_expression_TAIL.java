
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class module_path_expression_TAIL extends Acceptor implements NonTerminal, ITokenCodes {

    public module_path_expression_TAIL() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(QMARK),
new Repetition(new attribute_instance()) ,
new module_path_expression(),
new Terminal(COLON),
new module_path_expression()),
new Sequence(new binary_module_path_operator(),
new Repetition(new attribute_instance()) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public module_path_expression_TAIL create() {
        return new module_path_expression_TAIL();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


