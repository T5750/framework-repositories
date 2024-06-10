# Comflowy Note

## 下载 & 导入模型
不同 Stable Diffusion 的差别
- Stable Diffusion v1.5: [Stable Diffusion v1.x press release](https://stability.ai/blog/stable-diffusion-public-release)
- Stable Diffusion v2.0: [Stable Diffusion v2 press release](https://stability.ai/blog/stable-diffusion-v2-release)
- Stable Diffusion XL: [Stable Diffusion xl 1.0 press release](https://stability.ai/news/stable-diffusion-sdxl-1-announcement)

模型下载
- HuggingFace: 可以将 HuggingFace 理解为 AI 届的 Github
- CivitAI: 更偏向于 UGC 一些，会看到更多个人训练的模型

## Stable Diffusion 基础
AI 绘图工具与传统的绘图工具（如 Photoshop 或者 Figma）相比，最大的区别在于，它的很多配置或操作是不可视化的，且结果随机

Stable Diffusion（稳定扩散）严格说来它是一个由几个组件（模型）构成的系统，而非单独的一个模型

简单理解，就是用户输入了一段 Prompt 指令，机器会按照这个指令，在一个潜空间里，将一张随机图降噪为一张符合指令的图片

整个过程，与其说 AI 是在「生成」图片，不如称其为「雕刻」更合适。就像米开朗基罗在完成大卫雕像后，说过的一句话那样：雕像本来就在石头里，我只是把不要的部分去掉

Latent Space（潜在空间）用于表示数据的低维空间。通过对原始数据进行编码和降维得到的一组潜在变量。潜在空间的维度通常比原始数据的维度低，因此可以提取出数据中最重要的特征和结构

Variational Auto Encoder（变分自编码器）简称 VAE

CLIP 模型，全称为 Contrastive Language Image Pre-training（对比语言图像预训练）

## 文生图

节点名称 | 在 SD 模型里对应的名称 | 解释
---|---|---
CLIP Text Encode | Text Encoder | 两个节点分别连接 positive 和 negative 端，意味着一个是正向 prompt 一个代表负向 prompt
KSmapler | Image Information Creator | 最核心的模型生成图片的部分
Empty Latent Image | Image Information Creator | 整个生图过程是在 Latent Space 里进行
VAE Decoder | Image Decoder | Variational Auto Encoder（变分自编码器）简称 VAE

节点介绍
- Load Checkpoint：MODEL、CLIP 还有 VAE
- CLIP Text Encode(Prompt)：对比文本图像预训练。正向的 Prompt，负向的 Prompt
- Empty Latent Image
- Save Image
- KSampler

## 基本操作
③ ComfyUI 也可以像 Midjourney 那样看到生成预览图。首先安装 ComfyUI Manager，然后在设置里的 Preview Method 设置为 Latent2RGB 即可（这个生成速度会比较快）

④ 善用 Bypass 和 Mute 功能
 - Bypass：跳过某个节点让后续的节点继续运行
 - Mute：让某个节点及其以后的节点都不运行

⑤ 连续出图
- 设置 Batch
- Auto Queue

## SD Prompt 基础
基本原则
- 原则一：prompt 不是越长越好，最好保持在 75 个 token（或约 60 个字）以内
- 原则二：越重要的词放在靠前的位置
- 原则三：善用符号。逗号分隔不同的意图。使用 () 调整权重

## Embedding
为何机器能知道 Cat 是长什么样子的。关键是 Text Encoder 将自然语意的 Prompt 转成了词特征向量 Embedding

## LoRA
LoRA 是在不破坏任何一层函数，而是将参数注入到原有的每一层中。这样的好处是不破坏原有的模型，即插即用，并且模型的大小也比较小

用类比来理解，可以将 LoRA 视为类似相机「滤镜」

## 图生图
Stable Diffusion 模型图生图主流方法有两种：
- 重绘（drawing over）：将输入的图片做基底，用模型在其上重新生成新的图片。
- 参考（reference）：将输入的图片作为参数，将其和 prompt 一起输入到模型中，然后生成新的图片。

### 方法一：重绘
Stable Diffusion 的原理
- 文生图是将随机噪声图降噪成一张新的图
- 图生图是先给输入的图片添加噪音，再用相同的方法将这个噪音图降噪成新的图片

还是拿雕刻做类比的话，它的过程类似是雕刻师（模型）拿了一个雕像（初始输入的图），根据你的指令（Prompt）在其基础上重新雕一个雕塑（输出的图）。

### 方法二：参考
如果那雕刻做类比的话，这个过程类似于你给雕刻师下了文字指令（prompt）外，还给雕刻师看了张图片，让 TA 参考一下，然后根据你的指令和图片重新雕刻一个雕塑（输出的图）。

## Upscale
将一些分辨率很低的照片转化成高清照片

目前主流的 upscale 的方法有两种：
- Upscale pixel：将可视的图直接 upscale。如果拿 ComfyUI 节点解释，这个节点输入的是 image，输出的是 upscale 后的 image。
- Upscale latent：另一种是将不可视的潜空间图像 upscale，如果拿 ComfyUI 节点解释，这个节点输入的是 latent 输出的是 upscale 后的 latent，最后需要经过 decode 才能变成可视的图片。

根据需求，以及使用的模型不同，选择不同的 upscale workflow
- 如果想要绝对的还原，那用 Upscale pixel by algorithm workflow
- 如果想要放大图片的同时还想让图片变得更加清晰，可以选择 Upscale pixel by model workflow
- 如果想要放大的同时，想让图片呈现更多细节，并且不在意跟原图有些许出入，那就选择 Hi-res fix latent upscale workflow
- 如果想要相对平衡的效果，可以选择 Upscale pixel and Hi-res fix workflow

## In/Outpainting
- Inpainting: 将图片里的某一部分替换成其他
- Outpainting: 将原来的图片扩大

## ContorlNet
- LoRA 是滤镜，影响是模型，所以跟 Model 相连
- ControlNet 是 prompt 的补充，控制的是 Conditioning，所以跟 Prompt 相连

## SD 进阶
相较于 SD v1.5，SDXL 有两个 Image Creator。一个是 Base 模型，一个是 Refined 模型。Base 会先生成一个全局构图，然后 Refined 会在这个全局构图的基础上，生成更加细节丰富的图片

## Prompt 进阶
SDXL prompt styler 插件：调整图片风格

## Stable Cascade
与 Stable Diffusion 模型最大的不同在于它的潜空间更小，好处就是生图速度更快，训练模型的成本也更低

//TODO 图生图进阶 🚧

## References
- [Comflowy](https://comflowy.com/zh-CN/docs)