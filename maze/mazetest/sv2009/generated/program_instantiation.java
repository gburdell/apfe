
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class program_instantiation extends Acceptor implements NonTerminal, ITokenCodes {

    public program_instantiation() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new program_identifier(),
new Optional(new parameter_value_assignment()) ,
new hierarchical_instance(),
new Repetition(new Sequence(new Terminal(COMMA),
new hierarchical_instance())) ,
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public program_instantiation create() {
        return new program_instantiation();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


