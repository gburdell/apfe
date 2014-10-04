
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class enum_base_type extends Acceptor implements NonTerminal, ITokenCodes {

    public enum_base_type() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new integer_atom_type(),
new Optional(new signing()) ),
new Sequence(new integer_vector_type(),
new Optional(new signing()) ,
new Optional(new packed_dimension()) ),
new Sequence(new type_identifier(),
new Optional(new packed_dimension()) )) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public enum_base_type create() {
        return new enum_base_type();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


