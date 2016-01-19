#include <stdio.h>
#include <stdlib.h>
#include <string.h>

double celcius_to_ferinheight(double celcius)
{
    return celcius * (9/5.0) + 32;
}

double ferinheight_to_celcius(double ferinheight)
{
    return (ferinheight-32) * (5.0/9);
}

int main(int argc, char * args[])
{
    int i = 0;
    if(argc == 1)
    {
        return -1;
    }
    else if(argc == 3)
    {
        if (strcmp(args[1], "-c") == 0)
        {
           printf("%ff\n", celcius_to_ferinheight(atof(args[2]))); 
        }
        else if (strcmp(args[1], "-f") == 0)
        {
           printf("%fc\n", ferinheight_to_celcius(atof(args[2]))); 
        }
    }
}
