# LangChain

LangChain is a framework for developing applications powered by large language models (LLMs).

## Architecture
![](https://python.langchain.com/svg/langchain_stack_112024.svg)

- `langchain-core`: 基础抽象和LangChain表达式 (LCEL)
- Integration packages (e.g. `langchain-openai`, `langchain-anthropic`, etc.): Important integrations have been split into lightweight packages that are co-maintained by the LangChain team and the integration developers.
- `langchain`: 组成应用程序认知架构的链、代理和检索策略
- `langchain-community`: 第三方集成
- [LangGraph](https://langchain-ai.github.io/langgraph): 通过将步骤建模为图中的边和节点，构建强大且有状态的多参与者应用程序
- [LangServe](https://github.com/langchain-ai/langserve): 将LangChain链部署为REST API
- [LangSmith](https://docs.smith.langchain.com/): 一个开发者平台，让您调试、测试、评估和监控LLM应用程序

## OllamaLLM
### Installation
```sh
pip install -U langchain-ollama
```

### Usage
```sh
from langchain_core.prompts import ChatPromptTemplate
from langchain_ollama.llms import OllamaLLM
template = """Question: {question}
Answer: Let's think step by step."""
prompt = ChatPromptTemplate.from_template(template)
model = OllamaLLM(model="llama3.1")
chain = prompt | model
chain.invoke({"question": "What is LangChain?"})
```

## References
- [LangChain](https://python.langchain.com/)
- [LangChain GitHub](https://github.com/langchain-ai/langchain)
- [LangChain中文网](https://www.langchain.com.cn/)
- [LangChain OllamaLLM](https://python.langchain.com/docs/integrations/llms/ollama/)