module spc2wbm (

    /*
     * Inputs
     */

    // System inputs
    input sys_clock_i,                            // System Clock
    input sys_reset_i,                            // System Reset
    input[5:0] sys_interrupt_source_i,            // Encoded Interrupt Source

    // SPARC-side inputs connected to the PCX (Processor-to-Cache Xbar) outputs of the SPARC Core
    input[4:0] spc_req_i,                         // Request
    input spc_atom_i,                             // Atomic Request
    input[(   124  -1):0] spc_packetout_i,      // Outgoing Packet

    // Wishbone Master interface inputs
    input wbm_ack_i,                              // Ack
    input[( 64-1):0] wbm_data_i,       // Data In

    /*
     * Outputs
     */

    // SPARC-side outputs connected to the CPX (Cache-to-Processor Xbar) inputs of the SPARC Core
    output reg[4:0] spc_grant_o,                  // Grant
    output reg spc_ready_o,                       // Ready
    output reg[   145  -1:0] spc_packetin_o,    // Incoming Packet
    output reg spc_stall_o,                       // Stall Requests
    output reg spc_resume_o,                      // Resume Requests

    // Wishbone Master interface outputs
    output reg wbm_cycle_o,                       // Cycle Start
    output reg wbm_strobe_o,                      // Strobe Request
    output reg wbm_we_o,                          // Write Enable
    output reg[ 64-1:0] wbm_addr_o,    // Address Bus
    output reg[ 64-1:0] wbm_data_o,    // Data Out
    output reg[ 64/8-1:0] wbm_sel_o    // Select Output

  );


  /*
   * Registers
   */

  // Registers to latch requests from SPARC Core to Wishbone Master
  reg[3:0] state;
  reg[4:0] spc2wbm_region;                                             // Target region number (one-hot encoded)
  reg spc2wbm_atomic;                                                  // Request is Atomic
  reg[(   124  -1):0] spc2wbm_packet;                                // Latched Packet

  // Wishbone Master to SPARC Core info used to encode the return packet
  reg wbm2spc_valid;                                                   // Valid
  reg[(       143  -       140):0] wbm2spc_type;                         // Request type
  reg wbm2spc_miss;                                                    // L2 Miss
  reg[(      139  -      137-1):0] wbm2spc_error;                    // Error
  reg wbm2spc_nc;                                                      // Non-Cacheable
  reg[(       135  -       134):0] wbm2spc_thread;                       // Thread
  reg wbm2spc_way_valid;                                               // L2 Way Valid
  reg[(       132  -       131):0] wbm2spc_way;                          // Replaced L2 Way
  reg wbm2spc_boot_fetch;                                              // Fetch for Boot
  reg wbm2spc_atomic;                                                  // Atomic LD/ST or 2nd IFill packet
  reg wbm2spc_pfl;                                                     // PFL
  reg[(       127  -         0):0] wbm2spc_data;                         // Load Data
  reg[6:0] wbm2spc_interrupt_source;                                   // Encoded Interrupt Source
  reg wbm2spc_interrupt_new;                                           // New Interrupt Pending

  /*
   * Wires
   */

  // Decoded SPARC Core to Wishbone Master info
  wire spc2wbm_req;                                                     // Request
  wire spc2wbm_valid;                                                   // Valid
  wire[(       122  -       118):0] spc2wbm_type;                         // Request type
  wire spc2wbm_nc;                                                      // Non-Cacheable
  wire[(       116  -       114):0] spc2wbm_cpu_id;                       // CPU ID
  wire[(       113  -       112):0] spc2wbm_thread;                       // Thread
  wire spc2wbm_invalidate;                                              // Invalidate all
  wire[(       108  -       107):0] spc2wbm_way;                          // Replaced L1 Way
  wire[(       106  -       104):0] spc2wbm_size;                         // Load/Store size
  wire[(       103  -        64):0] spc2wbm_addr;                         // Address
  wire[(        63  -         0):0] spc2wbm_data;                         // Store Data

  // Return packets assembled with various fields
  wire[   145  -1:0] wbm2spc_packet;                                  // Incoming Packet

  /*
   * Encode/decode incoming info
   *
   * Legenda: available constants for some of the PCX/CPX fields.
   *
   * spc2wbm_size (3 bits) is one of:
   * - PCX_SZ_1B
   * - PCX_SZ_2B
   * - PCX_SZ_4B
   * - PCX_SZ_8B
   * - PCX_SZ_16B (Read accesses only)
   *
   * spc2wbm_type (5 bits) is one of:
   * { LOAD_RQ, IMISS_RQ, STORE_RQ, CAS1_RQ, CAS2_RQ, SWAP_RQ, STRLOAD_RQ, STRST_RQ, STQ_RQ,
   *   INT_RQ, FWD_RQ, FWD_RPY, RSVD_RQ }
   *
   * wbm2spc_type (4 bits) is one of:
   * { LOAD_RET, INV_RET, ST_ACK, AT_ACK, INT_RET, TEST_RET, FP_RET, IFILL_RET, EVICT_REQ,
   *   ERR_RET, STRLOAD_RET, STRST_ACK, FWD_RQ_RET, FWD_RPY_RET, RSVD_RET }
   *
   */

  // Decode info arriving from the SPC side
  assign spc2wbm_req = ( spc_req_i[4] | spc_req_i[3] | spc_req_i[2] | spc_req_i[1] | spc_req_i[0] );
  assign spc2wbm_valid = spc2wbm_packet[         123  ];
  assign spc2wbm_type = spc2wbm_packet[       122  :       118];
  assign spc2wbm_nc = spc2wbm_packet[          117  ];
  assign spc2wbm_cpu_id = spc2wbm_packet[       116  :       114];
  assign spc2wbm_thread = spc2wbm_packet[       113  :       112];
  assign spc2wbm_invalidate = spc2wbm_packet[      111];
  assign spc2wbm_way = spc2wbm_packet[       108  :       107];
  assign spc2wbm_size = spc2wbm_packet[       106  :       104];
  assign spc2wbm_addr = spc2wbm_packet[       103  :        64];
  assign spc2wbm_data = spc2wbm_packet[        63  :         0];

  // Encode info going to the SPC side assembling return packets
  assign wbm2spc_packet = { wbm2spc_valid, wbm2spc_type, wbm2spc_miss, wbm2spc_error, wbm2spc_nc, wbm2spc_thread,
    wbm2spc_way_valid, wbm2spc_way, wbm2spc_boot_fetch, wbm2spc_atomic, wbm2spc_pfl, wbm2spc_data };

  /*
   * State Machine
   */

  always @(posedge sys_clock_i) begin

    // Initialization
    if(sys_reset_i==1) begin

      // Clear outputs going to SPARC Core inputs
      spc_grant_o <= 5'b00000;
      spc_ready_o <= 0;
      spc_packetin_o <= 0;
      spc_stall_o <= 0;
      spc_resume_o <= 0;

      // Clear Wishbone Master interface outputs
      wbm_cycle_o <= 0;
      wbm_strobe_o <= 0;
      wbm_we_o <= 0;
      wbm_addr_o <= 64'b0;
      wbm_data_o <= 64'b0;
      wbm_sel_o <= 8'b0;

      // Prepare wakeup packet for SPARC Core, the resulting output is
      // spc_packetin_o <= `CPX_WIDTH'h1700000000000000000000000000000010001;
      wbm2spc_valid <= 1;
      wbm2spc_type <=          4'b0111;
      wbm2spc_miss <= 0;
      wbm2spc_error <= 0;
      wbm2spc_nc <= 0;
      wbm2spc_thread <= 0;
      wbm2spc_way_valid <= 0;
      wbm2spc_way <= 0;
      wbm2spc_boot_fetch <= 0;
      wbm2spc_atomic <= 0;
      wbm2spc_pfl <= 0;
      wbm2spc_data <= 64'h10001;
      wbm2spc_interrupt_source <= 7'h0;
      wbm2spc_interrupt_new <= 1'b0;

      // Clear state machine
      state <=           4'b0000;

    end else begin

      // FSM State 0: STATE_WAKEUP
      // Send to the SPARC Core the wakeup packet
      if(state==          4'b0000) begin

        // Send wakeup packet
        spc_ready_o <= 1;
        spc_packetin_o <= wbm2spc_packet;

// synopsys translate_off
        // Display comment
            
        $display("INFO: SPC2WBM: SPARC Core to Wishbone Master bridge starting...");
        $display("INFO: SPC2WBM: Wakeup packet sent to SPARC Core");
      
// synopsys translate_on

        // Unconditional state change
        state <=             4'b0001;

      // FSM State 1: STATE_IDLE
      // Wait for a request from the SPARC Core
      // If available send an interrupt packet to the Core
      end else if(state==            4'b0001) begin

        // Check if there's an incoming request
        if(spc2wbm_req==1) begin

          // Clear previously modified outputs
          spc_ready_o <= 0;
          spc_packetin_o <= 0;

          // Stall other requests from the SPARC Core
          spc_stall_o <= 1;

          // Latch target region and atomicity
          spc2wbm_region <= spc_req_i;
          spc2wbm_atomic <= spc_atom_i;

          // Jump to next state
          state <=  4'b0010;

        // See if the interrupt vector has changed
        end else if(sys_interrupt_source_i!=wbm2spc_interrupt_source) begin

          // Set the flag for next cycle
          wbm2spc_interrupt_new <= 1;

          // Prepare the interrupt packet for the SPARC Core
          wbm2spc_valid <= 1;
          wbm2spc_type <=          4'b0111;
          wbm2spc_miss <= 0;
          wbm2spc_error <= 0;
          wbm2spc_nc <= 0;
          wbm2spc_thread <= 0;
          wbm2spc_way_valid <= 0;
          wbm2spc_way <= 0;
          wbm2spc_boot_fetch <= 0;
          wbm2spc_atomic <= 0;
          wbm2spc_pfl <= 0;

        // Next cycle see if there's an int to be forwarded to the Core
        end else if(wbm2spc_interrupt_source!=6'b000000 && wbm2spc_interrupt_new) begin

          // Clean the flag
          wbm2spc_interrupt_new <= 0;

          // Send the interrupt packet to the Core
          spc_ready_o <= 1;
          spc_packetin_o <= wbm2spc_packet;

          // Stay in this state
          state <=             4'b0001;

        // Nothing to do, stay idle
        end else begin

          // Clear previously modified outputs
          spc_ready_o <= 0;
          spc_packetin_o <= 0;

      // Clear stall/resume signals
          spc_stall_o <= 0;
      spc_resume_o <= 0;

          // Stay in this state
          state <=             4'b0001;

        end

      // FSM State 2: STATE_REQUEST_LATCHED
      // We've just latched the request
      // Now we latch the packet
      // Start granting the request
      end else if(state== 4'b0010) begin

        // Latch the incoming packet
        spc2wbm_packet <= spc_packetout_i;

        // Grant the request to the SPARC Core
        spc_grant_o <= spc2wbm_region;

        // Clear the stall signal
        spc_stall_o <= 0;

// synopsys translate_off
        // Print details of SPARC Core request
            
        $display("INFO: SPC2WBM: *** NEW REQUEST FROM SPARC CORE ***");
        if(spc2wbm_region[0]==1) $display("INFO: SPC2WBM: Request to RAM Bank 0");
        else if(spc2wbm_region[1]==1) $display("INFO: SPC2WBM: Request to RAM Bank 1");
        else if(spc2wbm_region[2]==1) $display("INFO: SPC2WBM: Request to RAM Bank 2");
        else if(spc2wbm_region[3]==1) $display("INFO: SPC2WBM: Request to RAM Bank 3");
        else if(spc2wbm_region[4]==1) $display("INFO: SPC2WBM: Request targeted to I/O Block");
        else $display("INFO: SPC2WBM: Request to target region unknown");
        if(spc2wbm_atomic==1) $display("INFO: SPC2WBM: Request is ATOMIC");
        else $display("INFO: SPC2WBM: Request is not atomic");
      
// synopsys translate_on

        // Unconditional state change
        state <=   4'b0011;

      // FSM State 3: STATE_PACKET_LATCHED
      // The packet has already been latched
      // Decode this packet to build the request for the Wishbone bus
      // The grant of the request to the SPARC Core has been completed
      end else if(state==  4'b0011) begin

        // Clear previously modified outputs
        spc_grant_o <= 5'b0;

        // Issue a request on the Wishbone bus
        wbm_cycle_o <= 1;
        wbm_strobe_o <= 1;
        wbm_addr_o <= { spc2wbm_region, 19'b0, spc2wbm_addr[       103  -        64:3], 3'b000 };
        wbm_data_o <= spc2wbm_data;

        // Handle write enable and byte select
        if(spc2wbm_type==    5'b10000) begin

          // For instruction miss always read memory
          wbm_we_o <= 0;
          if(spc2wbm_region==5'b10000)
            // For accesses to SSI ROM only 32 bits are required
            wbm_sel_o <= (4'b1111<<(spc2wbm_addr[2]<<2));
          else
            // For accesses to RAM 256 bits are expected (2 ret packets)
            wbm_sel_o <= 8'b11111111;

        end else if(spc2wbm_type==     5'b00000) begin

          // For data load use the provided data
          wbm_we_o <= 0;
          case(spc2wbm_size)
                3'b000  : wbm_sel_o <= (1'b1<<spc2wbm_addr[2:0]);
                3'b001  : wbm_sel_o <= (2'b11<<(spc2wbm_addr[2:1]<<1));
                3'b010  : wbm_sel_o <= (4'b1111<<(spc2wbm_addr[2]<<2));
                3'b011  : wbm_sel_o <= 8'b11111111;
               3'b111  : wbm_sel_o <= 8'b11111111;  // Requires a 2nd access
            default: wbm_sel_o <= 8'b00000000;
          endcase

        end else if(spc2wbm_type==    5'b00001) begin

          // For data store use the provided data
          wbm_we_o <= 1;
          case(spc2wbm_size)
                3'b000  : wbm_sel_o <= (1'b1<<spc2wbm_addr[2:0]);
                3'b001  : wbm_sel_o <= (2'b11<<(spc2wbm_addr[2:1]<<1));
                3'b010  : wbm_sel_o <= (4'b1111<<(spc2wbm_addr[2]<<2));
                3'b011  : wbm_sel_o <= 8'b11111111;
               3'b111  : wbm_sel_o <= 8'b11111111;  // Requires a 2nd access
            default: wbm_sel_o <= 8'b00000000;
          endcase

        end else begin

          wbm_we_o <= 1;
          wbm_sel_o <= 8'b00000000;

        end

// synopsys translate_off
        // Print details of request packet
            
        $display("INFO: SPC2WBM: Valid bit is %X", spc2wbm_valid);
        case(spc2wbm_type)
               5'b00000: $display("INFO: SPC2WBM: Request of Type LOAD_RQ");
              5'b10000: $display("INFO: SPC2WBM: Request of Type IMISS_RQ");
              5'b00001: $display("INFO: SPC2WBM: Request of Type STORE_RQ");
               5'b00010: $display("INFO: SPC2WBM: Request of Type CAS1_RQ");
               5'b00011: $display("INFO: SPC2WBM: Request of Type CAS2_RQ");
               5'b00110: $display("INFO: SPC2WBM: Request of Type SWAP_RQ");
            5'b00100: $display("INFO: SPC2WBM: Request of Type STRLOAD_RQ");
              5'b00101: $display("INFO: SPC2WBM: Request of Type STRST_RQ");
                    5'b00111: $display("INFO: SPC2WBM: Request of Type STQ_RQ");
                    5'b01001: $display("INFO: SPC2WBM: Request of Type INT_RQ");
                    5'b01101: $display("INFO: SPC2WBM: Request of Type FWD_RQ");
                   5'b01110: $display("INFO: SPC2WBM: Request of Type FWD_RPY");
                   5'b11111: $display("INFO: SPC2WBM: Request of Type RSVD_RQ");
          default: $display("INFO: SPC2WBM: Request of Type Unknown");
    endcase
        $display("INFO: SPC2WBM: Non-Cacheable bit is %X", spc2wbm_nc);
        $display("INFO: SPC2WBM: CPU-ID is %X", spc2wbm_cpu_id);
        $display("INFO: SPC2WBM: Thread is %X", spc2wbm_thread);
        $display("INFO: SPC2WBM: Invalidate All is %X", spc2wbm_invalidate);
        $display("INFO: SPC2WBM: Replaced L1 Way is %X", spc2wbm_way);
        case(spc2wbm_size)
              3'b000  : $display("INFO: SPC2WBM: Request size is 1 Byte");
              3'b001  : $display("INFO: SPC2WBM: Request size is 2 Bytes");
              3'b010  : $display("INFO: SPC2WBM: Request size is 4 Bytes");
              3'b011  : $display("INFO: SPC2WBM: Request size is 8 Bytes");
             3'b111  : $display("INFO: SPC2WBM: Request size is 16 Bytes");
          default: $display("INFO: SPC2WBM: Request size is Unknown");
        endcase
        $display("INFO: SPC2WBM: Address is %X", spc2wbm_addr);
        $display("INFO: SPC2WBM: Data is %X", spc2wbm_data);
      
// synopsys translate_on

        // Unconditional state change
        state <=  4'b0100;

      // FSM State 4: STATE_REQUEST_GRANTED
      // Wishbone access completed, latch the incoming data
      end else if(state== 4'b0100) begin

        // Wait until Wishbone access completes
        if(wbm_ack_i==1) begin

          // Clear previously modified outputs
          if(spc2wbm_atomic==0) wbm_cycle_o <= 0;
          wbm_strobe_o <= 0;
          wbm_we_o <= 0;
          wbm_addr_o <= 64'b0;
          wbm_data_o <= 64'b0;
          wbm_sel_o <= 8'b0;

          // Latch the data and set up the return packet for the SPARC Core
          wbm2spc_valid <= 1;
          case(spc2wbm_type)
                5'b10000: begin
              wbm2spc_type <=        4'b0001; // I-Cache Miss
              wbm2spc_atomic <= 0;
            end
                 5'b00000: begin
              wbm2spc_type <=         4'b0000;  // Load
              wbm2spc_atomic <= spc2wbm_atomic;
            end
                5'b00001: begin
              wbm2spc_type <=           4'b0100;    // Store
              wbm2spc_atomic <= spc2wbm_atomic;
            end
          endcase
          wbm2spc_miss <= 0;
          wbm2spc_error <= 0;
          wbm2spc_nc <= spc2wbm_nc;
          wbm2spc_thread <= spc2wbm_thread;
          wbm2spc_way_valid <= 0;
          wbm2spc_way <= 0;
      if(spc2wbm_region==5'b10000) wbm2spc_boot_fetch <= 1;
      else wbm2spc_boot_fetch <= 0;
          wbm2spc_pfl <= 0;
          if(spc2wbm_addr[3]==0) wbm2spc_data <= { wbm_data_i, 64'b0 };
          else wbm2spc_data <= { 64'b0, wbm_data_i };

          // See if other 64-bit Wishbone accesses are required
          if(
              // Instruction miss directed to RAM expects 256 bits
              ( (spc2wbm_type==    5'b10000)&&(spc2wbm_region!=5'b10000) ) ||
              // Data access of 128 bits
              ( (spc2wbm_type==     5'b00000)&&(spc2wbm_size==   3'b111  ) )
            )
            state <=    4'b0101;
          else
            state <=     4'b1011;

        end else state <=  4'b0100;

      // FSM State 5: STATE_ACCESS2_BEGIN
      // If needed start a second read access to the Wishbone bus
      end else if(state==   4'b0101) begin

        // Issue a second request on the Wishbone bus
        wbm_cycle_o <= 1;
        wbm_strobe_o <= 1;
        wbm_we_o <= 0;
        wbm_addr_o <= { spc2wbm_region, 19'b0, spc2wbm_addr[       103  -        64:4], 4'b1000 };  // 2nd doubleword inside the same quadword
        wbm_data_o <= 64'b0;
        wbm_sel_o <= 8'b11111111;

        // Unconditional state change
        state <=      4'b0110;

      // FSM State 6: STATE_ACCESS2_END
      // Latch the second data returning from Wishbone when ready
      end else if(state==     4'b0110) begin

        // Wait until Wishbone access completes
        if(wbm_ack_i==1) begin

          // Clear previously modified outputs
          if(spc2wbm_atomic==0) wbm_cycle_o <= 0;
          wbm_strobe_o <= 0;
          wbm_we_o <= 0;
          wbm_addr_o <= 64'b0;
          wbm_data_o <= 64'b0;
          wbm_sel_o <= 8'b0;

          // Latch the data and set up the return packet for the SPARC Core
          wbm2spc_data[63:0] <= wbm_data_i;

          // See if two return packets are required or just one
          if(spc2wbm_type==    5'b10000 && spc2wbm_region==5'b10000)
            state <=     4'b1011;
          else
            state <=    4'b0111;

        end else state <=      4'b0110;

      // FSM State 7: STATE_ACCESS3_BEGIN
      // If needed start a third read access to the Wishbone bus
      // In the meanwhile we can return the first 128-bit packet
      end else if(state==   4'b0111) begin

        // Return the packet to the SPARC Core
        spc_ready_o <= 1;
        spc_packetin_o <= wbm2spc_packet;

        // Issue a third request on the Wishbone bus
        wbm_cycle_o <= 1;
        wbm_strobe_o <= 1;
        wbm_we_o <= 0;
        wbm_addr_o <= { spc2wbm_region, 19'b0, spc2wbm_addr[       103  -        64:5], 5'b10000 };  // 3nd doubleword inside the same 256-bit data
        wbm_data_o <= 64'b0;
        wbm_sel_o <= 8'b11111111;

// synopsys translate_off
        // Print details of return packet
            
        $display("INFO: WBM2SPC: *** RETURN PACKET TO SPARC CORE ***");
        $display("INFO: WBM2SPC: Valid bit is %X", wbm2spc_valid);
        case(wbm2spc_type)
                 4'b0001: $display("INFO: WBM2SPC: Return Packet of Type IFILL_RET");
                  4'b0000: $display("INFO: WBM2SPC: Return Packet of Type LOAD_RET");
                    4'b0100: $display("INFO: WBM2SPC: Return Packet of Type ST_ACK");
          default: $display("INFO: WBM2SPC: Return Packet of Type Unknown");
        endcase
        $display("INFO: WBM2SPC: L2 Miss is %X", wbm2spc_miss);
        $display("INFO: WBM2SPC: Error is %X", wbm2spc_error);
        $display("INFO: WBM2SPC: Non-Cacheable bit is %X", wbm2spc_nc);
        $display("INFO: WBM2SPC: Thread is %X", wbm2spc_thread);
        $display("INFO: WBM2SPC: Way Valid is %X", wbm2spc_way_valid);
        $display("INFO: WBM2SPC: Replaced L2 Way is %X", wbm2spc_way);
        $display("INFO: WBM2SPC: Fetch for Boot is %X", wbm2spc_boot_fetch);
        $display("INFO: WBM2SPC: Atomic LD/ST or 2nd IFill Packet is %X", wbm2spc_atomic);
        $display("INFO: WBM2SPC: PFL is %X", wbm2spc_pfl);
        $display("INFO: WBM2SPC: Data is %X", wbm2spc_data);
      
// synopsys translate_on

        // Unconditional state change
        state <=      4'b1000;

      // FSM State 8: STATE_ACCESS3_END
      // Latch the second data returning from Wishbone when ready
      end else if(state==     4'b1000) begin

        // Clear previously modified outputs
        spc_ready_o <= 0;

        // Wait until Wishbone access completes
        if(wbm_ack_i==1) begin

          // Clear previously modified outputs
          if(spc2wbm_atomic==0) wbm_cycle_o <= 0;
          wbm_strobe_o <= 0;
          wbm_we_o <= 0;
          wbm_addr_o <= 64'b0;
          wbm_data_o <= 64'b0;
          wbm_sel_o <= 8'b0;

          // Latch the data and set up the return packet for the SPARC Core
          wbm2spc_data <= { wbm_data_i, 64'b0 };

          // Jump to next state
          state <=    4'b1001;

        end else state <=      4'b1000;

      // FSM State 9: STATE_ACCESS4_BEGIN
      // If needed start a second read access to the Wishbone bus
      end else if(state==   4'b1001) begin

        // Issue a fourth request on the Wishbone bus
        wbm_cycle_o <= 1;
        wbm_strobe_o <= 1;
        wbm_we_o <= 0;
        wbm_addr_o <= { spc2wbm_region, 19'b0, spc2wbm_addr[       103  -        64:5], 5'b11000 };  // 4th doubleword inside the same 256-bit data
        wbm_data_o <= 64'b0;
        wbm_sel_o <= 8'b11111111;

        // Unconditional state change
        state <=      4'b1010;

      // FSM State 10: STATE_ACCESS4_END
      // Latch the second data returning from Wishbone when ready
      end else if(state==     4'b1010) begin

        // Wait until Wishbone access completes
        if(wbm_ack_i==1) begin

          // Clear previously modified outputs
          if(spc2wbm_atomic==0) wbm_cycle_o <= 0;
          wbm_strobe_o <= 0;
          wbm_we_o <= 0;
          wbm_addr_o <= 64'b0;
          wbm_data_o <= 64'b0;
          wbm_sel_o <= 8'b0;

          // Latch the data and set up the return packet for the SPARC Core
          wbm2spc_atomic <= 1;
          wbm2spc_data[63:0] <= wbm_data_i;

          // Jump to next state
          state <=     4'b1011;

        end else state <=      4'b1010;

      // FSM State 11: STATE_PACKET_READY
      // We can start returning the packet to the SPARC Core
      end else if(state==    4'b1011) begin

        // Return the packet to the SPARC Core
        spc_ready_o <= 1;
        spc_packetin_o <= wbm2spc_packet;

        // Resume requests
        spc_resume_o <= 1;

        // Unconditional state change
        state <=             4'b0001;

// synopsys translate_off
        // Print details of return packet
            
        $display("INFO: WBM2SPC: *** RETURN PACKET TO SPARC CORE ***");
        $display("INFO: WBM2SPC: Valid bit is %X", wbm2spc_valid);
        case(wbm2spc_type)
                 4'b0001: $display("INFO: WBM2SPC: Return Packet of Type IFILL_RET");
                  4'b0000: $display("INFO: WBM2SPC: Return Packet of Type LOAD_RET");
                    4'b0100: $display("INFO: WBM2SPC: Return Packet of Type ST_ACK");
          default: $display("INFO: WBM2SPC: Return Packet of Type Unknown");
        endcase
        $display("INFO: WBM2SPC: L2 Miss is %X", wbm2spc_miss);
        $display("INFO: WBM2SPC: Error is %X", wbm2spc_error);
        $display("INFO: WBM2SPC: Non-Cacheable bit is %X", wbm2spc_nc);
        $display("INFO: WBM2SPC: Thread is %X", wbm2spc_thread);
        $display("INFO: WBM2SPC: Way Valid is %X", wbm2spc_way_valid);
        $display("INFO: WBM2SPC: Replaced L2 Way is %X", wbm2spc_way);
        $display("INFO: WBM2SPC: Fetch for Boot is %X", wbm2spc_boot_fetch);
        $display("INFO: WBM2SPC: Atomic LD/ST or 2nd IFill Packet is %X", wbm2spc_atomic);
        $display("INFO: WBM2SPC: PFL is %X", wbm2spc_pfl);
        $display("INFO: WBM2SPC: Data is %X", wbm2spc_data);
      
// synopsys translate_on

      end
    end
  end

endmodule
module bw_u1_ckbuf_1p5x  (clk, rclk);
        buf (clk, rclk);

    initial begin
        if ($time > (4* 1)) begin
            $display ("MILSTATE",
                      "Error: SPARC/IFU/MILFSM: unknown state! %b\n",milstate);
        end
    end
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

