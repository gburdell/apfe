
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class class_constructor_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public class_constructor_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(FUNCTION_K),
new Optional(new class_scope()) ,
new Terminal(NEW_K),
new Optional(new Sequence(new Terminal(LPAREN),
new Optional(new tf_port_list()) ,
new Terminal(RPAREN))) ,
new Terminal(SEMI),
new Repetition(new block_item_declaration()) ,
new Optional(new Sequence(new Terminal(SUPER_K),
new Terminal(DOT),
new Terminal(NEW_K),
new Optional(new Sequence(new Terminal(LPAREN),
new list_of_arguments(),
new Terminal(RPAREN))) ,
new Terminal(SEMI))) ,
new Repetition(new function_statement_or_null()) ,
new Terminal(ENDFUNCTION_K),
new Optional(new Sequence(new Terminal(COLON),
new Terminal(NEW_K))) ) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public class_constructor_declaration create() {
        return new class_constructor_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


