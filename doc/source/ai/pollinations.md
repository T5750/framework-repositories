# Pollinations

Free Open-Source Image and Text Generation

## 🚀 Getting Started
### Image Generation
1. Visit [https://pollinations.ai](https://pollinations.ai)
2. Type your description in the text box
3. Click "Generate" and watch the magic happen!

### Text Generation
1. Visit [https://text.pollinations.ai](https://text.pollinations.ai)
2. Start chatting with the AI

### Audio Generation
1. Use the `openai-audio` model with our API ([explore voices at OpenAI.fm](https://www.openai.fm/))
2. Generate speech from text or transcribe audio to text

### MCP Server for AI Assistants
```bash
# Run with npx (no installation required)
npx @pollinations/model-context-protocol
```

## 📚 API
### Generate Image API
- Text-To-Image (GET): `https://image.pollinations.ai/prompt/{prompt}`
- List Available Image Models: [https://image.pollinations.ai/models](https://image.pollinations.ai/models)

### Generate Text API
- Text-To-Text (GET): `https://text.pollinations.ai/{prompt}`
- Text & Multimodal (OpenAI Compatible POST): `https://text.pollinations.ai/openai`
- List Available Text Models: [https://text.pollinations.ai/models](https://text.pollinations.ai/models)

### Generate Audio API
- Text-to-Speech (GET): `https://text.pollinations.ai/{prompt}?model=openai-audio&voice={voice}`
- Text-to-Speech (POST - OpenAI Compatible): `https://text.pollinations.ai/openai`

### MCP Server for AI Assistants
- Server Name: `pollinations-multimodal-api`
- [MCP Server Documentation](https://github.com/pollinations/pollinations/blob/master/model-context-protocol/README.md)

### Real-time Feeds API
- Image Feed: `https://image.pollinations.ai/feed`
- Text Feed: `https://text.pollinations.ai/feed`

## 🤝 References
- [Pollinations](https://pollinations.ai/)
- [Pollinations GitHub](https://github.com/pollinations/pollinations)
- [Pollinations API Documentation](https://github.com/pollinations/pollinations/blob/master/APIDOCS.md)