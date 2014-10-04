
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class class_constructor_prototype extends Acceptor implements NonTerminal, ITokenCodes {

    public class_constructor_prototype() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Sequence(new Terminal(FUNCTION_K),
new Terminal(NEW_K),
new Terminal(LPAREN),
new Optional(new tf_port_list()) ,
new Terminal(RPAREN),
new Terminal(SEMI)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public class_constructor_prototype create() {
        return new class_constructor_prototype();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


