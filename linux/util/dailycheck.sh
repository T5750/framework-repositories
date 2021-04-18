#!/bin/bash
prefix="`date '+%Y-%m-%d %T'`"
prefix_info="$prefix INFO"
prefix_warn="$prefix WARN"
suffix="--- [sh]"
dailycheckPath=~/dailycheck/logs
dailycheckLog=$dailycheckPath/dailycheck.log
checkUrl="https://www.baidu.com"
if [ ! -d "${dailycheckPath}" ]; then
  mkdir ${dailycheckPath}
fi
if [ ! -f "${dailycheckLog}" ]; then
  touch ${dailycheckLog}
fi
check_mem_usage(){
    ramusage=$(free | awk '/Mem/{printf("RAM Usage: %.2f\n"), $3/$2*100}'| awk '{print $3}')
    if [ $(echo "$ramusage > 85" | bc) = 1 ];then
        printf "%s %-5s %s\n" "$prefix_warn" $$ "$suffix mem rate is more than 85%, please check" >> $dailycheckLog
    fi
    printf "%s %-5s %s %.2f%s\n" "$prefix_info" $$ "$suffix mem rate is" "$ramusage" "%" >> $dailycheckLog
}
check_url(){
    curl_status=`curl -I $checkUrl| grep 200 | awk '{print $2}'`
    if [ "200" == "$curl_status" ];then
        printf "%s %-5s %s\n" "$prefix_info" $$ "$suffix $checkUrl is ok" >> $dailycheckLog
    else
        printf "%s %-5s %s\n" "$prefix_warn" $$ "$suffix $checkUrl is $curl_status" >> $dailycheckLog
    fi
}
check_cpuidle(){
    mincpu=`sar -u 2 10|grep all|awk '{print $NF}'|sort -nr|tail -1`
    if [ $(echo "${mincpu} < 20" | bc) = 1 ];then
    #if [ "$mincpu" -le "20" ];then
        printf "%s %-5s %s\n" "$prefix_warn" $$ "$suffix cpu idle is less than 20%, please check" >> $dailycheckLog
    else
        printf "%s %-5s %s\n" "$prefix_info" $$ "$suffix cpu idle is more than 20%, it is ok" >> $dailycheckLog
    fi
}
check_mem(){
    printf "%s %-5s %s\n" "$prefix_info" $$ "$suffix memory stat" >> $dailycheckLog
    vmstat 2 5 >> $dailycheckLog
}
check_io(){
    util=`sar -d 2 10|egrep -v 'x86|^$|await'|awk '{print $NF}'|sort -nr|tail -1`
    await=`sar -d 2 10|egrep -v 'x86|^$|await'|awk '{print $(NF-2)}'|sort -nr|tail -1`
    if [ $(echo "${util} < 80" | bc) = 1 ] && [ $(echo "${await} < 100" | bc) = 1 ] ;then
        printf "%s %-5s %s\n" "$prefix_info" $$ "$suffix disk io check is fine" >> $dailycheckLog
    else
        printf "%s %-5s %s\n" "$prefix_warn" $$ "$suffix disk io use too high" >> $dailycheckLog
    fi
}
check_swap(){
    tolswap=`cat /proc/meminfo|grep SwapTotal|awk '{print $2}'`
    #awk '/SwapTotal/{total=$2}/SwapFree/{free=$2}END{print (total-free)/1024}' /proc/meminfo
    useswap=`awk '/SwapTotal/{total=$2}/SwapFree/{free=$2}END{print (total-free)}' /proc/meminfo `
    util=`awk 'BEGIN{printf "%.1f\n",'$useswap'/'$tolswap'}'`
    if [ $(echo "${util} < 0.3" | bc) = 1 ] || [ $(echo "${useswap} < 1024" | bc) = 1 ] ;then
        printf "%s %-5s %s\n" "$prefix_info" $$ "$suffix swap use is ok" >> $dailycheckLog
    else
        printf "%s %-5s %s\n" "$prefix_warn" $$ "$suffix useswap: $useswap kb, swap util is $util" >> $dailycheckLog
    fi
}
check_df(){
    dfuse=`df -HT|awk '{print $6}'|grep -v Use|sed 's/%//g'|sort -nr|head -1`
    if [ $(echo "${dfuse} < 80" | bc) = 1 ];then
        printf "%s %-5s %s\n" "$prefix_info" $$ "$suffix disk used is less than 80%, it is ok" >> $dailycheckLog
    elif [ $(echo "${dfuse} > 80" | bc) = 1 ] && [ $(echo "${dfuse} < 90" | bc) = 1 ];then
        printf "%s %-5s %s\n" "$prefix_warn" $$ "$suffix disk used more than 80% and less than 90%" >> $dailycheckLog
    else
        printf "%s %-5s %s\n" "$prefix_warn" $$ "$suffix disk used more than 90%" >> $dailycheckLog
    fi
}
check_mem_usage
check_url
check_cpuidle
check_mem
check_io
check_swap
check_df