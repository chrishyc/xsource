;hello.asm
;write(int fd, const void *buffer, size_t nbytes)
;fd 文件描述符 file descriptor - linux下一切皆文件
​
section data
    msg db "Hello", 0xA
    len equ $ - msg
​
section .text
global _start
_start:
​
    mov edx, len
    mov ecx, msg
    mov ebx, 1 ;文件描述符1 std_out
    mov eax, 4 ;write函数系统调用号 4，先存入寄存器然后进行80中断
    int 0x80
​
    mov ebx, 0
    mov eax, 1 ;exit函数系统调用号
    int 0x80
