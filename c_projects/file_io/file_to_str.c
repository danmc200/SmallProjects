#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

#define PRINTS(x) printf("%s\n", x)
#define BUFFER_SIZE  10000

//returns 0 if unable to open file 
char * file_to_str(char *filename)
{
    char buffer [BUFFER_SIZE];
    FILE *file;

    if(!(file = fopen(filename, "r")))
    {
        printf("%s: %s\n", strerror(errno), filename);
        return 0;
    }
    int c, i;
    for(i=0; (c = fgetc(file)) != EOF; buffer[i++] = c);
    buffer[i] = '\0';

    int len = (int)strlen(buffer) + 1;
    char *str = malloc(len * sizeof(char));
    strcpy(str, buffer);

    return str;
}
