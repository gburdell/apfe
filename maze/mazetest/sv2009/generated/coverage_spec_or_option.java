
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class coverage_spec_or_option extends Acceptor implements NonTerminal, ITokenCodes {

    public coverage_spec_or_option() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new attribute_instance()) ,
new coverage_option(),
new Terminal(SEMI)),
new Sequence(new Repetition(new attribute_instance()) ,
new coverage_spec())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public coverage_spec_or_option create() {
        return new coverage_spec_or_option();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}

