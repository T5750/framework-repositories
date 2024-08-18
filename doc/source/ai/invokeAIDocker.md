# InvokeAI Docker

InvokeAI is an implementation of Stable Diffusion, the open source text-to-image and image-to-image generator. It provides a streamlined process with various new features and options to aid the image generation process.

## Quick Start
1. Download and unzip the installer from the bottom of the [latest release](https://github.com/invoke-ai/InvokeAI/releases/latest).
2. Run the installer script.
   - **Windows**: Double-click on the `install.bat` script.
   - **macOS**: Open a Terminal window, drag the file `install.sh` from Finder into the Terminal, and press enter.
   - **Linux**: Run `install.sh`.
3. When prompted, enter a location for the install and select your GPU type.
4. Once the install finishes, find the directory you selected during install. The default location is `C:\Users\Username\invokeai` for Windows or `~/invokeai` for Linux/macOS.
5. Run the launcher script (`invoke.bat` for Windows, `invoke.sh` for macOS and Linux) the same way you ran the installer script in step 2.
6. Select option 1 to start the application. Once it starts up, open your browser and go to <http://localhost:9090>.
7. Open the model manager tab to install a starter model and then you'll be ready to generate.

More detail, including hardware requirements and manual install instructions, are available in the [installation documentation](https://invoke-ai.github.io/InvokeAI/installation/INSTALLATION/).

## Manual Install
```sh
# Windows (PowerShell)
mkdir invokeai
cd invokeai
python -m venv .venv --prompt InvokeAI
.venv\Scripts\activate
pip install InvokeAI --use-pep517 --extra-index-url https://download.pytorch.org/whl/cpu
set HF_ENDPOINT=https://hf-mirror.com
invokeai-web
```

## Docker
Important: Ensure that Docker is set up to use the GPU. Refer to [NVIDIA](https://docs.nvidia.com/datacenter/cloud-native/container-toolkit/latest/install-guide.html) or [AMD](https://rocm.docs.amd.com/projects/install-on-linux/en/latest/how-to/docker.html) documentation.
```sh
docker run --runtime=nvidia --gpus=all --publish 9090:9090 ghcr.io/invoke-ai/invokeai
```
[http://localhost:9090/](http://localhost:9090/)

## Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://invoke-ai.github.io/InvokeAI/assets/invoke-web-server-1.png)

## References
- [InvokeAI](https://invoke-ai.github.io/InvokeAI/)
- [InvokeAI GitHub](https://github.com/invoke-ai/InvokeAI)
- [InvokeAI Configuration](https://invoke-ai.github.io/InvokeAI/features/CONFIGURATION/)
- [InvokeAI Manual Install](https://invoke-ai.github.io/InvokeAI/installation/020_INSTALL_MANUAL/)