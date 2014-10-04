
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class subroutine_call extends Acceptor implements NonTerminal, ITokenCodes {

    public subroutine_call() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Alternates(new Sequence(new Optional(new Alternates(new package_scope(),
new class_qualifier())) ,
new hierarchical_identifier(),
new select()),
new implicit_class_handle()),
new Terminal(DOT),
new method_call_body()),
new system_tf_call(),
new tf_call(),
new Sequence(new Optional(new std_colon2()) ,
new randomize_call())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public subroutine_call create() {
        return new subroutine_call();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


