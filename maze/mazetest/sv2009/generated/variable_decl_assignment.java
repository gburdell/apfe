
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class variable_decl_assignment extends Acceptor implements NonTerminal, ITokenCodes {

    public variable_decl_assignment() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new dynamic_array_variable_identifier(),
new unsized_dimension(),
new Optional(new variable_dimension()) ,
new Optional(new Sequence(new Terminal(EQ),
new dynamic_array_new())) ),
new Sequence(new variable_identifier(),
new Optional(new variable_dimension()) ,
new Optional(new Sequence(new Terminal(EQ),
new expression())) ),
new Sequence(new class_variable_identifier(),
new Optional(new Sequence(new Terminal(EQ),
new class_new())) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public variable_decl_assignment create() {
        return new variable_decl_assignment();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


