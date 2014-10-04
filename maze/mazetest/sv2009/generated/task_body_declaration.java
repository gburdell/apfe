
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class task_body_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public task_body_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Optional(new Alternates(new Sequence(new interface_identifier(),
new Terminal(DOT)),
new class_scope())) ,
new task_identifier(),
new Terminal(LPAREN),
new Optional(new tf_port_list()) ,
new Terminal(RPAREN),
new Terminal(SEMI),
new Repetition(new block_item_declaration()) ,
new Repetition(new statement_or_null()) ,
new Terminal(ENDTASK_K),
new Optional(new Sequence(new Terminal(COLON),
new task_identifier())) ),
new Sequence(new Optional(new Alternates(new Sequence(new interface_identifier(),
new Terminal(DOT)),
new class_scope())) ,
new task_identifier(),
new Terminal(SEMI),
new Repetition(new tf_item_declaration()) ,
new Repetition(new statement_or_null()) ,
new Terminal(ENDTASK_K),
new Optional(new Sequence(new Terminal(COLON),
new task_identifier())) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public task_body_declaration create() {
        return new task_body_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


