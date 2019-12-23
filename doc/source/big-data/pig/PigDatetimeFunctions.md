# Pig Date-time Functions

Functions | Description
---|----
ToDate(milliseconds) | This function returns a date-time object according to the given parameters. The other alternative for this function are ToDate(iosstring), ToDate(userstring, format), ToDate(userstring, format, timezone)
CurrentTime() | returns the date-time object of the current time.
GetDay(datetime) | Returns the day of a month from the date-time object.
GetHour(datetime) | Returns the hour of a day from the date-time object.
GetMilliSecond(datetime) | Returns the millisecond of a second from the date-time object.
GetMinute(datetime) | Returns the minute of an hour from the date-time object.
GetMonth(datetime) | Returns the month of a year from the date-time object.
GetSecond(datetime) | Returns the second of a minute from the date-time object.
GetWeek(datetime) | Returns the week of a year from the date-time object.
GetWeekYear(datetime) | Returns the week year from the date-time object.
GetYear(datetime) | Returns the year from the date-time object.
AddDuration(datetime, duration) | Returns the result of a date-time object along with the duration object.
SubtractDuration(datetime, duration) | Subtracts the Duration object from the Date-Time object and returns the result.
DaysBetween(datetime1, datetime2) | Returns the number of days between the two date-time objects.
HoursBetween(datetime1, datetime2) | Returns the number of hours between two date-time objects.
MilliSecondsBetween(datetime1, datetime2) | Returns the number of milliseconds between two date-time objects.
MinutesBetween(datetime1, datetime2) | Returns the number of minutes between two date-time objects.
MonthsBetween(datetime1, datetime2) | Returns the number of months between two date-time objects.
SecondsBetween(datetime1, datetime2) | Returns the number of seconds between two date-time objects.
WeeksBetween(datetime1, datetime2) | Returns the number of weeks between two date-time objects.
YearsBetween(datetime1, datetime2) | Returns the number of years between two date-time objects.

## ToDate()
```
grunt> ToDate(milliseconds)
grunt> ToDate(iosstring)
grunt> ToDate(userstring, format)
grunt> ToDate(userstring, format, timezone)
```
`vi ~/pig/date.txt`
```
001,1989/09/26 09:00:00
002,1980/06/20 10:22:00
003,1990/12/19 03:11:44
```
```
$ hdfs dfs -put ~/pig/date.txt hdfs://localhost:9000/pig_data/
grunt> date_data = LOAD 'hdfs://localhost:9000/pig_data/date.txt' USING PigStorage(',')
   as (id:int,date:chararray);
grunt> todate_data = foreach date_data generate ToDate(date,'yyyy/MM/dd HH:mm:ss')
   as (date_time:DateTime);
grunt> Dump todate_data;
```

## References
- [Pig - Date-time Functions](https://www.tutorialspoint.com/apache_pig/apache_pig_date_time_functions.htm)
- [Pig - ToDate()](https://www.tutorialspoint.com/apache_pig/apache_pig_todate.htm)