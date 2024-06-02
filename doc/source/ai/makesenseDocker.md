# Make Sense Docker

Free to use online tool for labelling photos.

## Local Setup
```sh
git clone https://github.com/SkalskiP/make-sense.git
cd make-sense
npm install
npm start
```

## Docker
```sh
docker build -t make-sense -f docker/Dockerfile .
docker run -dit -p 3000:3000 --restart=always --name=make-sense make-sense
docker logs make-sense
```
[http://localhost:3000/](http://localhost:3000/)

## Screenshots
https://user-images.githubusercontent.com/26109316/193255987-2d01c549-48c3-41ae-87e9-e1b378968966.mov

## References
- [Make Sense](https://makesense.ai/)
- [Make Sense GitHub](https://github.com/SkalskiP/make-sense)