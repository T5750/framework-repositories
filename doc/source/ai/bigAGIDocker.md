# Big-AGI Docker

Big-AGI is a groundbreaking AI suite designed to democratize access to advanced artificial intelligence for everyone. From professionals and developers to AI enthusiasts, Big-AGI provides a comprehensive, productivity-focused platform that combines state-of-the-art models with high-performance tools and complete data control.

## Demo
[Launch](https://get.big-agi.com/?_gl=1*2pw6r9*_ga*NzU1MDQxNDg3LjE3MTgwODQwNjQ.*_ga_J6VMGHMT2C*MTcxODI0MTEzNC4yLjEuMTcxODI0MTY5Mi42MC4wLjEyMzgxOTU3NjQ.)

## Docker
```sh
docker run -d --name big-agi -p 3000:3000 ghcr.io/enricoros/big-agi
```
[http://localhost:3000/](http://localhost:3000/)

## Browsing
Allows users to load web pages across various components of big-AGI. This feature is supported by Puppeteer-based browsing services, which are the most common way to render web pages in a headless environment.
```sh
#docker run -d --name chrome -p 9222:3000 browserless/chrome
docker run -d --name browserless -p 9222:3000 ghcr.io/browserless/chromium
```
[http://localhost:9222/json/version](http://localhost:9222/json/version)

### Configuration
1. Procure an Endpoint
    - `wss://${auth}@{some host}:{port}`, or ws:// for local services on your machine
2. Configure big-AGI
    - Preferences > Tools > Browse: `ws://172.17.0.4:3000`
3. Enable Features:
    - **Attach URLs**: Automatically load and attach a page when pasting a URL into the composer
    - **/browse Command**: Use the `/browse` command in the chat to load a web page
    - **ReAct**: Enable the `loadURL()` function in ReAct for advanced interactions

## Runtime Environment
- [TypeScript](https://www.typescriptlang.org/)

## Screenshots
![](https://big-agi.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fbig-agi-header-front-1.fb06e118.png&w=1080&q=90)

## References
- [Big-AGI](https://big-agi.com/)
- [Big-AGI GitHub](https://github.com/enricoros/big-AGI)
- [Big-AGI Docker](https://big-agi.com/docs/installation)
- [Ollama x big-AGI](https://big-agi.com/docs/config-local-ollama)
- [Browse Functionality in big-AGI](https://big-agi.com/docs/config-feature-browse)