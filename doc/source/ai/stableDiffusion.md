# Stable Diffusion

A latent text-to-image diffusion model

## Online
- [Stable Diffusion web UI](https://stabledifffusion.com/webui)
- [Easy Diffusion](https://stablediffusion.gigantic.work/)

## Stable Diffusion web UI
A web interface for Stable Diffusion, implemented using Gradio library.

### Setup
- [Install and run on NVidia GPUs](https://github.com/AUTOMATIC1111/stable-diffusion-webui/wiki/Install-and-Run-on-NVidia-GPUs)
- [Install and run via container (i.e. Docker)](https://github.com/AUTOMATIC1111/stable-diffusion-webui/wiki/Containers)
- [Run via online services](https://github.com/AUTOMATIC1111/stable-diffusion-webui/wiki/Online-Services)
- [wiki](https://github.com/AUTOMATIC1111/stable-diffusion-webui/wiki)

### Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)

### Screenshots
![](https://raw.githubusercontent.com/AUTOMATIC1111/stable-diffusion-webui/master/screenshot.png)

## Stable Diffusion WebUI Docker

### Setup
clone this repo and run:
```sh
git clone https://github.com/AbdBarho/stable-diffusion-webui-docker.git
docker compose --profile download up --build
# wait until its done, then:
docker compose --profile [ui] up --build
# where [ui] is one of: invoke | auto | auto-cpu | comfy | comfy-cpu
```

Next, choose which UI you want to run (you can easily change later):
- `invoke`: One of the earliest forks, stunning UI [Repo by InvokeAI](https://github.com/invoke-ai/InvokeAI)
- `auto`: The most popular fork, many features with neat UI, [Repo by AUTOMATIC1111](https://github.com/AUTOMATIC1111/stable-diffusion-webui)
- `auto-cpu`: for users without a GPU.
- `comfy`: A graph based workflow UI, very powerful, Repo by [comfyanonymous](https://github.com/comfyanonymous/ComfyUI)

## Easy Diffusion
The easiest way to install and use Stable Diffusion on your computer.

- [Installation](https://easydiffusion.github.io/docs/installation/)
- [UI Overview](https://github.com/easydiffusion/easydiffusion/wiki/UI-Overview)

### Screenshots
![](https://easydiffusion.github.io/assets/img/splash.webp)

## Models
- [admruul/anything-v3.0](https://hf-mirror.com/admruul/anything-v3.0)
- [runwayml/stable-diffusion-v1-5](https://hf-mirror.com/runwayml/stable-diffusion-v1-5)
- [latent-consistency/lcm-lora-sdv1-5](https://hf-mirror.com/latent-consistency/lcm-lora-sdv1-5)
- [stabilityai/stable-diffusion-xl-base-1.0](https://hf-mirror.com/stabilityai/stable-diffusion-xl-base-1.0)
- [stabilityai/stable-diffusion-xl-refiner-1.0](https://hf-mirror.com/stabilityai/stable-diffusion-xl-refiner-1.0)
- [ByteDance/SDXL-Lightning](https://hf-mirror.com/ByteDance/SDXL-Lightning)
- [ByteDance/Hyper-SD](https://hf-mirror.com/ByteDance/Hyper-SD)

## References
- [Stable Diffusion](https://ommer-lab.com/research/latent-diffusion-models/)
- [Stable Diffusion GitHub](https://github.com/CompVis/stable-diffusion)
- [Stable Diffusion web UI GitHub](https://github.com/AUTOMATIC1111/stable-diffusion-webui)
- [Stable Diffusion WebUI Docker GitHub](https://github.com/AbdBarho/stable-diffusion-webui-docker)
- [Easy Diffusion](https://easydiffusion.github.io/)
- [Easy Diffusion GitHub](https://github.com/easydiffusion/easydiffusion)