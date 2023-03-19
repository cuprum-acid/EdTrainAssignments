/* Program for playing a magic battle. Finding the best wizard team! by Eugene Bobkunov */
// Connecting standard libraries
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

// Creating a structure that will store data about players: their name, team, power, and visibility
struct players{
    char name[300];
    int team;
    int power;
    char visibility[300];
};

int main(){
    // Opening input file for reading
    FILE *in = fopen("input.txt", "r");
    // Opening output file for writing
    FILE *out = fopen("output.txt", "w");

    // Creating an array into which input file is written
    char arr[5000][300];
    // Length of the array
    int len=0;
    int numberOfWizards;
    int mNumberOfPlayers;
    /* This variable is needed to write information about the player from the array to the structure.
     * The player has 4 characteristics that are repeated through 4 lines. Therefore, a quad variable is needed */
    int qadro = 0;
    /* This variable plays the role of a point, indicating from what point in the array the players begin to go,
     * so that they can be written to the structure */
    int inToStruct;
    // A flag that changes the value if the input conditions are violated
    int invalidInputs = 0;
    /* The elements of this array do not change and contain duplicates of the names of the players.
     * This is necessary to remember the names of the players who formed the super player */
    char noSArr[1800][300];

     // Reading the input file into an array
    while (!feof(in)) {
        fscanf(in, "%s", arr[len]);
        len++;
    }

    // Check the conditions
    if (strlen(arr[0])>2)   invalidInputs=1;

    // Check if first line is a number
    for (int i = 0; i < strlen(arr[0]); i++) {
        if (!isdigit(arr[0][i]))    invalidInputs=1;
    }

    // Check if first digit is not 0 (We can not have numbers like 01, 02, 03, etc.)
    if (arr[0][0]=='0') invalidInputs=1;

    // Convert very first line to int
    numberOfWizards = atoi(arr[0]);
    // 1≤N≤10 condition
    if (numberOfWizards < 1 || numberOfWizards > 10)    invalidInputs=1;
    // Intermediate check and write to the output file
    if (invalidInputs==1){
        fprintf(out, "Invalid inputs\n");
        fclose(in);
        fclose(out);
        return 0;
    }

    // Check the conditions for the names of wizards
    for (int i = 1; i < numberOfWizards+1; i++){
        // (2≤L≤20) condition
        if (strlen(arr[i])<2 || strlen(arr[i])>20){
            invalidInputs=1;
        }
        // Begin with capital letter
        if (isupper(arr[i][0])==0) invalidInputs=1;
        // Check if the name contains only English letters
        for (int zen = 1; zen < strlen(arr[i]); zen++) {
            if (isalpha(arr[i][zen]) == 0)  invalidInputs = 1;
        }
        // Checking for a unique wizard name
        for(int j=i+1; j<numberOfWizards+2; j++){
            if (strcmp(arr[i], arr[j])==0) invalidInputs=1;
        }
        inToStruct=i;
    }
    inToStruct+=2;

    mNumberOfPlayers = atoi(arr[numberOfWizards+1]);
    // Check the conditions for the number of players
    // (consists of digits)
    for (int i = 0; i < strlen(arr[numberOfWizards+1]); i++) {
        if (!isdigit(arr[numberOfWizards+1][i]))   invalidInputs=1;
    }
    // Check if first digit is not 0 (We can not have numbers like 01, 02, 03, etc.)
    if (arr[numberOfWizards+1][0]=='0') invalidInputs=1;
    // (N≤M≤100) condition
    if (mNumberOfPlayers < numberOfWizards || mNumberOfPlayers > 100) invalidInputs=1;

    // Creating an array P for a struct
    struct players P[mNumberOfPlayers];
    // Fill struct array with players
    for (int i = 0; i < mNumberOfPlayers; i++){
        // Writing the name of the player from the array to the structure
        strcpy(P[i].name, arr[inToStruct + qadro]);
        strcpy(noSArr[i], P[i].name);
        // (2≤L≤20) condition
        if (strlen(P[i].name)<2 || strlen(P[i].name)>20)   invalidInputs=1;
        // Begin with capital letter
        if (isupper(P[i].name[0])==0)   invalidInputs=1;
        // Writing the player's team from the array to the structure
        P[i].team = atoi(arr[inToStruct + 1 + qadro]);
        // (0 ≤ teamNumber < N) condition
        if (P[i].team < 0 || P[i].team >= numberOfWizards)  invalidInputs=1;
        // Is the team a number (consists of digits) condition
        for (int zap = 0; zap < strlen(arr[inToStruct + 1 + qadro]); zap++) {
            if (!isdigit(arr[inToStruct + 1 + qadro][zap]))  invalidInputs=1;
        }
        // Writing the player's power from the array to the structure
        P[i].power = atoi(arr[inToStruct + 2 + qadro]);
        // (0 ≤ power ≤ 1000) condition
        if (P[i].power < 0 || P[i].power > 1000)   invalidInputs=1;

        // Is the power a number (consists of digits) condition
        for (int zap = 0; zap < strlen(arr[inToStruct + 2 + qadro]); zap++) {
            if (!isdigit(arr[inToStruct + 2 + qadro][zap]))  invalidInputs=1;
        }
        // Writing the player's visibility from the array to the structure
        strcpy(P[i].visibility, arr[inToStruct + 3 + qadro]);
        // Visibility only has two values: "True" or "False"
        if ((strcmp(P[i].visibility, "True") !=0 ) && ( strcmp(P[i].visibility, "False") != 0)) invalidInputs=1;
        // Increase the qadro variable by 4, since the player has 4 characteristics
        qadro=qadro+4;
    }

    // This variable shows where the players' actions start from
    int actions= (mNumberOfPlayers*4)+numberOfWizards+2;
    // This array contains string values of numbers from 0 : "0", "1", "2", "3", etc.
    // It is used to create the super player's name like S_0 S_1 ...
    char add[mNumberOfPlayers][5000];

    // Fill the array with string values of numbers
    for (int i = 0; i < mNumberOfPlayers; i++){
        itoa(i, add[i], 10);
    }
    int l=0;

    // Intermediate check and write to the output file
    if (invalidInputs==1){
        fprintf(out, "Invalid inputs\n");
        fclose(in);
        fclose(out);
        return 0;
    }

    // Sequence of actions for players S (0≤S≤1000)
    // This variable is a counter for the number of actions
    int sequenceOfActions=0;

    for (int i = actions; i < len-1; i++){
        int currentAction = 0;
        // Choosing an action
        if (strcmp(arr[i], "attack") == 0) currentAction = 1;
        if (strcmp(arr[i], "flip_visibility") == 0) currentAction = 2;
        if (strcmp(arr[i], "heal") == 0) currentAction = 3;
        if (strcmp(arr[i], "super") == 0) currentAction = 4;
        // If the action is not one of the four, then the input is invalid
        // It is a raised flag
        int actChk=1;
        if ((strcmp(arr[i], "attack") == 0) || (strcmp(arr[i], "flip_visibility") == 0) || (strcmp(arr[i], "heal") == 0) || (strcmp(arr[i], "super") == 0)){
            // Lowering the flag if the condition is met
            actChk=0;
        }
        // Check even the super players names
        char sUnder[2900]="S_";
        for (int j = 0; j < mNumberOfPlayers; j++){
            strcpy(sUnder, "S_");
            strcat(sUnder, add[j]) ;
            // Check default players names
            if ((strcmp(arr[i], P[j].name) == 0 || strcmp(arr[i], noSArr[j]) == 0)) {
                actChk=0;
            }
            // Check super players names
            if ((strcmp(arr[i], sUnder) == 0)){
                actChk=0;
            }
        }
        // If the action is not valid, then the program will write "Invalid inputs" to the output file
        if (actChk==1){
            fclose(in);
            fclose(out);
            FILE *invalidOut = fopen("output.txt", "w");
            fprintf(invalidOut, "Invalid inputs\n");
            fclose(invalidOut);
            return 0;
        }

        // If the action is "attack"
        if (currentAction == 1) {
            // Count number of actions (0≤S≤1000)
            sequenceOfActions+=1;
            int attackBegins=1;
            // The names of the players must match and generally exist
            for (int f = 0; f < mNumberOfPlayers; f++) {
                // Check does such a name of player [1] even exist
                if (strcmp(P[f].name, arr[i + 1]) == 0) {
                    for (int s = 0; s < mNumberOfPlayers; s++) {
                        // Check name of player [2]
                        if (strcmp(P[s].name, arr[i + 2]) == 0) {
                            // If the player is invisible, then the attack is not possible
                            if (strcmp(P[f].visibility, "False") == 0) {
                                fprintf(out, "This player can't play\n");
                                attackBegins=0;
                                break;
                            }
                            // If the power is zero (player is frozen), then the attack is not possible
                            if (P[f].power == 0) {
                                fprintf(out, "This player is frozen\n");
                                attackBegins=0;
                                break;
                            }
                            // Attack rules
                            if (attackBegins == 1) {
                                // If the first player attacks the second player which is invisible, then the first player looses power to 0
                                if (strcmp(P[s].visibility, "False") == 0) {
                                    P[f].power = 0;
                                } else if (P[f].power > P[s].power) {
                                    P[f].power =P[f].power + (P[f].power - P[s].power);
                                    P[s].power = 0;
                                } else if (P[s].power > P[f].power) {
                                    P[s].power = P[s].power + (P[s].power - P[f].power);
                                    P[f].power = 0;
                                }
                                // If the power of the players is equal, then both players loose power to 0
                                else if (P[f].power == P[s].power) {
                                    P[f].power = 0;
                                    P[s].power = 0;
                                }
                                // Player's power can not be more than 1000
                                if (P[f].power > 1000) P[f].power = 1000;
                                if (P[s].power > 1000) P[s].power = 1000;
                            }
                        }
                    }
                }
            }
        }
        // If the action is "flip_visibility"
        if (currentAction == 2) {
            // Count actions
            sequenceOfActions+=1;
            for (int f = 0; f < mNumberOfPlayers; f++) {
                if (strcmp(P[f].name, arr[i + 1]) == 0) {
                    // Change the visibility of the player if the player is not frozen
                    if (P[f].power != 0) {
                        if (strcmp(P[f].visibility, "True") == 0) {
                            strcpy(P[f].visibility, "False");
                        } else if (strcmp(P[f].visibility, "False") == 0) {
                            strcpy(P[f].visibility, "True");
                        }
                    } else {
                        fprintf(out,"This player is frozen\n");
                    }
                }
            }
        }
        // If the action is "heal"
        if (currentAction == 3) {
            // Count actions
            sequenceOfActions+=1;
            int healBegins=1;
            for (int f = 0; f < mNumberOfPlayers; f++) {
                if (strcmp(P[f].name, arr[i + 1]) == 0) {
                    for (int s = 0; s < mNumberOfPlayers; s++) {
                        if (strcmp(P[s].name, arr[i + 2]) == 0) {
                            if (strcmp(P[f].visibility, "False") == 0) {
                                fprintf(out,"This player can't play\n");
                                healBegins = 0;
                                break;
                            }
                            if (P[f].power == 0) {
                                fprintf(out,"This player is frozen\n");
                                healBegins = 0;
                                break;
                            }
                            // Player can not heal player from other team
                            if (P[f].team != P[s].team) {
                                fprintf(out,"Both players should be from the same team\n");
                                healBegins = 0;
                                break;
                            }
                            if (P[f].name == P[s].name) {
                                fprintf(out,"The player cannot heal itself\n");
                                healBegins = 0;
                                break;
                            }
                            // Player shares half of his power with the other player
                            if (healBegins == 1) {
                                if (P[f].power % 2 == 0) {
                                    P[s].power = P[s].power + P[f].power / 2;
                                    P[f].power = P[f].power / 2;
                                    if (P[s].power > 1000) P[s].power = 1000;

                                }
                                else {
                                    P[s].power = P[s].power + P[f].power / 2 + 1;
                                    P[f].power = (P[f].power / 2)+1;
                                    if (P[s].power > 1000) P[s].power = 1000;
                                }
                            }
                        }
                    }
                }
            }
        }
        // If the action is "super"
        if (currentAction == 4) {
            // Count actions
            sequenceOfActions+=1;
            int superBegins=1;
            for (int f = 0; f < mNumberOfPlayers; f++) {
                if (strcmp(P[f].name, arr[i + 1]) == 0) {
                    for (int s = 0; s < mNumberOfPlayers; s++) {
                        if (strcmp(P[s].name, arr[i + 2]) == 0) {
                            if (strcmp(P[f].visibility, "False") == 0) {
                                fprintf(out,"This player can't play\n");
                                superBegins = 0;
                                break;
                            }
                            if (P[f].power == 0) {
                                fprintf(out,"This player is frozen\n");
                                printf("%s player is frozen (dead)\n", P[f].name);
                                superBegins = 0;
                                break;
                            }
                            if (P[f].team != P[s].team) {
                                fprintf(out,"Both players should be from the same team\n");
                                superBegins = 0;
                                break;
                            }
                            if (P[f].name == P[s].name) {
                                fprintf(out,"The player cannot create a super player from itself\n");
                                superBegins = 0;
                                break;
                            }
                            // Creating Super Player from two players with the same team
                            if (superBegins == 1) {
                                // Replace the first player with a super
                                strcpy(P[f].name, "S_");
                                // Add its index-number
                                strcat(P[f].name, add[l]);
                                // Sum the power of the two players but not more than 1000
                                P[f].power = P[f].power + P[s].power;
                                // Set visibility to true
                                strcpy(P[f].visibility, "True");
                                if (P[f].power > 1000) P[f].power = 1000;
                                // Reset the second player. It does not exist anymore.
                                P[s].power = 0;
                                strcpy(P[s].name, "");
                                // Increase index-number for next super player
                                l++;
                            }
                        }
                    }
                }
            }
        }
    }
    // Check number of actions (0≤S≤1000)
    if (sequenceOfActions > 1000) {
        // Reopen files to delete previous data
        fclose(in);
        fclose(out);
        FILE *invalidOut = fopen("output.txt", "w");
        fprintf(invalidOut, "Invalid inputs\n");
        fclose(invalidOut);
        return 0;
    }

    // Find the team with the most power
    // Array stores the result of each team
    int teams[numberOfWizards];
    // Fill the array with zeros
    for (int i = 0; i < numberOfWizards; i++) {
        teams[i] = 0;
    }

    // Fill the array with the sum of all the powers of the players of the same team
    for (int i = 0; i < numberOfWizards; i++) {
        for (int j=0; j<mNumberOfPlayers; j++){
            if(P[j].team==i){
                teams[i]+=P[j].power;
            }
        }
    }

    // Find the maximum value of the power
    // Find the index of the team with the most power
    int max=0;
    int maxIndex;
    for (int i = 0; i < numberOfWizards; i++) {
        if(teams[i]>max){
            max=teams[i];
            maxIndex=i;
        }
    }
    // Checking is there only one team with such maximum power
    int ct=0;
    for (int i = 0; i < numberOfWizards; i++) {
        if(teams[i]==max) ct+=1;
    }
    // If there is not only one team with maximum power, then it is a tie
    if (ct>1){
        fprintf(out,"It's a tie\n");
    }
    // Otherwise, print the chosen wizard
    else{
        fprintf(out,"The chosen wizard is %s\n",arr[maxIndex+1]);
    }
    // Closing files
    fclose(in);
    fclose(out);
    return 0;
}