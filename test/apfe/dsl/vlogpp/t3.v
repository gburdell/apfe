`ifdef FOO
//comment
`endif

`define P 45 //`define P 0
`define Q `P+1	//`define Q 1
`define R `Q-2
`define S `R \
+2 \
+3//comment


module t3();
	wire [`S:0] d;
	/**/
	wire [`P:0] a;
	wire [`Q:0] b;
	wire [`R:0] c;
	/**/
endmodule
