
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class assignment_pattern_expression_type extends Acceptor implements NonTerminal, ITokenCodes {

    public assignment_pattern_expression_type() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new ps_type_identifier(),
new ps_parameter_identifier(),
new integer_atom_type(),
new type_reference()) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public assignment_pattern_expression_type create() {
        return new assignment_pattern_expression_type();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


