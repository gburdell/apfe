`define P 45 //`define P 0
`define Q `P+1	//`define Q 1
`define R `Q-2
`define S `R \
+2 \
+3//comment

`ifdef D1
	`define N 1
`elsif D2
	`define N 2
`elsif D3
	`define N 3
`else
	`define N 4
`endif

`ifdef M1
	`ifdef M1a
		`define M `N+1
	`else
		`ifdef M1b
			`define M `N+2
		`endif
	`endif
`elsif M2
	`define M `N+3
`else
	`define M `N+4
`endif

module t2(input [`N:0] x, input [`M:0] y);
	wire [`P:0] a;
	wire [`Q:0] b;
	wire [`R:0] c;
	wire [`S:0] d;
endmodule
