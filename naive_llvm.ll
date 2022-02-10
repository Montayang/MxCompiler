; ModuleID = 'F:\Programming\MxCompiler\test.mx'
source_filename = "F:\Programming\MxCompiler\test.mx"
target datalayout = "e-m:e-p270:32:32-p271:32:32-p272:64:64-i64:64-f80:128-n8:16:32:64-S128"
target triple = "x86_64-pc-linux-gnu"



declare i1 @_str_ne(i8* %lhs,i8* %rhs)
declare i1 @_str_le(i8* %lhs,i8* %rhs)
declare i8* @_str_substring(i8* %str,i32 %left,i32 %right)
declare i8* @_str_concatenate(i8* %lhs,i8* %rhs)
declare i8* @getString()
declare i1 @_str_ge(i8* %lhs,i8* %rhs)
declare i1 @_str_lt(i8* %lhs,i8* %rhs)
declare i32 @getInt()
declare i32 @_str_length(i8* %str)
declare void @print(i8* %str_0)
declare void @println(i8* %str_0)
declare i32 @_str_ord(i8* %str,i32 %pos)
declare i8* @_f_malloc(i32 %n)
declare void @printInt(i32 %n_0)
declare void @printlnInt(i32 %n_0)
declare i8* @toString(i32 %i_0)
declare i32 @_str_parseInt(i8* %str)
declare i1 @_str_eq(i8* %lhs,i8* %rhs)
declare i1 @_str_gt(i8* %lhs,i8* %rhs)

define dso_local i32 @main() {
entrance_block0:                                             
    call void @GLOBAL__sub_I_main_mx()
    %array_addr_1 = alloca i32**
    %current_array_ptr_addr = alloca i32**
    %return_register_infunction_addr = alloca i32
    %mul_bytes = mul i32 3, 8
    %sum_bytes = add i32 %mul_bytes, 4
    %malloca = call i8* @_f_malloc(i32 %sum_bytes)
    %array_cast_i8_to_i32 = bitcast i8* %malloca to i32*
    store i32 3, i32* %array_cast_i8_to_i32
    %array_tmp_begin_i32 = getelementptr inbounds i32, i32* %array_cast_i8_to_i32, i32 1
    %array_addr = bitcast i32* %array_tmp_begin_i32 to i32**
    %array_tail_addr = getelementptr inbounds i32*, i32** %array_addr, i32 3
    store i32** %array_addr, i32*** %current_array_ptr_addr
    br label %new_condition

new_condition:                                               ; preds = new_loop_body entrance_block0 
    %load_tmp_current_pointer = load i32**, i32*** %current_array_ptr_addr
    %addr_cmp_result = icmp slt i32** %load_tmp_current_pointer, %array_tail_addr
    br i1 %addr_cmp_result, label %new_loop_body, label %new_end

new_loop_body:                                               ; preds = new_condition 
    %mul_bytes_0 = mul i32 2, 4
    %sum_bytes_0 = add i32 %mul_bytes_0, 4
    %malloca_0 = call i8* @_f_malloc(i32 %sum_bytes_0)
    %array_cast_i8_to_i32_0 = bitcast i8* %malloca_0 to i32*
    store i32 2, i32* %array_cast_i8_to_i32_0
    %array_tmp_begin_i32_0 = getelementptr inbounds i32, i32* %array_cast_i8_to_i32_0, i32 1
    %array_addr_0 = bitcast i32* %array_tmp_begin_i32_0 to i32*
    store i32* %array_addr_0, i32** %load_tmp_current_pointer
    %nxt_pointer = getelementptr inbounds i32*, i32** %load_tmp_current_pointer, i32 1
    store i32** %nxt_pointer, i32*** %current_array_ptr_addr
    br label %new_condition

new_end:                                                     ; preds = new_condition 
    store i32** %array_addr, i32*** %array_addr_1
    br label %return_block0

return_block0:                                               ; preds = new_end 
    %returnval = load i32, i32* %return_register_infunction_addr
    ret i32 %returnval
}
define dso_local void @GLOBAL__sub_I_main_mx() {
entrance_block0:                                             
    br label %return_block0

return_block0:                                               ; preds = entrance_block0 
    ret void
}
