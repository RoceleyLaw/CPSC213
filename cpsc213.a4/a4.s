.pos 0x1000
    # v = s.x[i];
    ld $v, r0              # r0 = address of v

    ld $i, r1              # r1 = address of i
    ld (r1), r1            # r1 = i
    ld $s, r2              # r2 = address of s.x
    ld (r2, r1, 4), r3     # r3 = s.x[i]
    st r3, (r0)            # v = s.x[i]

    # v = s.y[i];
    ld 0x8(r2), r3        # r3 = value of what s.y points to - an address
    ld (r3, r1, 4), r3    # r3 = s.y[i]
    st r3, (r0)           # v = s.y[i]


    # v = s.z->x[i]

    ld 0xc(r2), r2         # r2 = value of what s.z points to, an address
    ld (r2, r1, 4), r2     # r2 = s.z -> x[i]
    st r2, (r0)            # v = s.z -> x[i]

    halt


.pos 0x2000
static:
i:               .long 0x1                #i
v:               .long 0x8                #v
s:               .long 0x13               #s.x[0]
                 .long 0x17               #s.x[1]
                 .long 0x3000             #s.y
                 .long 0x3008             #s.z


.pos 0x3000
heap0:           .long 0xd                #s.y[0]
                 .long 0xe                #s.y[1]

heap1:           .long 0x3                #s.z->x[0]
                 .long 0x4                #s.z->x[1]
                 .long 0x0                #s.z->y
                 .long 0x0                #s.z->z
