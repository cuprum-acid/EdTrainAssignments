/*Program for alphabetical sorting of names from a file with input conditions. Made by Eugene Bobkunov*/

//Connecting standard libraries.
#include <stdio.h>
#include <string.h>
#include<stdlib.h>
#include<ctype.h>
#define TWO_HUNDRED 200

/*The function of sorting strings by the bubble method.
The input data is the number of strings to sort and the maximum length of each string, as well as an array of strings.
This function consistently compare the values of neighboring elements and swap the string if the previous one is
"greater" than the next one.
Comparison and rearrangement of strings is performed by a standard function and with the help of a temporary variable.*/
void sorting_names_bubble_method(int howManyStrings, int lineLength, char strings[lineLength][lineLength]){
    int start;
    int swaps = 1;
    while(swaps > 0){
        start = 0;
        swaps = 0;
        while (start + 1 < howManyStrings){
            if (strcmp(strings[start], strings[start + 1])>0){
                char tmp[lineLength];
                strcpy(tmp,strings[start]);
                strcpy(strings[start],strings[start + 1]);
                strcpy(strings[start + 1],tmp);
                start++;
                swaps++;
            }else
                start++;
        }
    }
    return;
}

//The main function in which working with files and checking the conditions of the task takes place.
int main() {
    //Opening files in several modes "r"- for reading and "w"- for writing.
    FILE *in = fopen("input.txt", "r");
    FILE *out = fopen("output.txt", "w");

    char arr[TWO_HUNDRED][TWO_HUNDRED];
    int len=0;
    int numberOfString;
    char firstSymbol[TWO_HUNDRED];
    int condition=0;

    //Reading lines from a file into an array.
    while (!feof(in)){
        fscanf(in,"%s",arr[len]);
        printf("%s\n",arr[len]);
        len++;
    }

    //We do not count the digit at the very beginning as a string, so we subtract one.
    numberOfString = len-1;
    //Now we convert it to a string so that we can compare it with the number that is in the file itself.
    itoa(numberOfString-1,firstSymbol,10);
    //We check the first number in two ways and check the range in which the number is located.
    //If one of the conditions is not met, then the counter value changes and an input error appears.
    if ((strcmp (arr[0], firstSymbol)!=0)||(numberOfString>101)||(numberOfString<1)
    ||(atoi(arr[0])!=numberOfString-1)||(atoi(arr[0])<1)||(atoi(arr[0])>101)) condition=1;

    //This loop checks each name in the array for compliance with the standard:
    //The first letter-uppercase;
    //The rest- lowercase
    for(int len=1; len<numberOfString;len++){
        if(isupper(arr[len][0])==0) condition=1;
        for(int zen=1; zen<(strlen(arr[len]));zen++){
            if(islower(arr[len][zen])==0) condition=1;
        }
    }

    //Applying the function to the array.
    sorting_names_bubble_method(numberOfString,TWO_HUNDRED,arr);

    //Checking the condition counter and writing the result of the sorted array to the output file if
    // successful, otherwise writing an error.
    if (condition==1){
        fprintf(out,"Error in the input.txt");
    }else{
        for(int i=1; i<numberOfString;i++) {
            fprintf(out,"%s\n",arr[i]);
        }
    }
    //Closing files after working with them
    fclose(in);
    fclose(out);
    return 0;
}
