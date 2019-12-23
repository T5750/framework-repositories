# Hive Built-in Functions

## Built-In Functions

Return Type | Signature | Description
---|---|-----
BIGINT | round(double a) | It returns the rounded BIGINT value of the double.
BIGINT | floor(double a) | It returns the maximum BIGINT value that is equal or less than the double.
BIGINT | ceil(double a) | It returns the minimum BIGINT value that is equal or greater than the double.
double | rand(), rand(int seed) | It returns a random number that changes from row to row.
string | concat(string A, string B,...) | It returns the string resulting from concatenating B after A.
string | substr(string A, int start) | It returns the substring of A starting from start position till the end of string A.
string | substr(string A, int start, int length) | It returns the substring of A starting from start position with the given length.
string | upper(string A) | It returns the string resulting from converting all characters of A to upper case.
string | ucase(string A) | Same as above.
string | lower(string A) | It returns the string resulting from converting all characters of B to lower case.
string | lcase(string A) | Same as above.
string | trim(string A) | It returns the string resulting from trimming spaces from both ends of A.
string | ltrim(string A) | It returns the string resulting from trimming spaces from the beginning (left hand side) of A.
string | rtrim(string A) | rtrim(string A) It returns the string resulting from trimming spaces from the end (right hand side) of A.
string | regexp_replace(string A, string B, string C) | It returns the string resulting from replacing all substrings in B that match the Java regular expression syntax with C.
int | size(Map<K.V>) | It returns the number of elements in the map type.
int | size(Array<T>) | It returns the number of elements in the array type.
value of <type> | cast(<expr> as <type>) | It converts the results of the expression expr to <type> e.g. cast('1' as BIGINT) converts the string '1' to it integral representation. A NULL is returned if the conversion does not succeed.
string | from_unixtime(int unixtime) | convert the number of seconds from Unix epoch (1970-01-01 00:00:00 UTC) to a string representing the timestamp of that moment in the current system time zone in the format of "1970-01-01 00:00:00"
string | to_date(string timestamp) | It returns the date part of a timestamp string: to_date("1970-01-01 00:00:00") = "1970-01-01"
int | year(string date) | It returns the year part of a date or a timestamp string: year("1970-01-01 00:00:00") = 1970, year("1970-01-01") = 1970
int | month(string date) | It returns the month part of a date or a timestamp string: month("1970-11-01 00:00:00") = 11, month("1970-11-01") = 11
int | day(string date) | It returns the day part of a date or a timestamp string: day("1970-11-01 00:00:00") = 1, day("1970-11-01") = 1
string | get_json_object(string json_string, string path) | It extracts json object from a json string based on json path specified, and returns json string of the extracted json object. It returns NULL if the input json string is invalid.

### Example
```
hive> SELECT round(2.6) from employee;
hive> SELECT floor(2.6) from employee;
hive> SELECT ceil(2.6) from employee;
```

## Aggregate Functions

Return Type | Signature | Description
---|---|-----
BIGINT | count(*), count(expr), | count(*) - Returns the total number of retrieved rows.
DOUBLE | sum(col), sum(DISTINCT col) | It returns the sum of the elements in the group or the sum of the distinct values of the column in the group.
DOUBLE | avg(col), avg(DISTINCT col) | It returns the average of the elements in the group or the average of the distinct values of the column in the group.
DOUBLE | min(col) | It returns the minimum value of the column in the group.
DOUBLE | max(col) | It returns the maximum value of the column in the group.

## References
- [Hive - Built-in Functions](https://www.tutorialspoint.com/hive/hive_built_in_functions.htm)