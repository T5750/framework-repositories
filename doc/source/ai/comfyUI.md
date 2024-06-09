# ComfyUI

The most powerful and modular stable diffusion GUI and backend.

## Online
[ComfyUI Online](https://comfywebui.com/#comfyui-generator)

## Shortcuts

| Keybind                            | Explanation                                                                                                        |
|------------------------------------|--------------------------------------------------------------------------------------------------------------------|
| Ctrl + Enter                       | Queue up current graph for generation                                                                              |
| Ctrl + Shift + Enter               | Queue up current graph as first for generation                                                                     |
| Ctrl + Z/Ctrl + Y                  | Undo/Redo                                                                                                          |
| Ctrl + S                           | Save workflow                                                                                                      |
| Ctrl + O                           | Load workflow                                                                                                      |
| Ctrl + A                           | Select all nodes                                                                                                   |
| Alt + C                            | Collapse/uncollapse selected nodes                                                                                 |
| Ctrl + M                           | Mute/unmute selected nodes                                                                                         |
| Ctrl + B                           | Bypass selected nodes (acts like the node was removed from the graph and the wires reconnected through)            |
| Delete/Backspace                   | Delete selected nodes                                                                                              |
| Ctrl + Backspace                   | Delete the current graph                                                                                           |
| Space                              | Move the canvas around when held and moving the cursor                                                             |
| Ctrl/Shift + Click                 | Add clicked node to selection                                                                                      |
| Ctrl + C/Ctrl + V                  | Copy and paste selected nodes (without maintaining connections to outputs of unselected nodes)                     |
| Ctrl + C/Ctrl + Shift + V          | Copy and paste selected nodes (maintaining connections from outputs of unselected nodes to inputs of pasted nodes) |
| Shift + Drag                       | Move multiple selected nodes at the same time                                                                      |
| Ctrl + D                           | Load default graph                                                                                                 |
| Alt + `+`                          | Canvas Zoom in                                                                                                     |
| Alt + `-`                          | Canvas Zoom out                                                                                                    |
| Ctrl + Shift + LMB + Vertical drag | Canvas Zoom in/out                                                                                                 |
| Q                                  | Toggle visibility of the queue                                                                                     |
| H                                  | Toggle visibility of history                                                                                       |
| R                                  | Refresh graph                                                                                                      |
| Double-Click LMB                   | Open node quick search palette                                                                                     |

## Installing
### Windows
There is a portable standalone build for Windows that should work for running on Nvidia GPUs or for running on your CPU only on the [releases page](https://github.com/comfyanonymous/ComfyUI/releases).

[Direct link to download](https://github.com/comfyanonymous/ComfyUI/releases/download/latest/ComfyUI_windows_portable_nvidia_cu121_or_cpu.7z)

### Jupyter Notebook
To run it on services like paperspace, kaggle or colab you can use my [Jupyter Notebook](https://github.com/comfyanonymous/ComfyUI/blob/master/notebooks/comfyui_colab.ipynb)

## 模型复用
1. `extra_model_paths.yaml.example`重命名为`extra_model_paths.yaml`
2. 编辑path/to/stable-diffusion-webui/

## 使用ComfyUI的第一步
预览生成的图像而不立即保存图像：
1. 右键点击`Save Image`节点，然后选择`Remove`。
2. 在画布的空白部分双击，输入`preview`，然后点击`PreviewImage`选项。
3. 找到`VAE Decode`节点的`IMAGE`输出，并将其连接到您刚添加的`PreviewImage`节点的`images`输入上。
4. 在菜单中点击`Queue Prompt`，或在键盘上按Cmd+Enter或Ctrl+Enter，来生成第一张图像

## K采样器
### 输入
- `Model`：用于去噪的模型
- `Positive`：正向条件
- `Negative`：负向条件
- `latent_image`：将被去噪的潜在图像
- `seed`：用于创建噪声的随机种子
- `control_after_generate`：在每个提示后更改上述种子号的能力。节点可以`randomize`、`increment`、`decrement`或保持种子号`fixed`
- `steps`：去噪过程中使用的步骤数。采样器允许进行的步骤越多，结果就越准确
- `cfg`：分类器自由引导（cfg）比例决定了采样器在实现提示内容方面的积极性。更高的比例强制图像更好地代表提示，但设置过高的比例会负面影响图像质量
- `sampler_name`：使用哪个采样器
- `scheduler`：使用哪种计划
- `denoise`：通过噪声擦除多少潜在图像的信息

### 输出
- `LATENT`：去噪后的潜在图像

## Examples
[ComfyUI Basic Tutorial VN](https://comfyanonymous.github.io/ComfyUI_tutorial_vn/): All the art is made with ComfyUI. (early and not finished)

[Img2Img](https://github.com/comfyanonymous/ComfyUI_examples/blob/master/img2img)

[Lora](https://github.com/comfyanonymous/ComfyUI_examples/blob/master/lora)

## Runtime Environment
- [Python 3.11.x](https://www.python.org/downloads/)

## Screenshots
![](https://github.com/comfyanonymous/ComfyUI/raw/master/comfyui_screenshot.png)

## References
- [ComfyUI GitHub](https://github.com/comfyanonymous/ComfyUI)
- [ComfyUI Examples GitHub](https://github.com/comfyanonymous/ComfyUI_examples)
- [ComfyUI 用户手册](https://www.comfyuidoc.com/zh/)
- [ComfyUI 用户手册 K采样器](https://www.comfyuidoc.com/zh/Core%20Nodes/Sampling/KSampler.html)