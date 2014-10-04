
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class function_statement_or_null extends Acceptor implements NonTerminal, ITokenCodes {

    public function_statement_or_null() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new function_statement(),
new Sequence(new Repetition(new attribute_instance()) ,
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public function_statement_or_null create() {
        return new function_statement_or_null();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


