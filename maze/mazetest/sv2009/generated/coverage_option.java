
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class coverage_option extends Acceptor implements NonTerminal, ITokenCodes {

    public coverage_option() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(IDENT),
new Terminal(DOT),
new member_identifier(),
new Terminal(EQ),
new expression()),
new Sequence(new Terminal(IDENT),
new Terminal(DOT),
new member_identifier(),
new Terminal(EQ),
new constant_expression())) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public coverage_option create() {
        return new coverage_option();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


