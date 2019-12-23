# Pig String Functions

Functions | Description
---|-----
ENDSWITH(string, testAgainst) | To verify whether a given string ends with a particular substring.
STARTSWITH(string, substring) | Accepts two string parameters and verifies whether the first string starts with the second.
SUBSTRING(string, startIndex, stopIndex) | Returns a substring from a given string.
EqualsIgnoreCase(string1, string2) | To compare two stings ignoring the case.
INDEXOF(string, ‘character’, startIndex) | Returns the first occurrence of a character in a string, searching forward from a start index.
LAST_INDEX_OF(expression) | Returns the index of the last occurrence of a character in a string, searching backward from a start index.
LCFIRST(expression) | Converts the first character in a string to lower case.
UCFIRST(expression) | Returns a string with the first character converted to upper case.
UPPER(expression) | UPPER(expression) Returns a string converted to upper case.
LOWER(expression) | Converts all characters in a string to lower case.
REPLACE(string, ‘oldChar’, ‘newChar’); | To replace existing characters in a string with new characters.
STRSPLIT(string, regex, limit) | To split a string around matches of a given regular expression.
STRSPLITTOBAG(string, regex, limit) | Similar to the STRSPLIT() function, it splits the string by given delimiter and returns the result in a bag.
TRIM(expression) | Returns a copy of a string with leading and trailing whitespaces removed.
LTRIM(expression) | Returns a copy of a string with leading whitespaces removed.
RTRIM(expression) | Returns a copy of a string with trailing whitespaces removed.

## ENDSWITH()
```
grunt> ENDSWITH(string1, string2)
grunt> emp_data = LOAD 'hdfs://localhost:9000/pig_data/employee_details.txt' USING PigStorage(',')
   as (id:int, name:chararray, age:int, city:chararray);
grunt> emp_endswith = FOREACH emp_data GENERATE (id,name),ENDSWITH ( name, 'n' );
grunt> Dump emp_endswith;
```

## References
- [Pig - String Functions](https://www.tutorialspoint.com/apache_pig/apache_pig_string_functions.htm)
- [Pig - ENDSWITH()](https://www.tutorialspoint.com/apache_pig/apache_pig_endswith.htm)