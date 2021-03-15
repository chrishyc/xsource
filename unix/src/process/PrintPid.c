#include <unistd.h>
#include <stdio.h>
int main(){
        printf("pid: %ld\n",(long)getpid());
        return 0;
}
