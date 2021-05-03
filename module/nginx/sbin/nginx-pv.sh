#!/bin/bash
html_home="/usr/local/nginx/html"
logs_home="/usr/local/nginx/logs"
html_pv=$html_home/pv.html
logs_name=$logs_home/access.log
date=`date +%Y-%m-%d`
pv=`awk '{print $7}' $logs_name|wc -l`
uv=`awk '{print $1}' $logs_name|sort | uniq -c |wc -l`
url_arr=`awk '{print $7}' $logs_name|sort | uniq -c |sort -n -k 1 -r|more`
ip_arr=`awk '{print $1}' $logs_name|sort | uniq -c |sort -n -k 1 -r|more`
counter=0
sed -i '51,$d' $html_pv
echo "<span class=\"badge badge-primary badge-pill\">$pv</span>" >> $html_pv
echo "</li>" >> $html_pv
echo "<li class=\"list-group-item d-flex justify-content-between align-items-center list-group-item-action\">" >> $html_pv
echo "根据访问IP统计UV (Unique Visitor)" >> $html_pv
echo "<span class=\"badge badge-primary badge-pill\">$uv</span>" >> $html_pv
echo "</li>" >> $html_pv
echo "</ul>" >> $html_pv
echo "<div id=\"nginxAccordion\" data-children=\".item\">" >> $html_pv
echo "<div class=\"item\">" >> $html_pv
echo "<a data-toggle=\"collapse\" data-parent=\"#nginxAccordion\" href=\"#nginxAccordionUrl\" aria-expanded=\"true\" aria-controls=\"nginxAccordionUrl\">" >> $html_pv
echo "查询访问最频繁的URL" >> $html_pv
echo "</a>" >> $html_pv
echo "<div id=\"nginxAccordionUrl\" class=\"collapse show\" role=\"tabpanel\">" >> $html_pv
echo "<ul class=\"list-group\">" >> $html_pv
for url in ${url_arr[@]}
do
	if [[ "$url" =~ "/" ]]
	then
		echo "<li class=\"list-group-item d-flex justify-content-between align-items-center list-group-item-action\">" >> $html_pv
		echo "$url" >> $html_pv
		echo "<span class=\"badge badge-primary badge-pill\">$counter</span>" >> $html_pv
		echo "</li>" >> $html_pv
	else
		counter=$url
	fi
done
echo "</ul>" >> $html_pv
echo "</div>" >> $html_pv
echo "</div>" >> $html_pv
echo "<div class=\"item\">" >> $html_pv
echo "<a data-toggle=\"collapse\" data-parent=\"#nginxAccordion\" href=\"#nginxAccordionIp\" aria-expanded=\"true\" aria-controls=\"nginxAccordionIp\">" >> $html_pv
echo "查询访问最频繁的IP" >> $html_pv
echo "</a>" >> $html_pv
echo "<div id=\"nginxAccordionIp\" class=\"collapse show\" role=\"tabpanel\">" >> $html_pv
echo "<ul class=\"list-group\">" >> $html_pv
for ip in ${ip_arr[@]}
do
	if [[ "$ip" =~ "." ]]
	then
		echo "<li class=\"list-group-item d-flex justify-content-between align-items-center list-group-item-action\">" >> $html_pv
		echo "$ip" >> $html_pv
		echo "<span class=\"badge badge-primary badge-pill\">$counter</span>" >> $html_pv
		echo "</li>" >> $html_pv
	else
		counter=$ip
	fi
done
echo "</ul>" >> $html_pv
echo "</div>" >> $html_pv
echo "</div>" >> $html_pv
echo "</div>" >> $html_pv
echo "<div class=\"alert alert-light text-right\" role=\"alert\">" >> $html_pv
echo "最后更新于 $date" >> $html_pv
echo "</div>" >> $html_pv
echo "</div>" >> $html_pv
echo "</main>" >> $html_pv
echo "<footer class=\"footer mt-auto py-3\">" >> $html_pv
echo "<div class=\"container\">" >> $html_pv
echo "<span class=\"text-muted\">© 2019 T5750</span>" >> $html_pv
echo "</div>" >> $html_pv
echo "</footer>" >> $html_pv
echo "</body>" >> $html_pv
echo "</html>" >> $html_pv