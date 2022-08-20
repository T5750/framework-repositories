# Kibana Docker

Kibana gives shape to any kind of data — structured and unstructured — indexed in Elasticsearch.

## Docker
```sh
docker run -d --name elasticsearch -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.17.4
docker run -d --name kibana -e ELASTICSEARCH_HOSTS=http://elasticsearchIP:9200 -p 5601:5601 kibana:7.17.4
```
- [http://localhost:5601/](http://localhost:5601/)
- Dev Tools: [http://localhost:5601/app/dev_tools#/console](http://localhost:5601/app/dev_tools#/console)

## Tests
```
GET _cat/nodes
GET _cat/nodes?v=true
GET _cat/master?v=true

GET /_search
POST /_search
PUT /book
# 查看book索引数据
GET book/_search
# 添加一条数据
POST book/_doc
{
  "page":8,
  "content": "T5750喜欢运动"
}
# 更新数据
PUT book/_doc/MniDqYEBMPp1ImUSVFRj
{
  "page":8,
  "content":"T5750喜欢运动"
}
GET book/_search
{
    "query": {
    "match": {
      "content": "T5750"
    }
  }
}
# 删除数据
POST book/_delete_by_query
{
  "query": {
    "match": {
      "page": 8
    }
  }
}
# 批量插入数据
POST book/_bulk
{ "index":{} }
{ "page":22 , "content": "Adversity, steeling will strengthen body.逆境磨练意志，锻炼增强体魄。"}
{ "index":{} }
{ "page":23 , "content": "Reading is to the mind, such as exercise is to the body.读书之于头脑，好比运动之于身体。"}
{ "index":{} }
{ "page":24 , "content": "Years make you old, anti-aging.岁月催人老，运动抗衰老。"}
{ "index":{} }
```

## Screenshots
![](https://static-www.elastic.co/v3/assets/bltefdd0b53724fa2ce/blt47b86adba2f459aa/5fa31e03bfc5dd7188659491/screenshot-kibana-dashboard-webtraffic2-710-547x308.jpg)

![](https://static-www.elastic.co/v3/assets/bltefdd0b53724fa2ce/blt7f166fef273838cc/5fa31e1665bdd35303dff5b6/screenshot-elastic-maps-layers-logs-710-588x331.jpg)

![](https://static-www.elastic.co/v3/assets/bltefdd0b53724fa2ce/blta7894369d5c6bb4b/5fa31e224e40cf53001f3ba2/screenshot-kibana-timeseries-710-602x339.png)

![](https://static-www.elastic.co/v3/assets/bltefdd0b53724fa2ce/blt84378bd3dd281820/5fa31e2772a3526f28db8dc6/screenshot-kibana-machine-learning-710-602x339.jpg)

![](https://static-www.elastic.co/v3/assets/bltefdd0b53724fa2ce/blt56d304d272e89ad3/5fa31e3942256d5ffdf40460/screenshot-kibana-graph-710-602x339.png)

## References
- [Kibana Docker](https://hub.docker.com/_/kibana)
- [Kibana GitHub](https://github.com/elastic/kibana)
- [Kibana](https://www.elastic.co/cn/kibana/)
- [Kibana详细入门教程](https://www.cnblogs.com/chenqionghe/p/12503181.html)
