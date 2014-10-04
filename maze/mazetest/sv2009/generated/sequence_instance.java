
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class sequence_instance extends Acceptor implements NonTerminal, ITokenCodes {

    public sequence_instance() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new ps_or_hierarchical_sequence_identifier(),
new Optional(new Sequence(new Terminal(LPAREN),
new Optional(new sequence_list_of_arguments()) ,
new Terminal(RPAREN))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public sequence_instance create() {
        return new sequence_instance();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


