#include<stdio.h>
#include"file_to_str.c"

int main(int argv, char *args[])
{
    char *str;
    int i;
    for(i=1; i < argv; i++)
    {
        if(!(str = file_to_str(args[i])))
            return 0;
        PRINTS(str);
    }
}
