# Odoo Docker

Odoo, formerly known as OpenERP, is a suite of open-source business apps written in Python and released under the LGPL license. This suite of applications covers all business needs, from Website/Ecommerce down to manufacturing, inventory and accounting, all seamlessly integrated.

## Docker
### Start a PostgreSQL server
```
$ docker run -d -e POSTGRES_USER=odoo -e POSTGRES_PASSWORD=odoo -e POSTGRES_DB=postgres --name db postgres:13
```

### Start an Odoo instance
```
$ docker run -p 8069:8069 --name odoo --link db:db -t odoo
```
[http://localhost:8069/](http://localhost:8069/)

### Use named volumes to preserve data
```
$ docker run -v odoo-data:/var/lib/odoo -d -p 8069:8069 --name odoo --link db:db -t odoo
$ docker run -d -v odoo-db:/var/lib/postgresql/data -e POSTGRES_USER=odoo -e POSTGRES_PASSWORD=odoo -e POSTGRES_DB=postgres --name db postgres:13
```

### Run multiple Odoo instances
```
$ docker run -p 8070:8069 --name odoo2 --link db:db -t odoo
$ docker run -p 8071:8069 --name odoo3 --link db:db -t odoo
```

## Runtime Environment
- [PostgreSQL 13.x](https://www.postgresql.org/download/)
- [Python 3.7.x](https://www.python.org/downloads/)
- [Odoo 15](http://nightly.odoo.com/)

## Architecture
![](https://www.odoo.com/documentation/15.0/_images/three_tier.svg)

## Screenshots
![](https://odoocdn.com/openerp_website/static/src/img/2018/crm/crm_screenshot_03.gif)

![](https://odoocdn.com/web/image/11141266/Chat-Window-Overlay.png)

![](https://www.odoo.com/documentation/15.0/_images/simple_dashboard.png)

## References
- [Odoo Docker](https://hub.docker.com/_/odoo)
- [Architecture Overview](https://www.odoo.com/documentation/15.0/developer/howtos/rdtraining/01_architecture.html)
- [Odoo GitHub](https://github.com/odoo/odoo)