# Wagtail Docker

Wagtail is the leading open-source Python CMS

It's fast, elegant, and designed to give everyone on your team the tools they need to manage content at enterprise scale

## pip
```sh
pip install wagtail
wagtail start mysite
cd mysite
pip install -r requirements.txt
python manage.py migrate
python manage.py createsuperuser
python manage.py runserver
```

## docker-wagtail-develop
A script to painlessly set up a Docker environment for development of Wagtail
```sh
git clone https://github.com/wagtail/docker-wagtail-develop.git wagtail-dev
cd wagtail-dev/
# 4. Run the setup script. This will check out the bakerydemo project and local copies of wagtail and its dependencies.
./setup.sh
docker-compose build
docker-compose up
# 7. Now in a new shell, run the databse setup script. The database will be persisted across container executions by Docker's Volumes system so you will only need to run this commmand the first time you start the database.
./setup-db.sh
```
- Visit your site at http://localhost:8000
- The admin interface is at http://localhost:8000/admin/ - log in with `admin` / `changeme`.

## Screenshots
![](https://media.wagtail.org/images/screenshot-streamfield2_tECX3RB.width-1300.png)

![](https://media.wagtail.org/images/screenshot-code2_OMwPnAK.width-1300.png)

## References
- [Wagtail](https://wagtail.org/)
- [Wagtail GitHub](https://github.com/wagtail/wagtail)
- [Your first Wagtail site](https://docs.wagtail.org/en/stable/getting_started/tutorial.html)
- [docker-wagtail-develop GitHub](https://github.com/wagtail/docker-wagtail-develop/)
- [wagtail/bakerydemo](https://github.com/wagtail/bakerydemo)