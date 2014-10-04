
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class param_assignment extends Acceptor implements NonTerminal, ITokenCodes {

    public param_assignment() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new parameter_identifier(),
new Repetition(new unpacked_dimension()) ,
new Optional(new Sequence(new Terminal(EQ),
new constant_param_expression())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public param_assignment create() {
        return new param_assignment();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


