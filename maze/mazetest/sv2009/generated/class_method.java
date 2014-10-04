
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class class_method extends Acceptor implements NonTerminal, ITokenCodes {

    public class_method() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Repetition(new method_qualifier()) ,
new task_declaration()),
new Sequence(new Repetition(new method_qualifier()) ,
new function_declaration()),
new Sequence(new Repetition(new method_qualifier()) ,
new class_constructor_declaration()),
new Sequence(new Optional(new Terminal(EXTERN_K)) ,
new Repetition(new method_qualifier()) ,
new class_constructor_prototype()),
new Sequence(new Optional(new Terminal(EXTERN_K)) ,
new Repetition(new method_qualifier()) ,
new method_prototype(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public class_method create() {
        return new class_method();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


