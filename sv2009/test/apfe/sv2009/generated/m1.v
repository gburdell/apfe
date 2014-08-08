module bw_u1_ckbuf_1p5x  (clk, rclk);
        buf (clk, rclk);
endmodule
module zzadd32v (/*AUTOARG*/
   // Outputs
   z,
   // Inputs
   a, b, cin, add32
   ) ;
   parameter N = 31;
   input [N:0] a;
   wire          cout15; // carry out from lower 16 bit add
   wire          cin16; // carry in to the upper 16 bit add
   wire          cout31; // carry out from the upper 16 bit add

   assign        cin16 = (add32)? cout15: cin;

   assign      {cout15, z[15:0]} = a[15:0]+b[15:0]+ cin;
   assign      {cout31, z[N:16]} = a[N:16]+b[N:16]+ cin16;

endmodule // zzadd32v

module bw_r_irf_register(clk, wrens, save, save_addr, restore, restore_addr, wr_data0, wr_data1, wr_data2, wr_data3, rd_thread, rd_data);
    input       clk;
    input   [3:0]   wrens;
    input       save;
    input   [4:0]   save_addr;
    input       restore;
    input   [4:0]   restore_addr;
    input   [71:0]  wr_data0;
    input   [71:0]  wr_data1;
    input   [71:0]  wr_data2;
    input   [71:0]  wr_data3;
    input   [1:0]   rd_thread;
    output  [71:0]  rd_data;

reg [71:0]  window[31:0]/* synthesis syn_ramstyle = block_ram  syn_ramstyle = no_rw_check */;
reg [71:0]  reg_th0, reg_th1, reg_th2, reg_th3;

reg [4:0]   rd_addr;
reg [4:0]   wr_addr;
reg     save_d;

initial begin
  reg_th0 = 72'b0;
  reg_th1 = 72'b0;
  reg_th2 = 72'b0;
  reg_th3 = 72'b0;
end

bw_r_irf_72_4x1_mux mux4_1(
    .sel(rd_thread),
    .x0(reg_th0),
    .x1(reg_th1),
    .x2(reg_th2),
    .x3(reg_th3),
    .y(rd_data)
    );

  always @(negedge clk) begin
    rd_addr = restore_addr;
  end

  wire [71:0] restore_data = window[rd_addr];

  always @(posedge clk) begin
    wr_addr <= save_addr;
  end
  always @(posedge clk) begin
    save_d <= save;
  end

  wire [71:0] save_data;

  bw_r_irf_72_4x1_mux mux4_2(
        .sel(wr_addr[4:3]),
        .x0(reg_th0),
        .x1(reg_th1),
        .x2(reg_th2),
        .x3(reg_th3),
        .y(save_data)
        );

  always @(negedge clk) begin
    if(save_d) window[wr_addr] <= save_data;
  end

//Register implementation for 4 threads / 2 write & 1 restore port

  wire [3:0] restores = (1'b1 << rd_addr[4:3]) & {4{restore}};
  //wire [3:0] wren1s = (1'b1 << wr1_th) & {4{wren1}};
  //wire [3:0] wren2s = (1'b1 << wr2_th) & {4{wren2}};

  wire [71:0] wrdata0, wrdata1, wrdata2, wrdata3;

  bw_r_irf_72_2x1_mux mux2_5(
        .sel(restores[0]),
        .x0(wr_data0),
        .x1(restore_data),
        .y(wrdata0)
        );

  bw_r_irf_72_2x1_mux mux2_6(
        .sel(restores[1]),
        .x0(wr_data1),
        .x1(restore_data),
        .y(wrdata1)
        );

  bw_r_irf_72_2x1_mux mux2_7(
        .sel(restores[2]),
        .x0(wr_data2),
        .x1(restore_data),
        .y(wrdata2)
        );

  bw_r_irf_72_2x1_mux mux2_8(
        .sel(restores[3]),
        .x0(wr_data3),
        .x1(restore_data),
        .y(wrdata3)
        );

  //wire [3:0] wr_en = wren1s | wren2s | (restores & {4{(wr_addr[4:0] != rd_addr[4:0])}});
  wire [3:0] wr_en = wrens | (restores & {4{(wr_addr[4:0] != rd_addr[4:0])}});

  //288 Flops
  always @(posedge clk) begin
    if(wr_en[0]) reg_th0 <= wrdata0;
    if(wr_en[1]) reg_th1 <= wrdata1;
    if(wr_en[2]) reg_th2 <= wrdata2;
    if(wr_en[3]) reg_th3 <= wrdata3;
  end

endmodule

module zmuxi31d_prim (z, d0, d1, d2, s0, s1, s2);
output z;
input  d0, d1, d2, s0, s1, s2;
// for Blacktie
              
                                              
      
wire [2:0] sel = {s0,s1,s2}; // 0in one_hot
assign cwp_no_change_m = ~|(cwp_xor_m[3-1:0]);
reg z;
    always @ (s2 or d2 or s1 or d1 or s0 or d0)
        casez ({s2,d2,s1,d1,s0,d0})
            6'b0?0?10: z = 1'b1;
            6'b0?0?11: z = 1'b0;
            6'b0?100?: z = 1'b1;
            6'b0?110?: z = 1'b0;
            6'b0?1010: z = 1'b1;
            6'b0?1111: z = 1'b0;
            6'b100?0?: z = 1'b1;
            6'b110?0?: z = 1'b0;
            6'b100?10: z = 1'b1;
            6'b110?11: z = 1'b0;
            6'b10100?: z = 1'b1;
            6'b11110?: z = 1'b0;
            6'b101010: z = 1'b1;
            6'b111111: z = 1'b0;
            default: z = 1'bx;
        endcase

    m2 #(1) u2(.c(a), .d    ({a,b,c}));
    m1 #(1) u1(.c(w));
endmodule


/* -------------------------------------------------------------------------- */
//


module m1(
    idx, base, fd, sd
);

input   [6:0]   idx;
output  [26:0]  base;
output  [17:0]  fd;
output  [10:0]  sd;


reg [26:0]  base;
reg [17:0]  fd;
reg [10:0]  sd;

always @(idx)
  case (idx)
    7'h00   : begin  base = 27'h3fffffe; fd = 18'h3fff7; sd = 11'h7e8;  end
    7'h01   : begin  base = 27'h3f01fbf; fd = 18'h3f027; sd = 11'h7ba;  end
    7'h02   : begin  base = 27'h3e07e06; fd = 18'h3e0b4; sd = 11'h78d;  end
    7'h7b   : begin  base = 27'h01465fe; fd = 18'h10a4c; sd = 11'h10e;  end
    7'h7c   : begin  base = 27'h0104104; fd = 18'h10830; sd = 11'h10a;  end
    7'h7d   : begin  base = 27'h00c246d; fd = 18'h1061b; sd = 11'h108;  end
    7'h7e   : begin  base = 27'h0081020; fd = 18'h1040b; sd = 11'h104;  end
    7'h7f   : begin  base = 27'h0040404; fd = 18'h10202; sd = 11'h101;  end
  endcase

endmodule

