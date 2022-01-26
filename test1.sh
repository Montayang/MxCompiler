clang-10 -S -emit-llvm builtin.c -o builtin.ll -O0
clang-10 builtin.ll naive_llvm.ll -o code
#./code < std.in >std.out
#./code < std.in
./code
rm code