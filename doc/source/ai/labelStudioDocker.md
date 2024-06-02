# Label Studio Docker

Label Studio is a multi-type data labeling and annotation tool with standardized output format

## Playground
[Playground](https://labelstud.io/playground/)

## Documentation
- [Customizable Tags](https://labelstud.io/tags)
- [Labeling Templates](https://labelstud.io/templates)

## Docker
```sh
docker run -it --name label-studio -p 8080:8080 -v `pwd`/mydata:/label-studio/data heartexlabs/label-studio
```
You can find all the generated assets, including SQLite3 database storage `label_studio.sqlite3` and uploaded files, in the `./mydata` directory.

[http://localhost:8080/](http://localhost:8080/)

## Docker Compose
- Label Studio
- [Nginx](https://www.nginx.com/) - proxy web server used to load various static data, including uploaded audio, images, etc.
- [PostgreSQL](https://www.postgresql.org/) - production-ready database that replaces less performant SQLite3.

To start using the app from `http://localhost` run this command:
```bash
docker-compose up
```

## Install locally with pip
```bash
# Requires Python >=3.7 <=3.9
pip install label-studio
# Start the server at http://localhost:8080
label-studio
```

## Install locally with Anaconda
```bash
conda create --name label-studio
conda activate label-studio
pip install label-studio
```

## Import Data
### Cloud storage setup
#### Amazon S3
1. Open Label Studio in your web browser.
2. For a specific project, open **Settings > Cloud Storage**.
3. Click **Add Source Storage**.
4. In the dialog box that appears, select **Amazon S3** as the storage type.
5. In the **Storage Title** field, type a name for the storage to appear in the Label Studio UI.
6. Specify the name of the S3 bucket, and if relevant, the bucket prefix to specify an internal folder or container.
7. Adjust the remaining parameters:
    - Bucket Name, S3 Endpoint, Access Key ID, Secret Access Key
8. Click **Add Storage**.

## Screenshots
![Gif of Label Studio annotating different types of data](https://raw.githubusercontent.com/heartexlabs/label-studio/master/images/annotation_examples.gif)

![](https://labelstud.io/images/ls-modules-scheme.png)

![](https://labelstud.io/_astro/images-tab.64279c16_ZaBSvC.avif)

![](https://labelstud.io/_astro/video-tab.0ad16d1f_ZIpzuy.avif)

## Label Studio ML backend
Configs and boilerplates for Label Studio's Machine Learning backend

### How it works
1. Get your model code
2. Wrap it with the Label Studio SDK
3. Create a running server script
4. Launch the script
5. Connect Label Studio to ML backend on the UI

### Quickstart with an example ML backend
```sh
git clone https://github.com/heartexlabs/label-studio-ml-backend
cd label-studio-ml-backend/label_studio_ml/examples/simple_text_classifier
docker-compose up
```
[http://localhost:9090/](http://localhost:9090/)

### Start your custom ML backend with Label Studio
1. Setup environment
    ```sh
    cd label-studio-ml-backend
    # Install label-studio-ml and its dependencies
    pip install -U -e .
    # Install the dependencies for the example or your custom ML backend
    pip install -r label_studio_ml/examples/requirements.txt
    ```
2. Initialize an ML backend based on an example script:
    ```sh
    label-studio-ml init my_ml_backend --script label_studio_ml/examples/simple_text_classifier/simple_text_classifier.py
    ```
3. Start ML backend server
    ```sh
    label-studio-ml start my_ml_backend
    ```
4. Start Label Studio and connect it to the running ML backend on the project settings page.

## References
- [Label Studio](https://labelstud.io/)
- [Label Studio GitHub](https://github.com/heartexlabs/label-studio)
- [Label Studio Docker](https://hub.docker.com/r/heartexlabs/label-studio)
- [Get started with Label Studio](https://labelstud.io/guide/index.html)
- [Label Studio ML backend GitHub](https://github.com/heartexlabs/label-studio-ml-backend)
- [Machine learning integration](https://labelstud.io/guide/ml.html)
- [Cloud storage setup](https://labelstud.io/guide/storage.html)
- [Database setup](https://labelstud.io/guide/storedata.html)