`line 1 "small.sv" 0
/**********************************************************************
 * Definition of a small testcase for the ATM testbench
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/


`timescale 1ns/1ns

// Define this so Utopia won't include Utopia methods
                 
                                                  
                                                 

parameter NumRx = 4;
parameter NumTx = 4;

`line 1 "utopia.sv" 1
/**********************************************************************
 * Utopia ATM interface, modeled as a SystemVerilog interface
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *   1.10 21 Jul 2004 -- corrected errata as printed in the book
 *                       "SystemVerilog for Design" (first edition) and
 *                       to bring the example into conformance with the
 *                       final Accellera SystemVerilog 3.1a standard
 *                       (for a description of changes, see the file
 *                       "errata_SV-Design-book_26-Jul-2004.txt")
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

`line 1 "definitions.sv" 1
/**********************************************************************
 * External typdefs included by most of the Utopia ATM model files
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

                         
                         

/*
  Cell Formats
*/
/* UNI Cell Format */
typedef struct packed {
  bit        [3:0]  GFC;
  bit        [7:0]  VPI;
  bit        [15:0] VCI;
  bit               CLP;
  bit        [2:0]  PT;
  bit        [7:0]  HEC;
  bit [0:47] [7:0]  Payload;
} uniType;

/* NNI Cell Format */
typedef struct packed {
  bit        [11:0] VPI;
  bit        [15:0] VCI;
  bit               CLP;
  bit        [2:0]  PT;
  bit        [7:0]  HEC;
  bit [0:47] [7:0]  Payload;
} nniType;

/* Test View Cell Format (Payload Section) */
typedef struct packed {
  bit [0:4]  [7:0] Header;
  bit [0:3]  [7:0] PortID;
  bit [0:3]  [7:0] CellID;
  bit [0:39] [7:0] Padding;
} tstType;

/*
  Union of UNI / NNI / Test View / ByteStream
*/
typedef union packed {
  uniType uni;
  nniType nni;
  tstType tst;
  bit [0:52] [7:0] Mem;
} ATMCellType;

/*
  Cell Rewriting and Forwarding Configuration
*/
typedef struct packed {
  bit [ 4  -1:0] FWD;
  bit [11:0] VPI;
} CellCfgType;

       // _INCL_DEFINITIONS

`line 67 "utopia.sv" 2

interface Utopia;
  parameter int IfWidth = 8;

  logic [IfWidth-1:0] data;
  bit clk_in, clk_out;
  bit soc, en, clav, valid, ready, reset, selected;

  ATMCellType ATMcell;  // union of structures for ATM cells

  modport TopReceive (
    input  data, soc, clav, 
    output clk_in, reset, ready, clk_out, en, ATMcell, valid );

  modport TopTransmit (
    input  clav, 
    inout  selected,
    output clk_in, clk_out, ATMcell, data, soc, en, valid, reset, ready );

  modport CoreReceive (
    input  clk_in, data, soc, clav, ready, reset,
    output clk_out, en, ATMcell, valid );

  modport CoreTransmit (
    input  clk_in, clav, ATMcell, valid, reset,
    output clk_out, data, soc, en, ready );

   clocking cbr @(negedge clk_out);
      input clk_in, clk_out, ATMcell, valid, reset, en, ready;
      output data, soc, clav;
   endclocking : cbr
   modport TB_Rx (clocking cbr);

   clocking cbt @(negedge clk_out);
      input  clk_out, clk_in, ATMcell, soc, en, valid, reset, data, ready;
      output clav;
   endclocking : cbt
   modport TB_Tx (clocking cbt);

endinterface

typedef virtual Utopia vUtopia;
typedef virtual Utopia.TB_Rx vUtopiaRx;
typedef virtual Utopia.TB_Tx vUtopiaTx;

`line 31 "small.sv" 2

module top;
  logic rst;
  logic clk;

  // System Clock and Reset
  initial begin
    #0 rst = 0; clk = 0;
    #5 rst = 1;
    #5 clk = 1;
    #5 rst = 0; clk = 0;
    forever begin
      #5 clk = 1;
      #5 clk = 0;
    end
  end

   Utopia Rx[0:NumRx-1]();   // NumRx x Level 1 Utopia Rx Interfaces
   Utopia Tx[0:NumTx-1]();   // NumTx x Level 1 Utopia Tx Interfaces

   testp t1();
endmodule : top




program automatic testp;

`line 1 "environment.sv" 1
/**********************************************************************
 * Definition of the environment class for the ATM testbench
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/


                       
                       


`line 1 "generator.sv" 1
/**********************************************************************
 * Definition of the generator class for the ATM testbench
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/

                     
                      

`line 1 "atm_cell.sv" 1
/**********************************************************************
 * Definition of an ATM cell
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/

                    
                    

`line 1 "definitions.sv" 1
/**********************************************************************
 * External typdefs included by most of the Utopia ATM model files
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

       // _INCL_DEFINITIONS

`line 23 "atm_cell.sv" 2

virtual class BaseTr;
  static int count;  // Number of instance created
  int id;            // Unique transaction id

  function new();
    id = count++;
  endfunction

  // "pure" methods supported in VCS 2008.03 and later
  pure virtual function bit compare(input BaseTr to);
  pure virtual function BaseTr copy(input BaseTr to=null);
  pure virtual function void display(input string prefix="");
endclass // BaseTr

typedef class NNI_cell;


/////////////////////////////////////////////////////////////////////////////
// UNI Cell Format
/////////////////////////////////////////////////////////////////////////////
class UNI_cell extends BaseTr;
   // Physical fields
   rand bit        [3:0]  GFC;
   rand bit        [7:0]  VPI;
   rand bit        [15:0] VCI;
   rand bit               CLP;
   rand bit        [2:0]  PT;
        bit        [7:0]  HEC;
   rand bit [0:47] [7:0]  Payload;

   // Meta-data fields
   static bit [7:0] syndrome[0:255];
   static bit syndrome_not_generated = 1;

   extern function new();
   extern function void post_randomize();
   extern virtual function bit compare(input BaseTr to);
   extern virtual function void display(input string prefix="");
   extern virtual function void copy_data(input UNI_cell copy);
   extern virtual function BaseTr copy(input BaseTr to=null);
   extern virtual function void pack(output ATMCellType to);
   extern virtual function void unpack(input ATMCellType from);
   extern function NNI_cell to_NNI();
   extern function void generate_syndrome();
   extern function bit [7:0] hec (bit [31:0] hdr);
endclass : UNI_cell


//-----------------------------------------------------------------------------
function UNI_cell::new();
   if (syndrome_not_generated)
     generate_syndrome();
endfunction : new


//-----------------------------------------------------------------------------
// Compute the HEC value after all other data has been chosen
function void UNI_cell::post_randomize();
   HEC = hec({GFC, VPI, VCI, CLP, PT});
endfunction : post_randomize



//-----------------------------------------------------------------------------

function bit UNI_cell::compare(input BaseTr to);
   UNI_cell other;
   $cast(other, to);
   if (this.GFC != other.GFC)         return 0;
   if (this.VPI != other.VPI)         return 0;
   if (this.VCI != other.VCI)         return 0;
   if (this.CLP != other.CLP)         return 0;
   if (this.PT  != other.PT)           return 0;
   if (this.HEC != other.HEC)         return 0;
   if (this.Payload != other.Payload) return 0;
   return 1;
endfunction : compare


function void UNI_cell::display(input string prefix);
   ATMCellType p;

   $display("%sUNI id:%0d GFC=%x, VPI=%x, VCI=%x, CLP=%b, PT=%x, HEC=%x, Payload[0]=%x",
	    prefix, id, GFC, VPI, VCI, CLP, PT, HEC, Payload[0]);
   this.pack(p);
   $write("%s", prefix);
   foreach (p.Mem[i]) $write("%x ", p.Mem[i]); $display;
   //$write("%sUNI Payload=%x %x %x %x %x %x ...",
   //	  prefix, Payload[0], Payload[1], Payload[2], Payload[3], Payload[4], Payload[5]);
   //foreach(Payload[i]) $write(" %x", Payload[i]);
   $display;
endfunction : display


function void UNI_cell::copy_data(input UNI_cell copy);
   copy.GFC     = this.GFC;
   copy.VPI     = this.VPI;
   copy.VCI     = this.VCI;
   copy.CLP     = this.CLP;
   copy.PT      = this.PT;
   copy.HEC     = this.HEC;
   copy.Payload = this.Payload;
endfunction : copy_data


function BaseTr UNI_cell::copy(input BaseTr to);
   UNI_cell dst;
   if (to == null) dst = new();
   else            $cast(dst, to);
   copy_data(dst);
   return dst;
endfunction : copy


function void UNI_cell::pack(output ATMCellType to);
   to.uni.GFC     = this.GFC;
   to.uni.VPI     = this.VPI;
   to.uni.VCI     = this.VCI;
   to.uni.CLP     = this.CLP;
   to.uni.PT      = this.PT;
   to.uni.HEC     = this.HEC;
   to.uni.Payload = this.Payload;
   //$write("Packed: "); foreach (to.Mem[i]) $write("%x ", to.Mem[i]); $display;
endfunction : pack


function void UNI_cell::unpack(input ATMCellType from);
   this.GFC     = from.uni.GFC;
   this.VPI     = from.uni.VPI;
   this.VCI     = from.uni.VCI;
   this.CLP     = from.uni.CLP;
   this.PT      = from.uni.PT;
   this.HEC     = from.uni.HEC;
   this.Payload = from.uni.Payload;
endfunction : unpack


//---------------------------------------------------------------------------
// Generate a NNI cell from an UNI cell - used in scoreboard
function NNI_cell UNI_cell::to_NNI();
   NNI_cell copy;
   copy = new();
   copy.VPI     = this.VPI;   // NNI has wider VPI
   copy.VCI     = this.VCI;
   copy.CLP     = this.CLP;
   copy.PT      = this.PT;
   copy.HEC     = this.HEC;
   copy.Payload = this.Payload;
   return copy;
endfunction : to_NNI


//---------------------------------------------------------------------------
// Generate the syndome array, used to compute HEC
function void UNI_cell::generate_syndrome();
   bit [7:0] sndrm;
   for (int i = 0; i < 256; i = i + 1 ) begin
      sndrm = i;
      repeat (8) begin
         if (sndrm[7] === 1'b1)
           sndrm = (sndrm << 1) ^ 8'h07;
         else
           sndrm = sndrm << 1;
      end
      syndrome[i] = sndrm;
   end
   syndrome_not_generated = 0;
endfunction : generate_syndrome

//---------------------------------------------------------------------------
// Function to compute the HEC value
function bit [7:0] UNI_cell::hec (bit [31:0] hdr);
   hec = 8'h00;
   repeat (4) begin
      hec = syndrome[hec ^ hdr[31:24]];
      hdr = hdr << 8;
   end
   hec = hec ^ 8'h55;
endfunction : hec




/////////////////////////////////////////////////////////////////////////////
// NNI Cell Format
/////////////////////////////////////////////////////////////////////////////
class NNI_cell extends BaseTr;
   // Physical fields
   rand bit        [11:0] VPI;
   rand bit        [15:0] VCI;
   rand bit               CLP;
   rand bit        [2:0]  PT;
        bit        [7:0]  HEC;
   rand bit [0:47] [7:0]  Payload;

   // Meta-data fields
   static bit [7:0] syndrome[0:255];
   static bit syndrome_not_generated = 1;

   extern function new();
   extern function void post_randomize();
   extern virtual function bit compare(input BaseTr to);
   extern virtual function void display(input string prefix="");
   extern virtual function void copy_data(input NNI_cell copy);
   extern virtual function BaseTr copy(input BaseTr to=null);
   extern virtual function void pack(output ATMCellType to);
   extern virtual function void unpack(input ATMCellType from);
   extern function void generate_syndrome();
   extern function bit [7:0] hec (bit [31:0] hdr);
endclass : NNI_cell


function NNI_cell::new();
   if (syndrome_not_generated)
     generate_syndrome();
endfunction : new


//-----------------------------------------------------------------------------
// Compute the HEC value after all other data has been chosen
function void NNI_cell::post_randomize();
   HEC = hec({VPI, VCI, CLP, PT});
endfunction : post_randomize


function bit NNI_cell::compare(input BaseTr to);
   NNI_cell other;
   $cast(other, to);
   if (this.VPI != other.VPI)         return 0;
   if (this.VCI != other.VCI)         return 0;
   if (this.CLP != other.CLP)         return 0;
   if (this.PT  != other.PT)          return 0;
   if (this.HEC != other.HEC)         return 0;
   if (this.Payload != other.Payload) return 0;
   return 1;
endfunction : compare


function void NNI_cell::display(input string prefix);
   ATMCellType p;

   $display("%sNNI id:%0d VPI=%x, VCI=%x, CLP=%b, PT=%x, HEC=%x, Payload[0]=%x",
	    prefix, id, VPI, VCI, CLP, PT, HEC, Payload[0]);
   this.pack(p);
   $write("%s", prefix);
   foreach (p.Mem[i]) $write("%x ", p.Mem[i]); $display;
   //$write("%sUNI Payload=%x %x %x %x %x %x ...",
   $display;
endfunction : display

function void NNI_cell::copy_data(input NNI_cell copy);
   copy.VPI     = this.VPI;
   copy.VCI     = this.VCI;
   copy.CLP     = this.CLP;
   copy.PT      = this.PT;
   copy.HEC     = this.HEC;
   copy.Payload = this.Payload;
endfunction : copy_data

function BaseTr NNI_cell::copy(input BaseTr to);
   NNI_cell dst;
   if (to == null) dst = new();
   else            $cast(dst, to);
   copy_data(dst);
   return dst;
endfunction : copy

function void NNI_cell::pack(output ATMCellType to);
   to.nni.VPI     = this.VPI;
   to.nni.VCI     = this.VCI;
   to.nni.CLP     = this.CLP;
   to.nni.PT      = this.PT;
   to.nni.HEC     = this.HEC;
   to.nni.Payload = this.Payload;
endfunction : pack

function void NNI_cell::unpack(input ATMCellType from);
   this.VPI     = from.nni.VPI;
   this.VCI     = from.nni.VCI;
   this.CLP     = from.nni.CLP;
   this.PT      = from.nni.PT;
   this.HEC     = from.nni.HEC;
   this.Payload = from.nni.Payload;
endfunction : unpack

//---------------------------------------------------------------------------
// Generate the syndome array, used to compute HEC
function void NNI_cell::generate_syndrome();
   bit [7:0] sndrm;
   for (int i = 0; i < 256; i = i + 1 ) begin
      sndrm = i;
      repeat (8) begin
         if (sndrm[7] === 1'b1)
           sndrm = (sndrm << 1) ^ 8'h07;
         else
           sndrm = sndrm << 1;
      end
      syndrome[i] = sndrm;
   end
   syndrome_not_generated = 0;
endfunction : generate_syndrome

//---------------------------------------------------------------------------
// Function to compute the HEC value
function bit [7:0] NNI_cell::hec (bit [31:0] hdr);
   hec = 8'h00;
   repeat (4) begin
      hec = syndrome[hec ^ hdr[31:24]];
      hdr = hdr << 8;
   end
   hec = hec ^ 8'h55;
endfunction : hec


       // ATM_CELL__SV

`line 23 "generator.sv" 2


/////////////////////////////////////////////////////////////////////////////
class UNI_generator;

   UNI_cell blueprint;	// Blueprint for generator
   mailbox  gen2drv;	// Mailbox to driver for cells
   event    drv2gen;	// Event from driver when done with cell
   int      nCells;	// Number of cells for this generator to create
   int	    PortID;	// Which Rx port are we generating?
   
   function new(input mailbox gen2drv,
		input event drv2gen,
		input int nCells,
		input int PortID);
      this.gen2drv = gen2drv;
      this.drv2gen = drv2gen;
      this.nCells  = nCells;
      this.PortID  = PortID;
      blueprint = new();
   endfunction : new

   task run();
      UNI_cell c;
      repeat (nCells) begin
	 assert(blueprint.randomize());
	 $cast(c, blueprint.copy());
	 c.display($sformatf("@%0t: Gen%0d: ", $time, PortID));
	 gen2drv.put(c);
	 @drv2gen;		// Wait for driver to finish with it
      end
   endtask : run

endclass : UNI_generator



/////////////////////////////////////////////////////////////////////////////
class NNI_generator;

   NNI_cell blueprint;	// Blueprint for generator
   mailbox  gen2drv;	// Mailbox to driver for cells
   event    drv2gen;	// Event from driver when done with cell
   int      nCells;	// Number of cells for this generator to create
   int	    PortID;	// Which Rx port are we generating?

   function new(input mailbox gen2drv,
		input event drv2gen,
		input int nCells,
		input int PortID);
      this.gen2drv = gen2drv;
      this.drv2gen = drv2gen;
      this.nCells  = nCells;
      this.PortID  = PortID;
      blueprint = new();
   endfunction : new


   task run();
      NNI_cell c;
      repeat (nCells) begin
	 assert(blueprint.randomize());
	 $cast(c, blueprint.copy());
	 c.display($sformatf("Gen%0d: ", PortID));
	 gen2drv.put(c);
	 @drv2gen;		// Wait for driver to finish with it
      end
   endtask : run

endclass : NNI_generator

       // GENERATOR__SV

`line 25 "environment.sv" 2
`line 1 "driver.sv" 1
/**********************************************************************
 * Definition of an ATM driver
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/

                  
                  

`line 1 "atm_cell.sv" 1
/**********************************************************************
 * Definition of an ATM cell
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/

                    
                    

                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                        

                     
                                                  
                                             

                 
                 
             

                                                      
                                                     
                                                          
                                                             
                  

                       


                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                     
                                            
                                                  
                   


                                                                               
                         
                              
                         
                 


                                                                               
                                                             
                                         
                                       
                            



                                                                               

                                                
                  
                    
                                               
                                               
                                               
                                               
                                                
                                               
                                               
            
                     


                                                     
                 

                                                                                        
                                                          
                
                        
                                                        
                                                  
                                                                                        
                                                   
            
                     


                                                       
                           
                           
                           
                           
                          
                           
                               
                       


                                                
                
                               
                                  
                  
              
                  


                                                    
                             
                             
                             
                             
                            
                             
                                 
                                                                                
                  


                                                       
                               
                               
                               
                               
                              
                               
                                   
                    


                                                                             
                                                            
                                     
                 
                
                                                  
                           
                           
                          
                           
                               
               
                    


                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 




                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                            
                                                  
                   


                         
                              
                         
                 


                                                                               
                                                             
                                         
                                  
                            


                                                
                  
                    
                                               
                                               
                                               
                                               
                                               
                                               
            
                     


                                                     
                 

                                                                                
                                                     
                
                        
                                                        
                                                  
            
                     

                                                       
                           
                           
                           
                          
                           
                               
                       

                                                
                
                               
                                  
                  
              
                  

                                                    
                             
                             
                             
                            
                             
                                 
                  

                                                       
                               
                               
                               
                              
                               
                                   
                    

                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 


       // ATM_CELL__SV

`line 23 "driver.sv" 2


typedef class Driver;
/////////////////////////////////////////////////////////////////////////////
// Driver callback class
// Simple callbacks that are called before and after a cell is transmitted
// This class has empty tasks, which are used by default
// A testcase can extend this class to inject new behavior in the driver
// without having to change any code in the driver
class Driver_cbs;
   virtual task pre_tx(input Driver drv,
		       input UNI_cell c,
		       inout bit drop);
   endtask : pre_tx

   virtual task post_tx(input Driver drv,
		       input UNI_cell c);
   endtask : post_tx
endclass : Driver_cbs


/////////////////////////////////////////////////////////////////////////////
class Driver;

   mailbox gen2drv;	// For cells sent from generator
   event   drv2gen;	// Tell generator when I am done with cell
   vUtopiaRx Rx;	// Virtual interface for transmitting cells
   Driver_cbs cbsq[$];  // Queue of callback objects
   int PortID;
   
   extern function new(input mailbox gen2drv, input event drv2gen, 
		       input vUtopiaRx Rx, input int PortID);
   extern task run();
   extern task send (input UNI_cell c);

endclass : Driver


//---------------------------------------------------------------------------
// new(): Construct a driver object
//---------------------------------------------------------------------------
function Driver::new(input mailbox gen2drv,
		     input event drv2gen,
		     input vUtopiaRx Rx,
		     input int PortID);
   this.gen2drv = gen2drv;
   this.drv2gen = drv2gen;
   this.Rx      = Rx;
   this.PortID  = PortID;
endfunction : new 


//---------------------------------------------------------------------------
// run(): Run the driver. 
// Get transaction from generator, send into DUT
//---------------------------------------------------------------------------
task Driver::run();
   UNI_cell c;
   bit drop = 0;

   // Initialize ports
   Rx.cbr.data  <= 0;
   Rx.cbr.soc   <= 0;
   Rx.cbr.clav  <= 0;

   forever begin
      // Read the cell at the front of the mailbox
      gen2drv.peek(c);
      begin: Tx
	 // Pre-transmit callbacks
	 foreach (cbsq[i]) begin
	    cbsq[i].pre_tx(this, c, drop);
	    if (drop) disable Tx; 	// Don't transmit this cell
	 end

	 c.display($sformatf("@%0t: Drv%0d: ", $time, PortID));
	 send(c);
	 
	 // Post-transmit callbacks
	 foreach (cbsq[i])
	   cbsq[i].post_tx(this, c);
      end

      gen2drv.get(c);     // Remove cell from the mailbox
      ->drv2gen;	  // Tell the generator we are done with this cell
   end
endtask : run


//---------------------------------------------------------------------------
// send(): Send a cell into the DUT
//---------------------------------------------------------------------------
task Driver::send(input UNI_cell c);
   ATMCellType Pkt;

   c.pack(Pkt);
   $write("Sending cell: "); foreach (Pkt.Mem[i]) $write("%x ", Pkt.Mem[i]); $display;

   // Iterate through bytes of cell, deasserting Start Of Cell indicater
   @(Rx.cbr);
   Rx.cbr.clav <= 1;
   for (int i=0; i<=52; i++) begin
      // If not enabled, loop
      while (Rx.cbr.en === 1'b1) @(Rx.cbr);

      // Assert Start Of Cell indicater, assert enable, send byte 0 (i==0)
      Rx.cbr.soc  <= (i == 0);
      Rx.cbr.data <= Pkt.Mem[i];
      @(Rx.cbr);
    end
   Rx.cbr.soc <= 'z;
   Rx.cbr.data <= 8'bx;
   Rx.cbr.clav <= 0;
endtask

       // DRIVER__SV

`line 25 "environment.sv" 2
`line 1 "monitor.sv" 1
/**********************************************************************
 * Definition of the monitor class for the ATM testbench
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/


                   
                   

`line 1 "atm_cell.sv" 1
/**********************************************************************
 * Definition of an ATM cell
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/

                    
                    

                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                        

                     
                                                  
                                             

                 
                 
             

                                                      
                                                     
                                                          
                                                             
                  

                       


                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                     
                                            
                                                  
                   


                                                                               
                         
                              
                         
                 


                                                                               
                                                             
                                         
                                       
                            



                                                                               

                                                
                  
                    
                                               
                                               
                                               
                                               
                                                
                                               
                                               
            
                     


                                                     
                 

                                                                                        
                                                          
                
                        
                                                        
                                                  
                                                                                        
                                                   
            
                     


                                                       
                           
                           
                           
                           
                          
                           
                               
                       


                                                
                
                               
                                  
                  
              
                  


                                                    
                             
                             
                             
                             
                            
                             
                                 
                                                                                
                  


                                                       
                               
                               
                               
                               
                              
                               
                                   
                    


                                                                             
                                                            
                                     
                 
                
                                                  
                           
                           
                          
                           
                               
               
                    


                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 




                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                            
                                                  
                   


                         
                              
                         
                 


                                                                               
                                                             
                                         
                                  
                            


                                                
                  
                    
                                               
                                               
                                               
                                               
                                               
                                               
            
                     


                                                     
                 

                                                                                
                                                     
                
                        
                                                        
                                                  
            
                     

                                                       
                           
                           
                           
                          
                           
                               
                       

                                                
                
                               
                                  
                  
              
                  

                                                    
                             
                             
                             
                            
                             
                                 
                  

                                                       
                               
                               
                               
                              
                               
                                   
                    

                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 


       // ATM_CELL__SV

`line 24 "monitor.sv" 2


typedef class Monitor;
/////////////////////////////////////////////////////////////////////////////
// Monitor callback class
// Simple callbacks that are called before and after a cell is transmitted
// This class has empty tasks, which are used by default
// A testcase can extend this class to inject new behavior in the monitor
// without having to change any code in the monitor
class Monitor_cbs;
   virtual task post_rx(input Monitor mon,
		        input NNI_cell c);
   endtask : post_rx
endclass : Monitor_cbs


/////////////////////////////////////////////////////////////////////////////
class Monitor;

   vUtopiaTx Tx;		// Virtual interface with output of DUT
   Monitor_cbs cbsq[$];		// Queue of callback objects
   int PortID;

   extern function new(input vUtopiaTx Tx, input int PortID);
   extern task run();
   extern task receive (output NNI_cell c);
endclass : Monitor


//---------------------------------------------------------------------------
// new(): construct an object
//---------------------------------------------------------------------------
function Monitor::new(input vUtopiaTx Tx, input int PortID);
   this.Tx     = Tx;
   this.PortID = PortID;
endfunction : new


//---------------------------------------------------------------------------
// run(): Run the monitor
//---------------------------------------------------------------------------
task Monitor::run();
   NNI_cell c;
      
   forever begin
      receive(c);
      foreach (cbsq[i])
	cbsq[i].post_rx(this, c); 	 // Post-receive callback
   end
endtask : run


//---------------------------------------------------------------------------
// receive(): Read a cell from the DUT output, pack it into a NNI cell
//---------------------------------------------------------------------------
task Monitor::receive(output NNI_cell c);
   ATMCellType Pkt;

   Tx.cbt.clav <= 1;
   while (Tx.cbt.soc !== 1'b1 && Tx.cbt.en !== 1'b0)
     @(Tx.cbt);
   for (int i=0; i<=52; i++) begin
      // If not enabled, loop
      while (Tx.cbt.en !== 1'b0) @(Tx.cbt);
      
      Pkt.Mem[i] = Tx.cbt.data;
      @(Tx.cbt);
   end

   Tx.cbt.clav <= 0;

   c = new();
   c.unpack(Pkt);
   c.display($sformatf("@%0t: Mon%0d: ", $time, PortID));
   
endtask : receive

       // MONITOR__SV

`line 25 "environment.sv" 2
`line 1 "config.sv" 1
/**********************************************************************
 * Definition of an ATM configuration
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/


                  
                  


/////////////////////////////////////////////////////////////////////////////
// Configuration descriptor for ATM testbench
/////////////////////////////////////////////////////////////////////////////
class Config;
   int nErrors, nWarnings;		// Number of errors, warnings during simulation
   
   bit [31:0] numRx, numTx;		// Copy of parameters

   constraint c_numRxTx_valid
     {numRx inside {[1:16]};
      numTx inside {[1:16]};}

   rand bit [31:0] nCells;	// Total number of cells to transmit / receive
   constraint c_nCells_valid
     {nCells > 0; }
   constraint c_nCells_reasonable
     {nCells < 1000; }

   rand bit in_use_Rx[];	// Input / output channel enabled
   constraint c_in_use_valid
     {in_use_Rx.sum > 0; }	// Make sure at least one RX is enabled

   rand bit [31:0] cells_per_chan[];
   constraint c_sum_ncells_sum		// Split cells over all channels
     {cells_per_chan.sum == nCells;	// Total number of cells
      }

   // Set the cell count to zero for any channel not in use
   constraint c_zero_unused_channels
     {foreach (cells_per_chan[i])
       {
	solve in_use_Rx[i] before cells_per_chan[i];  // Needed for even dist of in_use
	if (in_use_Rx[i]) 
	     cells_per_chan[i] inside {[1:nCells]};
	else cells_per_chan[i] == 0;
	}
      }

   extern function new(input bit [31:0] numRx, numTx);
   extern virtual function void display(input string prefix="");

endclass : Config


//---------------------------------------------------------------------------
   function Config::new(input bit [31:0] numRx, numTx);
   if (!(numRx inside {[1:16]})) begin
      $display("FATAL %m numRx %0d out of bounds 1..16", numRx);
      $finish;
   end
   this.numRx = numRx;
   in_use_Rx = new[numRx];

   if (!(numTx inside{[1:16]})) begin
      $display("FATAL %m numTx %0d out of bounds 1..16", numTx);
      $finish;
   end
   this.numTx = numTx;

   cells_per_chan = new[numRx];
endfunction : new


//---------------------------------------------------------------------------
function void Config::display(input string prefix);
    $write("%sConfig: numRx=%0d, numTx=%0d, nCells=%0d (", prefix, numRx, numTx, nCells);
   foreach (cells_per_chan[i])
      $write("%0d ", cells_per_chan[i]);
   $write("), enabled RX: ", prefix);
   foreach (in_use_Rx[i]) if (in_use_Rx[i]) $write("%0d ", i);
   $display;
 endfunction : display

       // CONFIG__SV

`line 25 "environment.sv" 2
`line 1 "scoreboard.sv" 1
/**********************************************************************
 * Definition of the scoreboard class for the ATM testbench
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/

                      
                       

`line 1 "atm_cell.sv" 1
/**********************************************************************
 * Definition of an ATM cell
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/

                    
                    

                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                        

                     
                                                  
                                             

                 
                 
             

                                                      
                                                     
                                                          
                                                             
                  

                       


                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                     
                                            
                                                  
                   


                                                                               
                         
                              
                         
                 


                                                                               
                                                             
                                         
                                       
                            



                                                                               

                                                
                  
                    
                                               
                                               
                                               
                                               
                                                
                                               
                                               
            
                     


                                                     
                 

                                                                                        
                                                          
                
                        
                                                        
                                                  
                                                                                        
                                                   
            
                     


                                                       
                           
                           
                           
                           
                          
                           
                               
                       


                                                
                
                               
                                  
                  
              
                  


                                                    
                             
                             
                             
                             
                            
                             
                                 
                                                                                
                  


                                                       
                               
                               
                               
                               
                              
                               
                                   
                    


                                                                             
                                                            
                                     
                 
                
                                                  
                           
                           
                          
                           
                               
               
                    


                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 




                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                            
                                                  
                   


                         
                              
                         
                 


                                                                               
                                                             
                                         
                                  
                            


                                                
                  
                    
                                               
                                               
                                               
                                               
                                               
                                               
            
                     


                                                     
                 

                                                                                
                                                     
                
                        
                                                        
                                                  
            
                     

                                                       
                           
                           
                           
                          
                           
                               
                       

                                                
                
                               
                                  
                  
              
                  

                                                    
                             
                             
                             
                            
                             
                                 
                  

                                                       
                               
                               
                               
                              
                               
                                   
                    

                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 


       // ATM_CELL__SV

`line 23 "scoreboard.sv" 2

class Expect_cells;
   NNI_cell q[$];
   int iexpect, iactual;
endclass : Expect_cells



class Scoreboard;
   Config cfg;
   Expect_cells expect_cells[];
   NNI_cell cellq[$];
   int iexpect, iactual;

   extern function new(Config cfg);
   extern virtual function void wrap_up();
   extern function void save_expected(UNI_cell ucell);
   extern function void check_actual(input NNI_cell c, input int portn);
   extern function void display(string prefix="");
endclass : Scoreboard



//---------------------------------------------------------------------------
function Scoreboard::new(Config cfg);
   this.cfg = cfg;
   expect_cells = new[cfg.numTx];
   foreach (expect_cells[i])
     expect_cells[i] = new();
endfunction // Scoreboard


//---------------------------------------------------------------------------
function void Scoreboard::save_expected(UNI_cell ucell);
   NNI_cell ncell = ucell.to_NNI;
   CellCfgType CellCfg = top.squat.lut.read(ncell.VPI);

   $display("@%0t: Scb save: VPI=%0x, Forward=%b", $time, ncell.VPI, CellCfg.FWD);

   ncell.display($sformatf("@%0t: Scb save: ", $time));

   // Find all Tx ports where this cell will be forwarded
   for (int i=0; i<cfg.numTx; i++)
     if (CellCfg.FWD[i]) begin
	expect_cells[i].q.push_back(ncell); // Save cell in this forward queue
	expect_cells[i].iexpect++;
	iexpect++;
     end
endfunction : save_expected


//-----------------------------------------------------------------------------
function void Scoreboard::check_actual(input NNI_cell c,
				       input int portn);
   NNI_cell match;
   int match_idx;

   c.display($sformatf("@%0t: Scb check: ", $time));

   if (expect_cells[portn].q.size() == 0) begin
      $display("@%0t: ERROR: %m cell not found because scoreboard for TX%0d empty", $time, portn);
      c.display("Not Found: ");
      cfg.nErrors++;
      return;
   end

   expect_cells[portn].iactual++;
   iactual++;

   foreach (expect_cells[portn].q[i]) begin
      if (expect_cells[portn].q[i].compare(c)) begin
	 $display("@%0t: Match found for cell", $time);
	 expect_cells[portn].q.delete(i);
	 return;
      end
   end

   $display("@%0t: ERROR: %m cell not found", $time);
   c.display("Not Found: ");
   cfg.nErrors++;
endfunction : check_actual


//---------------------------------------------------------------------------
// Print end of simulation report
//---------------------------------------------------------------------------
function void Scoreboard::wrap_up();
   $display("@%0t: %m %0d expected cells, %0d actual cells received", $time, iexpect, iactual);

   // Look for leftover cells
   foreach (expect_cells[i]) begin
      if (expect_cells[i].q.size()) begin
	 $display("@%0t: %m cells remaining in Tx[%0d] scoreboard at end of test", $time, i);
	 this.display("Unclaimed: ");
	 cfg.nErrors++;
      end
   end
endfunction : wrap_up


//---------------------------------------------------------------------------
// Print the contents of the scoreboard, mainly for debugging
//---------------------------------------------------------------------------
function void Scoreboard::display(string prefix);
   $display("@%0t: %m so far %0d expected cells, %0d actual cells received", $time, iexpect, iactual);
   foreach (expect_cells[i]) begin
      $display("Tx[%0d]: exp=%0d, act=%0d", i, expect_cells[i].iexpect, expect_cells[i].iactual);
      foreach (expect_cells[i].q[j])
	expect_cells[i].q[j].display($sformatf("%sScoreboard: Tx%0d: ", prefix, i));
   end
endfunction : display


       // SCOREBOARD__SV

`line 25 "environment.sv" 2
`line 1 "coverage.sv" 1
/**********************************************************************
 * Functional coverage code
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/

                    
                    


class Coverage;

   bit [1:0] src;
   bit [NumTx-1:0] fwd;

   covergroup CG_Forward;

      coverpoint src
	{bins src[] = {[0:3]};
	 option.weight = 0;}
      coverpoint fwd
	{bins fwd[] = {[1:15]}; // Ignore fwd==0
	 option.weight = 0;}
      cross src, fwd;

   endgroup : CG_Forward

     // Instantiate the covergroup
     function new;
	CG_Forward = new;
     endfunction : new

   // Sample input data
   function void sample(input bit [1:0] src,
			input bit [NumTx-1:0] fwd);
      $display("@%0t: Coverage: src=%d. FWD=%b", $time, src, fwd);
      this.src = src;
      this.fwd = fwd;
      CG_Forward.sample();
   endfunction : sample

endclass : Coverage

       // COVERAGE__SV

`line 25 "environment.sv" 2
`line 1 "cpu_ifc.sv" 1
/**********************************************************************
 * Cell rewriting and forwarding configuration
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *   1.10 21 Jul 2004 -- corrected errata as printed in the book
 *                       "SystemVerilog for Design" (first edition) and
 *                       to bring the example into conformance with the
 *                       final Accellera SystemVerilog 3.1a standard
 *                       (for a description of changes, see the file
 *                       "errata_SV-Design-book_26-Jul-2004.txt")
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

                   
                    


`line 1 "definitions.sv" 1
/**********************************************************************
 * External typdefs included by most of the Utopia ATM model files
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

       // _INCL_DEFINITIONS

`line 71 "cpu_ifc.sv" 2

interface cpu_ifc;
  logic        BusMode;
  logic [11:0] Addr;
  logic        Sel;
  CellCfgType  DataIn;
  CellCfgType  DataOut;
  logic        Rd_DS;
  logic        Wr_RW;
  logic        Rdy_Dtack;

  modport Peripheral (input  BusMode, Addr, Sel, DataIn, Rd_DS, Wr_RW,
		      output DataOut, Rdy_Dtack);

  modport Test (output BusMode, Addr, Sel, DataIn, Rd_DS, Wr_RW,
		input  DataOut, Rdy_Dtack);

endinterface : cpu_ifc

typedef virtual cpu_ifc vCPU;
typedef virtual cpu_ifc.Test vCPU_T;


       // CPU_IFC__SV

`line 25 "environment.sv" 2
`line 1 "cpu_driver.sv" 1
/**********************************************************************
 * Definition of the CPU driver class
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/


                      
                       

`line 1 "atm_cell.sv" 1
/**********************************************************************
 * Definition of an ATM cell
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/

                    
                    

                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                        

                     
                                                  
                                             

                 
                 
             

                                                      
                                                     
                                                          
                                                             
                  

                       


                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                     
                                            
                                                  
                   


                                                                               
                         
                              
                         
                 


                                                                               
                                                             
                                         
                                       
                            



                                                                               

                                                
                  
                    
                                               
                                               
                                               
                                               
                                                
                                               
                                               
            
                     


                                                     
                 

                                                                                        
                                                          
                
                        
                                                        
                                                  
                                                                                        
                                                   
            
                     


                                                       
                           
                           
                           
                           
                          
                           
                               
                       


                                                
                
                               
                                  
                  
              
                  


                                                    
                             
                             
                             
                             
                            
                             
                                 
                                                                                
                  


                                                       
                               
                               
                               
                               
                              
                               
                                   
                    


                                                                             
                                                            
                                     
                 
                
                                                  
                           
                           
                          
                           
                               
               
                    


                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 




                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                            
                                                  
                   


                         
                              
                         
                 


                                                                               
                                                             
                                         
                                  
                            


                                                
                  
                    
                                               
                                               
                                               
                                               
                                               
                                               
            
                     


                                                     
                 

                                                                                
                                                     
                
                        
                                                        
                                                  
            
                     

                                                       
                           
                           
                           
                          
                           
                               
                       

                                                
                
                               
                                  
                  
              
                  

                                                    
                             
                             
                             
                            
                             
                                 
                  

                                                       
                               
                               
                               
                              
                               
                                   
                    

                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 


       // ATM_CELL__SV

`line 24 "cpu_driver.sv" 2
`line 1 "cpu_ifc.sv" 1
/**********************************************************************
 * Cell rewriting and forwarding configuration
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *   1.10 21 Jul 2004 -- corrected errata as printed in the book
 *                       "SystemVerilog for Design" (first edition) and
 *                       to bring the example into conformance with the
 *                       final Accellera SystemVerilog 3.1a standard
 *                       (for a description of changes, see the file
 *                       "errata_SV-Design-book_26-Jul-2004.txt")
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

                   
                    


                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                       

                  
                       
                    
                   
                      
                       
                     
                     
                         

                                                                      
                                   

                                                                
                             

                      

                             
                                    


       // CPU_IFC__SV

`line 24 "cpu_driver.sv" 2
//`include "CPUMethod.sv"

class CPU_driver;
   vCPU_T mif;
   CellCfgType lookup [255:0]; // copy of look-up table
   Config cfg;
   bit [NumTx-1:0] fwd;

   extern function new(vCPU_T mif, Config cfg);
   extern task Initialize_Host ();
   extern task HostWrite (int a, CellCfgType d); // configure
   extern task HostRead (int a, output CellCfgType d);
   extern task run();
endclass : CPU_driver


function CPU_driver::new(vCPU_T mif, Config cfg);
   this.mif = mif;
   this.cfg = cfg;
endfunction : new


task CPU_driver::Initialize_Host ();
   mif.BusMode <= 1;
   mif.Addr <= 0;
   mif.DataIn <= 0;
   mif.Sel <= 1;
   mif.Rd_DS <= 1;
   mif.Wr_RW <= 1;
endtask : Initialize_Host


task CPU_driver::HostWrite (int a, CellCfgType d); // configure
   #10 mif.Addr <= a; mif.DataIn <= d; mif.Sel <= 0;
   #10 mif.Wr_RW <= 0;
   while (mif.Rdy_Dtack!==0) #10;
   #10 mif.Wr_RW <= 1; mif.Sel <= 1;
   while (mif.Rdy_Dtack==0) #10;
endtask : HostWrite


task CPU_driver::HostRead (int a, output CellCfgType d);
   #10 mif.Addr <= a; mif.Sel <= 0;
   #10 mif.Rd_DS <= 0;
   while (mif.Rdy_Dtack!==0) #10;
   #10 d = mif.DataOut; mif.Rd_DS <= 1; mif.Sel <= 1;
   while (mif.Rdy_Dtack==0) #10;
endtask : HostRead


task CPU_driver::run();
   CellCfgType CellFwd;
   Initialize_Host();

   // Configure through Host interface
   repeat (10) @(negedge clk);
   $write("Memory: Loading ... ");
   for (int i=0; i<=255; i++) begin
      CellFwd.FWD = $urandom();
             
                      
      
      $display("CellFwd.FWD[%0d]=%0d", i, CellFwd.FWD);
      CellFwd.VPI = i;
      HostWrite(i, CellFwd);
      lookup[i] = CellFwd;
   end

   // Verify memory
   $write("Verifying ...");
   for (int i=0; i<=255; i++) begin
      HostRead(i, CellFwd);
      if (lookup[i] != CellFwd) begin
         $display("FATAL, Mem Location 0x%x contains 0x%x, expected 0x%x",
                  i, CellFwd, lookup[i]);
         $finish;
      end
   end
   $display("Verified");

endtask : run


       // CPU_DRIVER__SV

`line 25 "environment.sv" 2


/////////////////////////////////////////////////////////
// Call scoreboard from Driver using callbacks
/////////////////////////////////////////////////////////
class Scb_Driver_cbs extends Driver_cbs;
   Scoreboard scb;

   function new(Scoreboard scb);
      this.scb = scb;
   endfunction : new

   // Send received cell to scoreboard
   virtual task post_tx(input Driver drv,
		        input UNI_cell c);
   scb.save_expected(c);
   endtask : post_tx
endclass : Scb_Driver_cbs


/////////////////////////////////////////////////////////
// Call scoreboard from Monitor using callbacks
/////////////////////////////////////////////////////////
class Scb_Monitor_cbs extends Monitor_cbs;
   Scoreboard scb;

   function new(Scoreboard scb);
      this.scb = scb;
   endfunction : new

   // Send received cell to scoreboard
   virtual task post_rx(input Monitor mon,
		        input NNI_cell c);
   scb.check_actual(c, mon.PortID);
   endtask : post_rx
endclass : Scb_Monitor_cbs


/////////////////////////////////////////////////////////
// Call coverage from Monitor using callbacks
/////////////////////////////////////////////////////////
class Cov_Monitor_cbs extends Monitor_cbs;
   Coverage cov;

   function new(Coverage cov);
      this.cov = cov;
   endfunction : new

   // Send received cell to coverage
   virtual task post_rx(input Monitor mon,
		        input NNI_cell c);
   CellCfgType CellCfg = top.squat.lut.read(c.VPI);
   cov.sample(mon.PortID, CellCfg.FWD);
   endtask : post_rx
endclass : Cov_Monitor_cbs



/////////////////////////////////////////////////////////
class Environment;
   UNI_generator gen[];
   mailbox gen2drv[];
   event   drv2gen[];
   Driver drv[];
   Monitor mon[];
   Config cfg;
   Scoreboard scb;
   Coverage cov;
   virtual Utopia.TB_Rx Rx[];
   virtual Utopia.TB_Tx Tx[];
   int numRx, numTx;
   vCPU_T mif;
   CPU_driver cpu;

   extern function new(input vUtopiaRx Rx[],
		       input vUtopiaTx Tx[],
		       input int numRx, numTx,
		       input vCPU_T mif);
   extern virtual function void gen_cfg();
   extern virtual function void build();
   extern virtual task run();
   extern virtual function void wrap_up();

endclass : Environment


//---------------------------------------------------------------------------
// Construct an environment instance
//---------------------------------------------------------------------------
function Environment::new(input vUtopiaRx Rx[],
			  input vUtopiaTx Tx[],
			  input int numRx, numTx,
			  input vCPU_T mif);
   this.Rx = new[Rx.size()];
   foreach (Rx[i]) this.Rx[i] = Rx[i];
   this.Tx = new[Tx.size()];
   foreach (Tx[i]) this.Tx[i] = Tx[i];
   this.numRx = numRx;
   this.numTx = numTx;
   this.mif = mif;

   cfg = new(numRx,numTx);

   if ($test$plusargs("ntb_random_seed")) begin
      int seed;
      $value$plusargs("ntb_random_seed=%d", seed);
      $display("Simulation run with random seed=%0d", seed);
   end
   else
     $display("Simulation run with default random seed");
   
endfunction : new


//---------------------------------------------------------------------------
// Randomize the configuration descriptor
//---------------------------------------------------------------------------
function void Environment::gen_cfg();
   assert(cfg.randomize());
   cfg.display();
endfunction : gen_cfg


//---------------------------------------------------------------------------
// Build the environment objects for this test
// Note that objects are built for every channel, even if they are not in use
// This prevents the bug when you use a null handle
//---------------------------------------------------------------------------
function void Environment::build();

   cpu = new(mif, cfg);

   gen = new[numRx];
   drv = new[numRx];
   gen2drv = new[numRx];
   drv2gen = new[numRx];
   scb = new(cfg);
   cov = new();
   
   foreach(gen[i]) begin
      gen2drv[i] = new();
      gen[i] = new(gen2drv[i], drv2gen[i], cfg.cells_per_chan[i], i);
      drv[i] = new(gen2drv[i], drv2gen[i], Rx[i], i);
   end

   mon = new[numTx];
   foreach (mon[i])
     mon[i] = new(Tx[i], i);

   // Connect the scoreboard with callbacks
   begin
      Scb_Driver_cbs sdc = new(scb);
      Scb_Monitor_cbs smc = new(scb);
      foreach (drv[i]) drv[i].cbsq.push_back(sdc);  // Add scb to every driver
      foreach (mon[i]) mon[i].cbsq.push_back(smc);  // Add scb to every monitor
   end

   // Connect coverage with callbacks
   begin
      Cov_Monitor_cbs smc = new(cov);
      foreach (mon[i]) mon[i].cbsq.push_back(smc);  // Add cov to every monitor
   end

endfunction : build


//---------------------------------------------------------------------------
// Start the transactors (generators, drivers, monitors) in the environment
// Channels that are not in use don't get started
//---------------------------------------------------------------------------
task Environment::run();
   int num_gen_running;

   // Let the CPU interface do its initialization before anyone else starts
   cpu.run();

   num_gen_running = numRx;

   // For each input RX channel, start generator and driver
   foreach(gen[i]) begin
      int j=i;		// Automatic variable to hold index in spawned threads
      fork
	 begin
	 if (cfg.in_use_Rx[j]) gen[j].run();	// Wait for generator to finish
	    num_gen_running--;		// Decrement driver count
	 end
	 if (cfg.in_use_Rx[j]) drv[j].run();
      join_none
   end

   // For each output TX channel, start monitor
   foreach(mon[i]) begin
      int j=i;		// Automatic variable to hold index in spawned threads
      fork
	 mon[j].run();
      join_none
   end

   // Wait for all generators to finish, or time-out
   fork : timeout_block
      wait (num_gen_running == 0);
      begin
	 repeat (1_000_000) @(Rx[0].cbr);
	 $display("@%0t: %m ERROR: Timeout while waiting for generators to finish", $time);
	 cfg.nErrors++;
      end
   join_any
   disable timeout_block;
   
   // Wait a little longer for the data flow through switch, into monitors, and scoreboards
   repeat (1_000) @(Rx[0].cbr);
endtask : run


//---------------------------------------------------------------------------
// Any post-run cleanup / reporting
//---------------------------------------------------------------------------
function void Environment::wrap_up();
   $display("@%0t: End of simulation, %0d error%s, %0d warning%s", 
	    $time, cfg.nErrors, cfg.nErrors==1 ? "" : "s", cfg.nWarnings, cfg.nWarnings==1 ? "" : "s");
   scb.wrap_up;
   
endfunction : wrap_up

       // ENVIRONMENT__SV

`line 59 "small.sv" 2
   Environment env;

   initial begin
      env = new(top.Rx, top.Tx, NumRx, NumTx);
      env.gen_cfg;
      env.cfg.nCells = 2;
      env.cfg.cells_per_chan[0] = 2; env.cfg.in_use_Rx[0] = 1;
      env.cfg.cells_per_chan[1] = 0; env.cfg.in_use_Rx[1] = 0;
      env.cfg.cells_per_chan[2] = 0; env.cfg.in_use_Rx[2] = 0;
      env.cfg.cells_per_chan[3] = 0; env.cfg.in_use_Rx[3] = 0;
      env.cfg.display("Env: ");
      env.build();
      env.run();
      env.wrap_up;
   end
endprogram : testp

`line 1 "test.sv" 0
/**********************************************************************
 * Utopia ATM testbench
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *   1.10 21 Jul 2004 -- corrected errata as printed in the book
 *                       "SystemVerilog for Design" (first edition) and
 *                       to bring the example into conformance with the
 *                       final Accellera SystemVerilog 3.1a standard
 *                       (for a description of changes, see the file
 *                       "errata_SV-Design-book_26-Jul-2004.txt")
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

// The following include file listed in the book text is in an example
// file that is included by 10.00.00_example_top.sv
//`include "methods.sv"

`line 1 "definitions.sv" 1
/**********************************************************************
 * External typdefs included by most of the Utopia ATM model files
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

       // _INCL_DEFINITIONS

`line 71 "test.sv" 2

program automatic test
  #(parameter int NumRx = 4, parameter int NumTx = 4)
   (Utopia.TB_Rx Rx[0:NumRx-1],
    Utopia.TB_Tx Tx[0:NumTx-1],
    cpu_ifc.Test mif,
    input logic rst, clk);

  // Miscellaneous control interfaces
  logic Initialized;

  initial begin
    $display("Simulation was run with conditional compilation settings of:");
    $display("                                
    $display("                                
                 
                                 
          
                    
      $display("                    
          
    $display("");
  end

`line 1 "environment.sv" 1
/**********************************************************************
 * Definition of the environment class for the ATM testbench
 *
 * Author: Chris Spear
 * Revision: 1.01
 * Last modified: 8/2/2011
 *
 * (c) Copyright 2008-2011, Chris Spear, Greg Tumbush. *** ALL RIGHTS RESERVED ***
 * http://chris.spear.net
 *
 *  This source file may be used and distributed without restriction
 *  provided that this copyright statement is not removed from the file
 *  and that any derivative work contains this copyright notice.
 *
 * Used with permission in the book, "SystemVerilog for Verification"
 * By Chris Spear and Greg Tumbush
 * Book copyright: 2008-2011, Springer LLC, USA, Springer.com
 *********************************************************************/


                       
                       


                        
                                                                       
                                                          
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       

                     
                      

                       
                                                                       
                            
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       

                    
                    

                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                        

                     
                                                  
                                             

                 
                 
             

                                                      
                                                     
                                                          
                                                             
                  

                       


                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                     
                                            
                                                  
                   


                                                                               
                         
                              
                         
                 


                                                                               
                                                             
                                         
                                       
                            



                                                                               

                                                
                  
                    
                                               
                                               
                                               
                                               
                                                
                                               
                                               
            
                     


                                                     
                 

                                                                                        
                                                          
                
                        
                                                        
                                                  
                                                                                        
                                                   
            
                     


                                                       
                           
                           
                           
                           
                          
                           
                               
                       


                                                
                
                               
                                  
                  
              
                  


                                                    
                             
                             
                             
                             
                            
                             
                                 
                                                                                
                  


                                                       
                               
                               
                               
                               
                              
                               
                                   
                    


                                                                             
                                                            
                                     
                 
                
                                                  
                           
                           
                          
                           
                               
               
                    


                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 




                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                            
                                                  
                   


                         
                              
                         
                 


                                                                               
                                                             
                                         
                                  
                            


                                                
                  
                    
                                               
                                               
                                               
                                               
                                               
                                               
            
                     


                                                     
                 

                                                                                
                                                     
                
                        
                                                        
                                                  
            
                     

                                                       
                           
                           
                           
                          
                           
                               
                       

                                                
                
                               
                                  
                  
              
                  

                                                    
                             
                             
                             
                            
                             
                                 
                  

                                                       
                               
                               
                               
                              
                               
                                   
                    

                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 


                      

                         


                                                                             
                    

                                                 
                                                   
                                                             
                                                                   
                                                      
   
                                      
                      
                   
                    
                             
                             
                            
                            
                        
                    

              
                 
                           
                                
                             
                                                        
                 
                                                 
         
                

                        



                                                                             
                    

                                                 
                                                   
                                                             
                                                                   
                                                      

                                      
                      
                   
                    
                             
                             
                            
                            
                        
                    


              
                 
                           
                                
                             
                                           
                 
                                                 
         
                

                        

                       

                           
                     
                                                                       
                              
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       

                  
                  

                       
                                                                       
                            
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       

                    
                    

                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                        

                     
                                                  
                                             

                 
                 
             

                                                      
                                                     
                                                          
                                                             
                  

                       


                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                     
                                            
                                                  
                   


                                                                               
                         
                              
                         
                 


                                                                               
                                                             
                                         
                                       
                            



                                                                               

                                                
                  
                    
                                               
                                               
                                               
                                               
                                                
                                               
                                               
            
                     


                                                     
                 

                                                                                        
                                                          
                
                        
                                                        
                                                  
                                                                                        
                                                   
            
                     


                                                       
                           
                           
                           
                           
                          
                           
                               
                       


                                                
                
                               
                                  
                  
              
                  


                                                    
                             
                             
                             
                             
                            
                             
                                 
                                                                                
                  


                                                       
                               
                               
                               
                               
                              
                               
                                   
                    


                                                                             
                                                            
                                     
                 
                
                                                  
                           
                           
                          
                           
                               
               
                    


                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 




                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                            
                                                  
                   


                         
                              
                         
                 


                                                                               
                                                             
                                         
                                  
                            


                                                
                  
                    
                                               
                                               
                                               
                                               
                                               
                                               
            
                     


                                                     
                 

                                                                                
                                                     
                
                        
                                                        
                                                  
            
                     

                                                       
                           
                           
                           
                          
                           
                               
                       

                                                
                
                               
                                  
                  
              
                  

                                                    
                             
                             
                             
                            
                             
                                 
                  

                                                       
                               
                               
                               
                              
                               
                                   
                    

                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 


                      

                      


                     
                                                                             
                        
                                                                          
                                                        
                                                                        
                                                  
                 
                                        
                          
                         
                   

                                         
                           
                    
                     


                                                                             
             

                                                    
                                                              
                                                            
                                                    
              
   
                                                                   
                                               
                     
                                       

                 


                                                                             
                                   
                                                                             
                                           
                           
                          
                         
                          
                          
                     
                         
                  


                                                                             
                          
                                                
                                                                             
                   
              
                

                      
                     
                     
                     

                
                                                  
                      
               
                           
                         
                                   
                                                       
     

                                                        
          
  
                            
                   
                             
         

                                                         
                                                                   
      
             


                                                                             
                                   
                                                                             
                                    
                   

               
                                                                                      

                                                                        
             
                    
                                  
                             
                                           

                                                                          
                              
                                
                
       
                    
                       
                    
       

                    

                           
                      
                                                                       
                                                        
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       


                   
                   

                       
                                                                       
                            
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       

                    
                    

                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                        

                     
                                                  
                                             

                 
                 
             

                                                      
                                                     
                                                          
                                                             
                  

                       


                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                     
                                            
                                                  
                   


                                                                               
                         
                              
                         
                 


                                                                               
                                                             
                                         
                                       
                            



                                                                               

                                                
                  
                    
                                               
                                               
                                               
                                               
                                                
                                               
                                               
            
                     


                                                     
                 

                                                                                        
                                                          
                
                        
                                                        
                                                  
                                                                                        
                                                   
            
                     


                                                       
                           
                           
                           
                           
                          
                           
                               
                       


                                                
                
                               
                                  
                  
              
                  


                                                    
                             
                             
                             
                             
                            
                             
                                 
                                                                                
                  


                                                       
                               
                               
                               
                               
                              
                               
                                   
                    


                                                                             
                                                            
                                     
                 
                
                                                  
                           
                           
                          
                           
                               
               
                    


                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 




                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                            
                                                  
                   


                         
                              
                         
                 


                                                                               
                                                             
                                         
                                  
                            


                                                
                  
                    
                                               
                                               
                                               
                                               
                                               
                                               
            
                     


                                                     
                 

                                                                                
                                                     
                
                        
                                                        
                                                  
            
                     

                                                       
                           
                           
                           
                          
                           
                               
                       

                                                
                
                               
                                  
                  
              
                  

                                                    
                             
                             
                             
                            
                             
                                 
                  

                                                       
                               
                               
                               
                              
                               
                                   
                    

                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 


                      

                       


                      
                                                                             
                         
                                                                          
                                                        
                                                                         
                                                   
                  
                                          
                            
                    
                      


                                                                             
              

                                                         
                                                     
              

                                                             
                     
                                           
                  


                                                                             
                             
                                                                             
                                                            
                    
                        
                 


                                                                             
                         
                                                                             
                    
              
      
                
                 
                       
                                                     
      
             


                                                                             
                                                                      
                                                                             
                                         
                   

                    
                                                    
               
                                  
                             
                                           
      
                               
                
      

                    

             
                 
                                                         
   
                 

                     

                           
                     
                                                                       
                                     
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       


                  
                  


                                                                             
                                             
                                                                             
             
                                                                           
   
                                                  

                             
                            
                             

                                                                         
                            
                   
                                 
                      

                                                          
                            
                                                                  

                                    
                                                                
                                                            
       

                                                           
                                    
                                 
        
                                                                                
                   
                                            
                             
  
       

                                                      
                                                                

                 


                                                                             
                                                       
                                      
                                                                
              
      
                      
                          

                                     
                                                                
              
      
                      

                               
                 


                                                                             
                                                   
                                                                                         
                              
                                        
                                     
                                                              
            
                      

                    

                           
                         
                                                                       
                                                           
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       

                      
                       

                       
                                                                       
                            
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       

                    
                    

                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                        

                     
                                                  
                                             

                 
                 
             

                                                      
                                                     
                                                          
                                                             
                  

                       


                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                     
                                            
                                                  
                   


                                                                               
                         
                              
                         
                 


                                                                               
                                                             
                                         
                                       
                            



                                                                               

                                                
                  
                    
                                               
                                               
                                               
                                               
                                                
                                               
                                               
            
                     


                                                     
                 

                                                                                        
                                                          
                
                        
                                                        
                                                  
                                                                                        
                                                   
            
                     


                                                       
                           
                           
                           
                           
                          
                           
                               
                       


                                                
                
                               
                                  
                  
              
                  


                                                    
                             
                             
                             
                             
                            
                             
                                 
                                                                                
                  


                                                       
                               
                               
                               
                               
                              
                               
                                   
                    


                                                                             
                                                            
                                     
                 
                
                                                  
                           
                           
                          
                           
                               
               
                    


                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 




                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                            
                                                  
                   


                         
                              
                         
                 


                                                                               
                                                             
                                         
                                  
                            


                                                
                  
                    
                                               
                                               
                                               
                                               
                                               
                                               
            
                     


                                                     
                 

                                                                                
                                                     
                
                        
                                                        
                                                  
            
                     

                                                       
                           
                           
                           
                          
                           
                               
                       

                                                
                
                               
                                  
                  
              
                  

                                                    
                             
                             
                             
                            
                             
                                 
                  

                                                       
                               
                               
                               
                              
                               
                                   
                    

                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 


                      

                          

                   
                 
                        
                       



                 
              
                               
                     
                        

                                   
                                          
                                                      
                                                                        
                                                  
                     



                                                                             
                                     
                  
                                 
                            
                             
                         


                                                                             
                                                        
                                 
                                                       

                                                                                  

                                                       

                                                         
                                  
                              
                                                                       
                           
           
        
                           


                                                                               
                                                        
                            
                  
                 

                                                    

                                               
                                                                                                  
                               
                    
             
      

                                 
             

                                           
                                                    
                                                
                                  
         
         
      

                                                     
                            
                 
                          


                                                                             
                                 
                                                                             
                                    
                                                                                               

                             
                                  
                                         
                                                                                      
                              
                
         
      
                     


                                                                             
                                                             
                                                                             
                                                 
                                                                                                      
                                  
                                                                                                 
                                    
                                                                             
      
                     


                        

                           
                       
                                                                       
                           
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       

                    
                    


               

                 
                       

                         

                    
                       
                     
                    
                                         
                     
                     

                        

                                  
                  
                  
                      

                       
                                            
                              
                                                                  
                     
                     
                          
                       

                   

                      

                           
                      
                                                                       
                                              
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
                                                                
                                                                       
                                                                       
                                                                    
                                                                    
                                                                 
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                   
                    


                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                       

                  
                       
                    
                   
                      
                       
                     
                     
                         

                                                                      
                                   

                                                                
                             

                      

                             
                                    


                     

                           
                         
                                                                       
                                     
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       


                      
                       

                       
                                                                       
                            
  
                      
                 
                          
  
                                                                                  
                         
  
                                                                    
                                                                       
                                                                
  
                                                                     
                                  
                                                             
                                                                       

                    
                    

                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                        

                     
                                                  
                                             

                 
                 
             

                                                      
                                                     
                                                          
                                                             
                  

                       


                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                     
                                            
                                                  
                   


                                                                               
                         
                              
                         
                 


                                                                               
                                                             
                                         
                                       
                            



                                                                               

                                                
                  
                    
                                               
                                               
                                               
                                               
                                                
                                               
                                               
            
                     


                                                     
                 

                                                                                        
                                                          
                
                        
                                                        
                                                  
                                                                                        
                                                   
            
                     


                                                       
                           
                           
                           
                           
                          
                           
                               
                       


                                                
                
                               
                                  
                  
              
                  


                                                    
                             
                             
                             
                             
                            
                             
                                 
                                                                                
                  


                                                       
                               
                               
                               
                               
                              
                               
                                   
                    


                                                                             
                                                            
                                     
                 
                
                                                  
                           
                           
                          
                           
                               
               
                    


                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 




                                                                             
                  
                                                                             
                              
                     
                              
                              
                              
                             
                              
                                  

                      
                                    
                                         

                         
                                         
                                                        
                                                                
                                                               
                                                             
                                                            
                                                               
                                            
                                                  
                   


                         
                              
                         
                 


                                                                               
                                                             
                                         
                                  
                            


                                                
                  
                    
                                               
                                               
                                               
                                               
                                               
                                               
            
                     


                                                     
                 

                                                                                
                                                     
                
                        
                                                        
                                                  
            
                     

                                                       
                           
                           
                           
                          
                           
                               
                       

                                                
                
                               
                                  
                  
              
                  

                                                    
                             
                             
                             
                            
                             
                                 
                  

                                                       
                               
                               
                               
                              
                               
                                   
                    

                                                                             
                                                  
                                            
                   
                                             
                
                      
                               
                                        
             
                              
         
                          
      
                              
                               

                                                                             
                                    
                                                  
               
                   
                                       
                     
      
                     
                 


                      

                          
                      
                                                                       
                                              
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
                                                                
                                                                       
                                                                       
                                                                    
                                                                    
                                                                 
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                   
                    


                          
                                                                       
                                                                  
  
                                                               
                                                                    
                               
  
                                       
  
                                                                       
                         
  
                                                             
                                                                      
                                                                  
                                                                     
                                                                     
                                                                 
                                                                       
                                                                       
                                                               
                                                              
                                                                       
                                                                 
                                                                
                                                                       
                                                                   
  
                                                                     
                                                                       
                                                          
                                              
  
                                                               
                                                           
                                                                      
                                    
  
                    
                                                           
                                                                    
                                           
  
                                                                     
                                                                     
                                                                     
                                                                
  
                                                                  
                                                                      
                                              
  
                                                                     
                                                                     
                                                                  
                                                               
                                                                    
                    
                                                                       

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

                           

                       

                  
                       
                    
                   
                      
                       
                     
                     
                         

                                                                      
                                   

                                                                
                             

                      

                             
                                    


                     

                          
                         

                 
              
                                                       
              
                       

                                               
                                  
                                                             
                                                      
                     
                     


                                                 
                  
                  
                 


                                    
                    
                 
                   
                
                  
                  
                         


                                                               
                                                    
                      
                                 
                                    
                                
                   


                                                        
                                   
                      
                                 
                                                     
                                
                  


                       
                       
                     

                                      
                              
                                  
                                   
                               
             
                      
      
                                                       
                      
                            
                          
      

                   
                           
                                   
                           
                                     
                                                                          
                                         
                 
         
      
                        

             


                        

                           


                                                         
                                              
                                                         
                                        
                  

                                
                     
                    

                                      
                                         
                            
                        
                    
                         


                                                         
                                               
                                                         
                                          
                  

                                
                     
                    

                                      
                                          
                            
                                   
                    
                          


                                                         
                                             
                                                         
                                          
                

                              
                     
                    

                                    
                                          
                            
                                                   
                                       
                    
                          



                                                         
                  
                       
                     
                     
                
                 
              
                  
                
                             
                             
                    
              
                  

                                            
                              
                                
                           
                                          
                                        
                             
                                          

                      


                                                                             
                                    
                                                                             
                                               
                          
                            
                       
                            
                                      
                            
                                      
                      
                      
                  

                          

                                               
               
                                                  
                                                            
      
       
                                                         
   
                 


                                                                             
                                         
                                                                             
                                     
                           
                 
                     


                                                                             
                                              
                                                                             
                                                   
                                                                             
                                   

                       

                    
                    
                        
                        
                  
               
   
                        
                         
                                                                     
                                                     
      

                    
                   
                            

                                           
        
                                    
                                     
                                                                              
                                                                               
      

                                     
        
                                     
                                                                               
      

                   


                                                                             
                                                                           
                                                 
                                                                             
                        
                       

                                                                           
             

                           

                                                           
                        
                                                                      
          
       
                                                                     
                                                  
     
                                     
               
      

                                               
                        
                                                                      
          
               
               
      

                                                    
                       
                                  
           
                                  
                                                                                    
                
         
           
                         
   
                                                                                           
                               
             


                                                                             
                                   
                                                                             
                                     
                                                                   
                                                                                                
               
   
                     

       // ENVIRONMENT__SV

`line 95 "test.sv" 2
  Environment env;

// class Driver_cbs_drop extends Driver_cbs;
//  virtual task pre_tx(input ATM_cell cell, ref bit drop);
//     // Randomly drop 1 out of every 100 transactions
//     drop = ($urandom_range(0,99) == 0);
//   endtask
// endclass

// class Config_10_cells extends Config;
//    constraint ten_cells {nCells == 10; }

//    function new(input int NumRx,NumTx);
//       super.new(NumRx,NumTx);
//    endfunction : new
// endclass : Config_10_cells


  initial begin
    env = new(Rx, Tx, NumRx, NumTx, mif);

//      begin // Just simulate for 10 cells
// 	Config_10_cells c10 = new(NumRx,NumTx);
// 	env.cfg = c10;
//      end

    env.gen_cfg();
//     env.cfg.nCells = 100_000;
//     $display("nCells = 100_000");
    env.build();

//     begin             // Create error injection callback
//       Driver_cbs_drop dcd = new();
//       env.drv.cbs.push_back(dcd); // Put into driver's Q
//     end

    env.run();
    env.wrap_up();
  end

endprogram // test


`line 1 "top.sv" 0
/**********************************************************************
 * Utopia top-level--includes all files for simulating the complete
 * utopia design (not in book)
 *
 * This top-level file includes all of the example files in chapter 10.
 *
 * To simulate this example, invoke simulation on this file, with:
 *   `define SYNTHESIS commented out
 *   `define FWDALL uncommented (or enabled using +define+FWDALL)
 *
 * To synthesize this example, invoke simulation on this file, with:
 *   `define SYNTHESIS uncommented (or enabled using +define+SYNTHESIS)
 *   `define FWDALL commented out
 *
 * Author: Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/
`timescale 1ns/1ns

//`define SYNTHESIS	// conditional compilation flag for synthesis
//`define FWDALL		// conditional compilation flag to forward cells

                                                  
                                                 


module top;

  parameter int NumRx =  4  ;
  parameter int NumTx =  4  ;

  logic rst, clk;

  // System Clock and Reset
  initial begin
    rst = 0; clk = 0;
    #5ns rst = 1;
    #5ns clk = 1;
    #5ns rst = 0; clk = 0;
    forever 
      #5ns clk = ~clk;
  end

  Utopia Rx[0:NumRx-1] ();	// NumRx x Level 1 Utopia Rx Interface
  Utopia Tx[0:NumTx-1] ();	// NumTx x Level 1 Utopia Tx Interface
  cpu_ifc mif();	  // Intel-style Utopia parallel management interface
  squat #(NumRx, NumTx) squat(Rx, Tx, mif, rst, clk);	// DUT
  test  #(NumRx, NumTx) t1(Rx, Tx, mif, rst, clk);	// Test

endmodule : top

`line 1 "utopia1_atm_rx.sv" 0
/**********************************************************************
 * Utopia ATM receiver
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

module utopia1_atm_rx ( Utopia Rx );

  // 25MHz Rx clk out
  assign Rx.clk_out = Rx.clk_in;

  // Listen to the interface, collecting byte.
  // A complete cell is then copied to the cell buffer
  bit [0:5] PayloadIndex;
  enum bit [0:2] { reset, soc, vpi_vci, vci, vci_clp_pt, hec,
                   payload, ack } UtopiaStatus;

  always_ff @(posedge Rx.clk_in, posedge Rx.reset) begin: FSM
    if (Rx.reset) begin
      Rx.valid <= 0;
      Rx.en <= 1;
      UtopiaStatus <= reset;
    end
    else begin: FSM_sequencer
      unique case (UtopiaStatus)
        reset: begin: reset_state
          if (Rx.ready) begin
            UtopiaStatus <= soc;
            Rx.en <= 0;
          end
        end: reset_state

        soc: begin: soc_state
          if (Rx.soc && Rx.clav) begin
            {Rx.ATMcell.uni.GFC,
             Rx.ATMcell.uni.VPI[7:4]} <= Rx.data;
            UtopiaStatus <= vpi_vci;
          end
        end: soc_state

        vpi_vci: begin: vpi_vci_state
          if (Rx.clav) begin
            {Rx.ATMcell.uni.VPI[3:0],
             Rx.ATMcell.uni.VCI[15:12]} <= Rx.data;
            UtopiaStatus <= vci;
          end
        end: vpi_vci_state

        vci: begin: vci_state
          if (Rx.clav) begin
            Rx.ATMcell.uni.VCI[11:4] <= Rx.data;
            UtopiaStatus <= vci_clp_pt;
          end
        end: vci_state

        vci_clp_pt: begin: vci_clp_pt_state
          if (Rx.clav) begin
            {Rx.ATMcell.uni.VCI[3:0], Rx.ATMcell.uni.CLP,
             Rx.ATMcell.uni.PT} <= Rx.data;
            UtopiaStatus <= hec;
          end
        end: vci_clp_pt_state

        hec: begin: hec_state
          if (Rx.clav) begin
            Rx.ATMcell.uni.HEC <= Rx.data;
            UtopiaStatus <= payload;
            PayloadIndex = 0;  /* Blocking Assignment, due to
                                  blocking increment in
                                  payload state */
          end
        end: hec_state

        payload: begin: payload_state
          if (Rx.clav) begin
            Rx.ATMcell.uni.Payload[PayloadIndex] <= Rx.data;
            if (PayloadIndex==47) begin
              UtopiaStatus <= ack;
              Rx.valid <= 1;
              Rx.en <= 1;
            end
            PayloadIndex++;
          end
        end: payload_state

        ack: begin: ack_state
          if (!Rx.ready) begin
            UtopiaStatus <= reset;
            Rx.valid <= 0;
          end
        end: ack_state

        default: UtopiaStatus <= reset;
      endcase
    end: FSM_sequencer
  end: FSM
endmodule // utopia1_atm_rx


`line 1 "utopia1_atm_tx.sv" 0
/**********************************************************************
 * Utopia ATM transmitter
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

module utopia1_atm_tx ( Utopia Tx );

  assign Tx.clk_out = Tx.clk_in;

  logic [0:5] PayloadIndex;  // 0 to 47
  enum bit [0:3] { reset, soc, vpi_vci, vci, vci_clp_pt, hec,
                   payload, ack, done } UtopiaStatus;

  always_ff @(posedge Tx.clk_in, posedge Tx.reset) begin: FSM
    if (Tx.reset) begin
      Tx.soc <= 0;
      Tx.en <= 1;
      Tx.ready <= 1;
      UtopiaStatus <= reset;
    end
    else begin: FSM_sequencer
      unique case (UtopiaStatus)
        reset: begin: reset_state
          Tx.en <= 1;
          Tx.ready <= 1;
          if (Tx.valid) begin
            Tx.ready <= 0;
            UtopiaStatus <= soc;
          end
        end: reset_state

        soc: begin: soc_state
          if (Tx.clav) begin
            Tx.soc <= 1;
            Tx.data <= Tx.ATMcell.nni.VPI[11:4];
            UtopiaStatus <= vpi_vci;
          end
          Tx.en <= !Tx.clav;
        end: soc_state

        vpi_vci: begin: vpi_vci_state
          Tx.soc <= 0;
          if (Tx.clav) begin
            Tx.data <= {Tx.ATMcell.nni.VPI[3:0],
                        Tx.ATMcell.nni.VCI[15:12]};
            UtopiaStatus <= vci;
          end
          Tx.en <= !Tx.clav;
        end: vpi_vci_state

        vci: begin: vci_state
          if (Tx.clav) begin
            Tx.data <= Tx.ATMcell.nni.VCI[11:4];
            UtopiaStatus <= vci_clp_pt;
          end
          Tx.en <= !Tx.clav;
        end: vci_state

        vci_clp_pt: begin: vci_clp_pt_state
          if (Tx.clav) begin
            Tx.data <= {Tx.ATMcell.nni.VCI[3:0],
                        Tx.ATMcell.nni.CLP, Tx.ATMcell.nni.PT};
            UtopiaStatus <= hec;
          end
          Tx.en <= !Tx.clav;
        end: vci_clp_pt_state

        hec: begin: hec_state
          if (Tx.clav) begin
            Tx.data <= Tx.ATMcell.nni.HEC;
            UtopiaStatus <= payload;
            PayloadIndex = 0;
          end
          Tx.en <= !Tx.clav;
        end: hec_state

        payload: begin: payload_state
          if (Tx.clav) begin
            Tx.data <= Tx.ATMcell.nni.Payload[PayloadIndex];
            if (PayloadIndex==47) UtopiaStatus <= ack;
            PayloadIndex++;
          end
          Tx.en <= !Tx.clav;
        end: payload_state

        ack: begin: ack_state
          Tx.en <= 1;
          if (!Tx.valid) begin
            Tx.ready <= 1;
            UtopiaStatus <= done;
          end
        end: ack_state

        done: begin: done_state
          if (!Tx.valid) begin
            Tx.ready <= 0;
            UtopiaStatus <= reset;
          end
        end: done_state
      endcase
    end: FSM_sequencer
  end: FSM
endmodule // utopia1_atm_tx


`line 1 "utopia.sv" 0
/**********************************************************************
 * Utopia ATM interface, modeled as a SystemVerilog interface
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *   1.10 21 Jul 2004 -- corrected errata as printed in the book
 *                       "SystemVerilog for Design" (first edition) and
 *                       to bring the example into conformance with the
 *                       final Accellera SystemVerilog 3.1a standard
 *                       (for a description of changes, see the file
 *                       "errata_SV-Design-book_26-Jul-2004.txt")
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

`line 1 "definitions.sv" 1
/**********************************************************************
 * External typdefs included by most of the Utopia ATM model files
 *
 * To simulate this example with stimulus, invoke simulation on
 * 10.00.00_example_top.sv.  This top-level file includes all of the
 * example files in chapter 10.
 *
 * Author: Lee Moore, Stuart Sutherland
 *
 * (c) Copyright 2003, Sutherland HDL, Inc. *** ALL RIGHTS RESERVED ***
 * www.sutherland-hdl.com
 *
 * This example is based on an example from Janick Bergeron's
 * Verification Guild[1].  The original example is a non-synthesizable
 * behavioral model written in Verilog-1995 of a quad Asynchronous
 * Transfer Mode (ATM) user-to-network interface and forwarding node.
 * This example modifies the original code to be synthesizable, using
 * SystemVerilog constructs.  Also, the model has been made to be
 * configurable, so that it can be easily scaled from a 4x4 quad switch
 * to a 16x16 switch, or any other desired configuration.  The example,
 * including a nominal test bench, is partitioned into 8 files,
 * numbered 10.xx.xx_example_10-1.sv through 10-8.sv (where xx
 * represents section and subsection numbers in the book "SystemVerilog
 * for Design" (first edition).  The file 10.00.00_example_top.sv
 * includes all of the other files.  Simulation only needs to be
 * invoked on this one file.  Conditional compilation switches (`ifdef)
 * is used to compile the examples for simulation or for synthesis.
 *
 * [1] The Verification Guild is an independent e-mail newsletter and
 * moderated discussion forum on hardware verification.  Information on
 * the original Verification Guild example can be found at
 * www.janick.bergeron.com/guild/project.html.
 *
 * Used with permission in the book, "SystemVerilog for Design"
 *  By Stuart Sutherland, Simon Davidmann, and Peter Flake.
 *  Book copyright: 2003, Kluwer Academic Publishers, Norwell, MA, USA
 *  www.wkap.il, ISBN: 0-4020-7530-8
 *
 * Revision History:
 *   1.00 15 Dec 2003 -- original code, as included in book
 *   1.01 10 Jul 2004 -- cleaned up comments, added expected results
 *                       to output messages
 *
 * Caveat: Expected results displayed for this code example are based
 * on an interpretation of the SystemVerilog 3.1 standard by the code
 * author or authors.  At the time of writing, official SystemVerilog
 * validation suites were not available to validate the example.
 *
 * RIGHT TO USE: This code example, or any portion thereof, may be
 * used and distributed without restriction, provided that this entire
 * comment block is included with the example.
 *
 * DISCLAIMER: THIS CODE EXAMPLE IS PROVIDED "AS IS" WITHOUT WARRANTY
 * OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED
 * TO WARRANTIES OF MERCHANTABILITY, FITNESS OR CORRECTNESS. IN NO
 * EVENT SHALL THE AUTHOR OR AUTHORS BE LIABLE FOR ANY DAMAGES,
 * INCLUDING INCIDENTAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF THE
 * USE OF THIS CODE.
 *********************************************************************/

                         
                         

  
              
  
                     
                       
                        
                        
                        
                        
                       
                        
                            
          

                     
                       
                        
                        
                        
                       
                        
                            
          

                                             
                       
                          
                          
                          
                           
          

  
                                             
  
                      
              
              
              
                       
              

  
                                             
  
                       
                         
                 
              

       // _INCL_DEFINITIONS

`line 67 "utopia.sv" 2

interface Utopia;
  parameter int IfWidth = 8;

  logic [IfWidth-1:0] data;
  bit clk_in, clk_out;
  bit soc, en, clav, valid, ready, reset, selected;

  ATMCellType ATMcell;  // union of structures for ATM cells

  modport TopReceive (
    input  data, soc, clav, 
    output clk_in, reset, ready, clk_out, en, ATMcell, valid );

  modport TopTransmit (
    input  clav, 
    inout  selected,
    output clk_in, clk_out, ATMcell, data, soc, en, valid, reset, ready );

  modport CoreReceive (
    input  clk_in, data, soc, clav, ready, reset,
    output clk_out, en, ATMcell, valid );

  modport CoreTransmit (
    input  clk_in, clav, ATMcell, valid, reset,
    output clk_out, data, soc, en, ready );

   clocking cbr @(negedge clk_out);
      input clk_in, clk_out, ATMcell, valid, reset, en, ready;
      output data, soc, clav;
   endclocking : cbr
   modport TB_Rx (clocking cbr);

   clocking cbt @(negedge clk_out);
      input  clk_out, clk_in, ATMcell, soc, en, valid, reset, data, ready;
      output clav;
   endclocking : cbt
   modport TB_Tx (clocking cbt);

endinterface

typedef virtual Utopia vUtopia;
typedef virtual Utopia.TB_Rx vUtopiaRx;
typedef virtual Utopia.TB_Tx vUtopiaTx;

