.pos 0x100
    ld $val, r0         #r0 = address of val
    ld $i, r1           #r1 = address of i
    ld $j, r2           #r2 = address of j
    ld (r1), r1         #r1 = i
    ld (r2), r2         #r2 = j
    ld(r0,r1,4), r4     #r4 = val[i] (r4 = t)
    ld (r0, r2, 4), r3  #r3 = val[j]
    st r3 ,(r0, r1, 4)  #val[i] = val[j]
    st r4, (r0, r2, 4)  #val[j] = r4 (val[i])

    halt

.pos 0x200
# Data area

i:  .long 1             # i
j:  .long 2             # j

val:.long 0             # val[0]
    .long 10            # val[1]
    .long 20            # val[2]
    .long 0             # val[3]

