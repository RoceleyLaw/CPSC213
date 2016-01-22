.pos 0x100

            ld   $0x1000, r0            # r0 = 1000
            ld   $0x1500, r1            # r1 = 1500
            ld   $b, r2                 # r2 = address of b
            ld   $a, r3                 # r3 = address of a


            st  r0,(r3)                 # a = 1000 (r3 = r0)
            st r3,(r2,r3,4)             # b[a] = a (r2[r3] = r3)
            mov r0,r4                   # r4 = r0 = 1000
            and r0, r1                  # r1 = r0 & r1 = 1000
            add r0,r1                   # r1 = r0+r1 = 2000
            inc r0                      # r0 = r0 +1 = 1001
            dec r1                      # r1 = r1-1 = 1fff
            inca r0                     # r0 = r0 + 4 =1005
            deca r0                     # r0 = r0 - 4 = 1001
            ld $0x1, r5                 # r5 = 1
            not r5                      # r5 = 0xfffffffe
            ld $0x1, r6                 # r6 = 1
            shl $0x1,r6                 # r6 = 1 << 1 =2
            shr $0x1, r6                # r6 = 2 >> 1 = 1
            nop                         # do nothing
            halt                        # throw halt exception

.pos 0x1000
a:               .long 0xffffffff         # a
.pos 0x2000
b:               .long 0x00003000         # address of b[0]; loaded dynamically
                 halt                     # throw halt exception
