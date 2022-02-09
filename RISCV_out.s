    .text

    .globl main					# start function : main
    .p2align	2
main:
.LBB_main_entrance_block0:
    addi	sp,sp,-28
    sw	ra,24(sp)
    sw	s0,20(sp)
    addi	s0,sp,28
    call	GLOBAL__sub_I_main_mx
    lw	%n,n
    call	getInt
    mv	call_getInt,a0
    la	la,n
    sw	call_getInt,0(la)
    lw	%p,p
    call	getInt
    mv	call_getInt_0,a0
    la	la,p
    sw	call_getInt_0,0(la)
    lw	%k,k
    call	getInt
    mv	call_getInt_1,a0
    la	la,k
    sw	call_getInt_1,0(la)
    lw	%p_0,p
    lw	%k_0,k
    sub	sub,%p_0,%k_0
    li	virtual_reg_const_li,1
    sgt	sgt,sub,virtual_reg_const_li
    beqz	sgt,	.LBB_main_if_withoutelse_end_basicblock
    j	.LBB_main_single_then_basicblock
.LBB_main_single_then_basicblock:
    la	tmp_str_addrreg,const_string0
    mv	a0,tmp_str_addrreg
    call	print
    j	.LBB_main_if_withoutelse_end_basicblock
.LBB_main_if_withoutelse_end_basicblock:
    lw	%i,i
    lw	%p_1,p
    lw	%k_1,k
    sub	sub_0,%p_1,%k_1
    la	la,i
    sw	sub_0,0(la)
    j	.LBB_main_for_condition
.LBB_main_for_body:
    lw	%i_1,i
    li	virtual_reg_const_li,1
    sgt	sle_0,virtual_reg_const_li,%i_1
    xori	sle_0,sle_0,1
    sw	sle_0,-12(s0)
    beqz	sle_0,	.LBB_main_short_circuit_AND_end_AND
    j	.LBB_main_short_circuit_AND_branch_AND
.LBB_main_for_end_merge:
    lw	%p_4,p
    lw	%k_3,k
    add	add_1,%p_4,%k_3
    lw	%n_1,n
    slt	slt,add_1,%n_1
    beqz	slt,	.LBB_main_if_withoutelse_end_basicblock1
    j	.LBB_main_single_then_basicblock1
.LBB_main_for_condition:
    lw	%i_0,i
    lw	%p_2,p
    lw	%k_2,k
    add	add,%p_2,%k_2
    sgt	sle,%i_0,add
    xori	sle,sle,1
    beqz	sle,	.LBB_main_for_end_merge
    j	.LBB_main_for_body
.LBB_main_for_step:
    lw	%i_6,i
    li	virtual_reg_const_li,1
    add	add_0,%i_6,virtual_reg_const_li
    la	la,i
    sw	add_0,0(la)
    j	.LBB_main_for_condition
.LBB_main_short_circuit_AND_end_AND:
    lw	%AND_short_circuit,-12(s0)
    beqz	%AND_short_circuit,	.LBB_main_if_withoutelse_end_basicblock0
    j	.LBB_main_single_then_basicblock0
.LBB_main_short_circuit_AND_branch_AND:
    lw	%i_2,i
    lw	%n_0,n
    sgt	sle_1,%i_2,%n_0
    xori	sle_1,sle_1,1
    sw	sle_1,-12(s0)
    j	.LBB_main_short_circuit_AND_end_AND
.LBB_main_single_then_basicblock0:
    lw	%i_3,i
    lw	%p_3,p
    xor	sub_virtual_reg,%i_3,%p_3
    seqz	eq,sub_virtual_reg
    beqz	eq,	.LBB_main_else_basicblock
    j	.LBB_main_then_basicblock
.LBB_main_if_withoutelse_end_basicblock0:
    j	.LBB_main_for_step
.LBB_main_then_basicblock:
    la	tmp_str_addrreg,const_string1
    mv	a0,tmp_str_addrreg
    call	print
    lw	%i_4,i
    mv	a0,%i_4
    call	toString
    mv	call_toString,a0
    mv	a0,call_toString
    call	print
    la	tmp_str_addrreg,const_string2
    mv	a0,tmp_str_addrreg
    call	print
    j	.LBB_main_if_end_basicblock
.LBB_main_else_basicblock:
    lw	%i_5,i
    mv	a0,%i_5
    call	printInt
    la	tmp_str_addrreg,const_string3
    mv	a0,tmp_str_addrreg
    call	print
    j	.LBB_main_if_end_basicblock
.LBB_main_if_end_basicblock:
    j	.LBB_main_if_withoutelse_end_basicblock0
.LBB_main_single_then_basicblock1:
    la	tmp_str_addrreg,const_string4
    mv	a0,tmp_str_addrreg
    call	print
    j	.LBB_main_if_withoutelse_end_basicblock1
.LBB_main_if_withoutelse_end_basicblock1:
    sw	zero,-16(s0)
    j	.LBB_main_return_block0
.LBB_main_return_block0:
    lw	%returnval,-16(s0)
    mv	a0,%returnval
    lw	s0,20(sp)
    lw	ra,24(sp)
    addi	sp,sp,28
    ret
# end function : main

    .globl GLOBAL__sub_I_main_mx					# start function : GLOBAL__sub_I_main_mx
    .p2align	2
GLOBAL__sub_I_main_mx:
.LBB_GLOBAL__sub_I_main_mx_entrance_block0:
    addi	sp,sp,-20
    sw	ra,16(sp)
    sw	s0,12(sp)
    addi	s0,sp,20
    j	.LBB_GLOBAL__sub_I_main_mx_return_block0
.LBB_GLOBAL__sub_I_main_mx_return_block0:
    mv	a0,zero
    lw	s0,12(sp)
    lw	ra,16(sp)
    addi	sp,sp,20
    ret
# end function : GLOBAL__sub_I_main_mx


	.section	.sdata
	.p2align	2
	.globl	p
p:
	.word	0

	.p2align	2
	.globl	i
i:
	.word	0

	.p2align	2
	.globl	k
k:
	.word	0

	.p2align	2
	.globl	n
n:
	.word	0


	.section	.rodata
	.p2align	2
const_string3:
	.string	" "

	.section	.rodata
	.p2align	2
const_string1:
	.string	"("

	.section	.rodata
	.p2align	2
const_string2:
	.string	") "

	.section	.rodata
	.p2align	2
const_string0:
	.string	"<< "

	.section	.rodata
	.p2align	2
const_string4:
	.string	">> "

