
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class class_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public class_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Optional(new Terminal(VIRTUAL_K)) ,
new Terminal(CLASS_K),
new Optional(new lifetime()) ,
new class_identifier(),
new Optional(new parameter_port_list()) ,
new Optional(new Sequence(new Terminal(EXTENDS_K),
new class_type(),
new Optional(new Sequence(new Terminal(LPAREN),
new list_of_arguments(),
new Terminal(RPAREN))) )) ,
new Terminal(SEMI),
new Repetition(new class_item()) ,
new Terminal(ENDCLASS_K),
new Optional(new Sequence(new Terminal(COLON),
new class_identifier())) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public class_declaration create() {
        return new class_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


