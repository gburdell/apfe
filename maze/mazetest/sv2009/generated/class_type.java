
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class class_type extends Acceptor implements NonTerminal, ITokenCodes {

    public class_type() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new ps_class_identifier(),
new Optional(new parameter_value_assignment()) ,
new Repetition(new Sequence(new Terminal(COLON2),
new class_identifier(),
new Optional(new parameter_value_assignment()) )) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public class_type create() {
        return new class_type();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


