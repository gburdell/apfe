`define N 3

module m2
    #(
      parameter a b = c'( 0 ),
      parameter int d= 1
      )
    (
     input logic_t e,
     input logic_t f,
	 g.h i,
	 j.k l[` N:0],
     output logic_t [7:0][3:0][15:0] m
     );
endmodule
