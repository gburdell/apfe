`line 1 "foo.v" 0
module g();
   dffr rstint_ff();
endmodule
/**/
`line 1 "bar.v" 0


module f();
   dffr #1  rstint_ff(.din  (rst_int_i1),
              .q    (tlu_ifu_rstint_i2),
              .clk  (clk));
endmodule
/**/
