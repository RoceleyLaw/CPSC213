.pos 0x100
    # a[i] = a[i+1] + b[i+2]
    ld $i, r0           # r0 = address of i
    ld (r0), r1         # r1 = i
    ld $a, r2           # r2 = address of a  do not overwrite
    inc r1              # r1 = i + 1
    ld (r2, r1, 4), r3  # r3 = a[i+1]
    inc r1              # r1 = i + 2
    ld $b, r4           # r4 = address of b do not overwrite
    ld (r4, r1, 4), r1  # r1 = b[i+2]
    add  r3,r1          # r1 = a[i+1] + b[i+2]
    ld (r0),r3          # r3 = i  do not overwrite
    st r1,(r2,r3,4)     # a[i] = a[i+1]+b[i+2]


    # d[i] = a[i]+ b[i]
    ld (r2,r3,4), r5    # r5 = a[i]
    ld (r4,r3,4), r1    # r1 = b[i]
    mov r5, r0          # r0 = a[i]
    add r1, r5          # r5 = a[i] + b[i]
    ld  $d, r6          # r6 = address of d
    ld (r6),r6          # r6 = d
    st  r5, (r6, r3, 4) # d[i] = a[i] + b[i]

    # d[i] = a[b[i]]+b[a[i]]
    ld (r2, r1, 4), r5  # r5 = a[b[i]]
    ld (r4, r0, 4), r0  # r0 = b[a[i]]
    add r5, r0          # r0 = a[b[i]]+b[a[i]]
    st  r0,(r6, r3, 4)  # d[i] = a[b[i]]+b[a[i]]


    # d[b[i]] = b[a[i & 3] & 3] - a[b[i & 3] & 3] + d[i]

    # a[b[i & 3] & 3, d[i]
    ld (r6, r3, 4), r0  #r0 = d[i]
    ld $3, r5           #r5 = 3
    and r5, r3          #r3 = 3 & i
    ld (r4,r3,4),r7     #r7 = b[i & 3]
    and r5,r7           #r7 = 3 & b[i & 3]
    ld (r2,r7,4),r7     #r7 = a[3 & b[i & 3]]

    # b[a[i & 3] & 3]

    ld (r2,r3,4),r3     #r3 = a[3 & i]
    and r5,r3           #r3 = a[3 & i] & 3
    ld (r4,r3,4), r3    #r3 = b[a[3 & i] & 3]


    # d[b[i]] = b[a[i & 3] & 3] - a[b[i & 3] & 3] + d[i]

    not r7              #r7 = ~ a[3 & b[i & 3]]
    inc r7              #r7 = ~a[b[i & 3] & 3]+1;
    add r3, r7          #r7 = b[a[3 & i] & 3] - a[3 & b[i & 3]]
    add r0, r7          #r7 = b[a[3 & i] & 3] - a[3 & b[i & 3]] + d[i]
    st r7, (r6, r1, 4)  #d[b[i]] = b[a[i & 3] & 3] - a[b[i & 3] & 3] + d[i]

    halt

.pos 0x200
# Data area

a:  .long 0             # a[0]
    .long 11            # a[1]
    .long 2             # a[2]
    .long 0             # a[3]
    .long 1             # a[4]
    .long 2             # a[5]
    .long 0             # a[6]
    .long 1             # a[7]
b:  .long 2             # b[0]
    .long 12            # b[1]
    .long 0             # b[2]
    .long 2             # b[3]
    .long 1             # b[4]
    .long 0             # b[5]
    .long 2             # b[6]
    .long 1             # b[7]
c:  .long 99            # c[0]
    .long 6             # c[1]
    .long 2             # c[2]
    .long 1             # c[3]
    .long 0             # c[4]
    .long 2             # c[5]
    .long 1             # c[6]
    .long 0             # c[7]

i:  .long 5             # i
d:  .long c             # *d = c

