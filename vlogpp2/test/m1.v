`define PASTE /``/this is a comment
`PASTE
`include "m1.vh"
`include "m1b.vh"
`timescale  1ps/1ps
`ifndef N
`define N 4
`endif
`define N 5
`undef M


module m1 (input [`N-1:0] d);
`PASTE
reg a[3:0];
reg b[3:0][4:0];
endmodule
