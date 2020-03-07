# GitHub Security

GitHub 宣布增加一些新的特性，旨在帮助开发者保护其代码，包括能够针对需要为包含安全补丁而更新的依赖创建 PR，支持与 [WhiteSource](https://www.whitesourcesoftware.com/GitHubSecurityAlerts) 数据集成，以增强漏洞评估和增进对依赖关系的理解。
- [Security vulnerability alerts now with WhiteSource data](https://help.github.com/en/articles/about-security-alerts-for-vulnerable-dependencies)：目的在于为开发者提供更多有关已发现漏洞的数据
- [Dependency insights](https://help.github.com/articles/viewing-insights-for-your-organization)：采用 GitHub 依赖关系图表，向开发者展示项目依赖关系状态的概况，包括公开安全公告、列出并检查项目的依赖关系等
- [Token scanning](https://help.github.com/en/articles/about-token-scanning)

## Automated security fixes with Dependabot
>“虽然安全漏洞报警给用户提供了信息来保护其项目，行业数据显示，超过 70% 的漏洞在被发现 30 天后仍然没有打补丁，甚至有些 1 年后都还没打补丁。”

[Dependabot](https://dependabot.com/blog/hello-github/) 能够扫描项目的依赖关系，发现其中的所有漏洞，并且自动为每一个漏洞创建 PR

## Open source security
- [Maintainer security advisories (beta)](https://help.github.com/articles/about-maintainer-security-advisories)：为了改善项目维护人员交换信息和讨论任何已发行的漏洞时的沟通
- [Security policy](https://help.github.com/articles/adding-a-security-policy-to-your-repository)：支持显性设置与某个项目相关的安全策略

## References
- [GitHub 增加 Dependabot：新增自动化安全 PR 等一些安全特性](https://www.infoq.cn/article/B9CcEt12PAFMGEwl8_lc)
- [Introducing new ways to keep your code secure](https://github.blog/2019-05-23-introducing-new-ways-to-keep-your-code-secure/)