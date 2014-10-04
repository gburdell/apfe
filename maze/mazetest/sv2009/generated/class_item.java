
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class class_item extends Acceptor implements NonTerminal, ITokenCodes {

    public class_item() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new attribute_instance()) ,
new class_property()),
new Sequence(new Repetition(new attribute_instance()) ,
new class_method()),
new Sequence(new Repetition(new attribute_instance()) ,
new class_constraint()),
new Sequence(new Repetition(new attribute_instance()) ,
new class_declaration()),
new Sequence(new Repetition(new attribute_instance()) ,
new covergroup_declaration()),
new Sequence(new local_parameter_declaration(),
new Terminal(SEMI)),
new Sequence(new parameter_declaration(),
new Terminal(SEMI)),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public class_item create() {
        return new class_item();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


