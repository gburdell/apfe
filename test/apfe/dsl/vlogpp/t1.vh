// A comment

`ifndef  __t1_vh__
`define  __t1_vh__

`ifndef N
`define N 23
`endif

`m1(a,b, c)
/*comment*/
`m2/*foo*/(b,//bar
d)

module t1 (input [`N-1:0] a);
endmodule

`endif	//__t1_vh__
