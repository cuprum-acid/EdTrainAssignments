# Assignment 1

Alphabetical sorting of names from a text file.


- input: **input.txt**
- output: **output.txt**


## Assignment description 
#### (From [codeforces.com](https://codeforces.com/))

Given a text file 𝚒𝚗𝚙𝚞𝚝.𝚝𝚡𝚝 starting with number N followed by N names of maximum length of L English letters each. Each name starts with an uppercase letter followed by non-negative number of lowercase letters without spaces or symbols in between. You have to sort them in _non-decreasing_ Lexicographic Order and write them to another file 𝚘𝚞𝚝𝚙𝚞𝚝.𝚝𝚡𝚝.


In case of finding any problems inside the input file (non-English letters, value N which does not correspond to the number of names, or anything violating the assignment rules), the output file should contain only the next string: Error in the 𝚒𝚗𝚙𝚞𝚝.𝚝𝚡𝚝

### **Input**
The first line of input file 𝚒𝚗𝚙𝚞𝚝.𝚝𝚡𝚝 should contain only **_N (1 ≤ N ≤ 100)_**, the total amount of names. The next _**N**_ lines should contain list of names (**1** per line) with length **_L (1 ≤ L ≤ 100)_**.
### **Output**
The output file 𝚘𝚞𝚝𝚙𝚞𝚝.𝚝𝚡𝚝 should contain the list of lexicographically sorted names.
#### **Examples**

>**input**
>  ``` 
> 5
> Munir
> Alaa
> Mohamad
> Mike
> Alexander
> ```
>**output**
>  ``` 
> Alaa
> Alexander
> Mike
> Mohamad
> Munir
> ```


>**input**
>  ``` 
> 3
> Abc
> Ab
> A
> ```
>**output**
>  ``` 
> A
> Ab
> Abc
> ```


>**input**
>  ``` 
> 1
> Z
> ```
>**output**
>  ``` 
> Z
> ```


>**input**
>  ``` 
> 6
> Munir
> Alaa
> Mohamad
> Mike
> Alexander
> ```
>**output**
>  ``` 
> Error in the input.txt
> ```


>**input**
>  ``` 
> 5
> Mun1r
> Alaa
> M0hamad
> Mike
> Alexand6r
> ```
>**output**
>  ``` 
> Error in the input.txt
> ```


>**input**
>  ``` 
> 3
> Abc
> ab
> A
> ```
>**output**
>  ``` 
> Error in the input.txt
> ```


### Note
Make sure to insert one newline ("`\n`") character after the last name written in the input and output.

## Authors

- **Evgeny Bobkunov** - *Wrote a program and issued a README file*
  - Email: [e.bobkunov@innopolis.university](mailto:e.bobkunov@innopolis.university) 