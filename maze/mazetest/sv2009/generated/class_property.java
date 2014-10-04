
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class class_property extends Acceptor implements NonTerminal, ITokenCodes {

    public class_property() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(CONST_K),
new Repetition(new class_item_qualifier()) ,
new data_type(),
new const_identifier(),
new Optional(new Sequence(new Terminal(EQ),
new constant_expression())) ,
new Terminal(SEMI)),
new Sequence(new Repetition(new property_qualifier()) ,
new data_declaration())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public class_property create() {
        return new class_property();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


