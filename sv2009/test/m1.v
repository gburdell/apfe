module bar(
input wire clk,
output wire z,
output reg x
);
assign snext.ena  = sprev.ena;
assign sense[CHAIN_LENGTH].ena  = sprev.ena;
function [49:0] fn1;
input [13:0] x;         
input [13:0] y;         
input z;
endfunction

endmodule
module foo();
output wire ww, ww2;
integer fives[4] = '{ 5, 10, 15, 20 };
integer threes[4] = ' { 3, 6, 9, 12 };
endmodule

