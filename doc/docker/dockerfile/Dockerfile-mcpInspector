FROM node:22-alpine

WORKDIR /app

# Install the required package with a clean npm cache and specific flags
RUN npm i --no-fund --no-audit @modelcontextprotocol/inspector

# Expose the required ports
EXPOSE 6274 9000

CMD ["npx", "@modelcontextprotocol/inspector"]