package arch.sm213.machine.student;

import arch.sm213.machine.AbstractSM213CPU;
import machine.AbstractMainMemory;
import machine.RegisterSet;
import util.UnsignedByte;


/**
 * The Simple Machine CPU.
 *
 * Simulate the execution of a single cycle of the Simple Machine SM213 CPU. 
 */

public class CPU extends AbstractSM213CPU {
  
  /**
   * Create a new CPU.
   *
   * @param name   fully-qualified name of CPU implementation.
   * @param memory main memory used by CPU.
   */
  public CPU (String name, AbstractMainMemory memory) {
    super (name, memory);
  }
  
  /**
   * Fetch Stage of CPU Cycle.
   * Fetch instruction at address stored in "pc" register from memory into instruction register
   * and set "pc" to point to the next instruction to execute.
   *
   * Input register:   pc.
   * Output registers: pc, instruction, insOpCode, insOp0, insOp1, insOp2, insOpImm, insOpExt
   * @see AbstractSM213CPU for pc, instruction, insOpCode, insOp0, insOp1, insOp2, insOpImm, insOpExt
   *
   * @throws MainMemory.InvalidAddressException when program counter contains an invalid memory address
   */
  @Override protected void fetch() throws MainMemory.InvalidAddressException {
    int            pcVal  = pc.get();
    UnsignedByte[] ins    = mem.read (pcVal, 2);
    byte           opCode = (byte) (ins[0].value() >>> 4);
    insOpCode.set (opCode);
    insOp0.set    (ins[0].value() & 0x0f);
    insOp1.set    (ins[1].value() >>> 4);
    insOp2.set    (ins[1].value() & 0x0f);
    insOpImm.set  (ins[1].value());
    pcVal += 2;
    switch (opCode) {
      case 0x0:
      case 0xb:
	long opExt = mem.readIntegerUnaligned (pcVal);
	pcVal += 4;
	insOpExt.set    (opExt);
	instruction.set (ins[0].value() << 40 | ins[1].value() << 32 | opExt);
	break;
      default:
	insOpExt.set    (0);
	instruction.set (ins[0].value() << 40 | ins[1].value() << 32);
    }
    pc.set (pcVal);
  }

  
  /**
   * Execution Stage of CPU Cycle.
   * Execute instruction that was fetched by Fetch stage.
   *
   * Input state: pc, instruction, insOpCode, insOp0, insOp1, insOp2, insOpImm, insOpExt, reg, mem
   * Ouput state: pc, reg, mem
   * @see AbstractSM213CPU for pc, instruction, insOpCode, insOp0, insOp1, insOp2, insOpImm, insOpExt
   * @see MainMemory       for mem
   * @see machine.AbstractCPU      for reg
   *
   * @throws InvalidInstructionException                when instruction format is invalid.
   * @throws MachineHaltException                       when instruction is the HALT instruction.
   * @throws RegisterSet.InvalidRegisterNumberException when instruction references an invalid register (i.e, not 0-7).
   * @throws MainMemory.InvalidAddressException         when instruction references an invalid memory address.
   */
  @Override protected void execute () throws InvalidInstructionException, MachineHaltException, RegisterSet.InvalidRegisterNumberException, MainMemory.InvalidAddressException
  { int d,v,p,i,s;
  
    switch (insOpCode.get()) {
      case 0x0: // ld $i, d .............. 0d-- iiii iiii
    	  //load immediate; r[d] <- v;0d
        reg.set (insOp0.get(), insOpExt.get());
        break;
      case 0x1: // ld o(rs), rd .......... 1psd  (p = o / 4)
    	  //load base+offset; r[d] <- m[4*p + r[s]] ;1psd
        // TODO
    	  p = insOp0.get();
    	  s = insOp1.get();
    	  d = insOp2.get();
    	  
    	  reg.set(d, mem.readInteger(4*p+reg.get(s)));
    	  
        break;
      case 0x2: // ld (rs, ri, 4), rd .... 2sid
    	  //load indexed; r[d] <- m[r[s]+4*r[i]];2sid
        // TODO
    	  s = insOp0.get();
    	  i = insOp1.get();
    	  d = insOp2.get();
    	  
    	  reg.set(d, mem.readInteger(reg.get(s)+4*reg.get(i)));
    	  
    	  
        break;
      case 0x3: // st rs, o(rd) .......... 3spd  (p = o / 4)
    	  //store base+offset; m[4*p+r[d]] <- r[s];3spd
    	  // TODO
    	  s = insOp0.get();
    	  p = insOp1.get();
    	  d = insOp2.get();
    	  
    	  mem.writeInteger(4*p+reg.get(d), reg.get(s));
    	  
    	  
        break;
      case 0x4: // st rs, (rd, ri, 4) .... 4sdi
        //store indexed; m[r[d]+4*r[i]] <- r[s];4sdi
    	  // TODO
    	  s = insOp0.get();
    	  d = insOp1.get();
    	  i = insOp2.get();
    	  
    	  mem.writeInteger(reg.get(d)+4*reg.get(i), reg.get(s));
    	  
        break;
      case 0x6: // ALU ................... 6-sd
	switch (insOp0.get()) {
	  case 0x0: // mov rs, rd ........ 60sd
		  //register move r[d] <- r[s]
            // TODO
		 
    	  s = insOp1.get();
    	  d = insOp2.get();
    	  
    	  reg.set(d, reg.get(s));
    	  
	    break;
	  case 0x1: // add rs, rd ........ 61sd
            // TODO
		  //add r[d]<-r[d] +r[s]
		  
		 
    	  s = insOp1.get();
    	  d = insOp2.get();
    	  
    	  reg.set(d, reg.get(d)+reg.get(s));
	    break;
	  case 0x2: // and rs, rd ........ 62sd
            // TODO
		  //and r[d] <- r[d]&r[s]
		  
		
    	  s = insOp1.get();
    	  d = insOp2.get();
    	  
    	  reg.set(d,reg.get(d)&reg.get(s));
	    break;
	  case 0x3: // inc rr ............ 63-r
            // TODO
		  //inc 
    	  d = insOp2.get();
    	  
    	  reg.set(d,reg.get(d)+1);
		  
	    break;
	  case 0x4: // inca rr ........... 64-r
            // TODO
		  //inc address
		  d = insOp2.get();
    	  
    	  reg.set(d,reg.get(d)+4);
	    break;
	  case 0x5: // dec rr ............ 65-r
            // TODO
		  //dec
		  d = insOp2.get();
    	  
    	  reg.set(d,reg.get(d)-1);
	    break;
	  case 0x6: // deca rr ........... 66-r
            // TODO
		  //dec address
		  d = insOp2.get();
    	  
    	  reg.set(d,reg.get(d)-4);
	    break;
	  case 0x7: // not ............... 67-r
            // TODO
		  //not
		  d = insOp2.get();
    	  
    	  reg.set(d,~reg.get(d));
	    break;
	  default:
	    throw new InvalidInstructionException();
	}
	break;
      case 0x7: // sh? $i,rd ............. 7dii
        // TODO
    	  //shift left
    	  d = insOp0.get();
    	  i = insOpImm.get();
    	  
    	  if(i > 0)
    		  reg.set(d,reg.get(d) <<i);
    	  else
    		  reg.set(d,reg.get(d) >> 0-i);
        break;
      case 0xf: // halt or nop ............. f?--
	if (insOp0.get() == 0)
	  // halt .......................... f0--
	  throw new MachineHaltException();
	else if (insOp0.getUnsigned() == 0xf)
	  // nop ........................... ff--
	  break;
        break;
      default:
        throw new InvalidInstructionException();
    }
  }
}
