
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class block_item_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public block_item_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new attribute_instance()) ,
new local_parameter_declaration(),
new Terminal(SEMI)),
new Sequence(new Repetition(new attribute_instance()) ,
new let_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new parameter_declaration(),
new Terminal(SEMI)),
new Sequence(new Repetition(new attribute_instance()) ,
new data_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new overload_declaration())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public block_item_declaration create() {
        return new block_item_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


