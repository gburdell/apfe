
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class integer_atom_type extends Acceptor implements NonTerminal, ITokenCodes {

    public integer_atom_type() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Terminal(BYTE_K),
new Terminal(SHORTINT_K),
new Terminal(INT_K),
new Terminal(LONGINT_K),
new Terminal(INTEGER_K),
new Terminal(TIME_K)) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public integer_atom_type create() {
        return new integer_atom_type();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


