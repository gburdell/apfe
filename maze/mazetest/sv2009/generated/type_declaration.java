
package apfe.maze.sv2009.generated ;


import apfe.maze.runtime.Graph;
import apfe.maze.runtime.*;
import apfe.maze.sv2009.*;



public  class type_declaration extends Acceptor implements NonTerminal, ITokenCodes {

    public type_declaration() {
    }

    @Override
    protected boolean acceptImpl() {
		Acceptor matcher = new Alternates(new Sequence(new Terminal(TYPEDEF_K),
new interface_instance_identifier(),
new constant_bit_select(),
new Terminal(DOT),
new type_identifier(),
new type_identifier(),
new Terminal(SEMI)),
new Sequence(new Terminal(TYPEDEF_K),
new data_type(),
new type_identifier(),
new Repetition(new variable_dimension()) ,
new Terminal(SEMI)),
new Sequence(new Terminal(TYPEDEF_K),
new Optional(new Alternates(new Terminal(ENUM_K),
new Terminal(STRUCT_K),
new Terminal(UNION_K),
new Terminal(CLASS_K))) ,
new type_identifier(),
new Terminal(SEMI))) ;
        Graph subg = matcher.accept(getSubgraphRoot());
        boolean match = (null != subg);
        if (match) {
            setSubGraph(subg);
        }

        return match;
    }
 
    @Override
    public type_declaration create() {
        return new type_declaration();
    }

    @Override
    public int getEdgeTypeId() {
        return stEdgeTypeId;
    }

    private static final int stEdgeTypeId = getNextEdgeTypeId();


}


