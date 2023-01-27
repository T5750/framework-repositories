# GitLab Pages Gitbook

## GitLab CI
This project's static Pages are built by [GitLab CI](https://about.gitlab.com/gitlab-ci/), following the steps
defined in [.gitlab-ci.yml](https://gitlab.com/pages/gitbook/-/blob/master/.gitlab-ci.yml)

## Building locally
To work locally with this project, you'll have to follow the steps below:
1. Fork, clone or download this project
1. [Install](http://toolchain.gitbook.com/setup.html) GitBook `npm install gitbook-cli -g`
1. Fetch GitBook's latest stable version `gitbook fetch latest`
1. Preview your project: `gitbook serve`
1. Add content
1. Generate the website: `gitbook build` (optional)
1. Push your changes to the master branch: `git push`

## CLI
`gitbook --help`
- `gitbook ls` 列出本地安装版本
- >语法格式: `gitbook ls`
- `gitbook current` 列出当前使用版本
- >语法格式: `gitbook current`
- `gitbook ls-remote` 列出远程可用版本
- >语法格式: `gitbook ls-remote`
- `gitbook fetch` 安装指定版本
- >语法格式: `gitbook fetch [version]`
- `gitbook alias` 指定文件夹别名
- >语法格式: `gitbook alias [folder] [version]`
- `gitbook uninstall` 卸载指定版本
- >语法格式: `gitbook uninstall [version]`
- `gitbook update` 更新指定版本
- >语法格式: `gitbook update [tag]`

`gitbook help`
- `gitbook build` 构建电子书
- >语法格式: `gitbook build [book] [output]`
- `gitbook serve` 启动本地服务器
- >语法格式: `gitbook serve [book] [output]`
- `gitbook install` 安装插件
- >语法格式: `gitbook install [book]`
- `gitbook parse` 解析电子书
- >语法格式: `gitbook parse [book]`
- `gitbook pdf` 输出 PDF 电子书
- >语法格式: `gitbook pdf [book] [output]`
- `gitbook epub` 输出 epub 电子书
- >语法格式: `gitbook epub [book] [output]`
- `gitbook mobi` 输出 mobi 电子书
- >语法格式: `gitbook mobi [book] [output]`

## Tips
### TypeError: cb.apply is not a function
`polyfills.js`
```
//fs.stat = statFix(fs.stat)
//fs.fstat = statFix(fs.fstat)
//fs.lstat = statFix(fs.lstat)
```

## References
- [Introduction · GitBook](https://pages.gitlab.io/gitbook/)
- [GitBook GitLab](https://gitlab.com/pages/gitbook)
- [GitbookIO/gitbook GitHub](https://github.com/GitbookIO/gitbook)
- [gitbook 入门教程之常用命令详解](https://www.cnblogs.com/snowdreams1006/p/10658492.html)
- [gitbook出现TypeError: cb.apply is not a function解决办法](https://cloud.tencent.com/developer/article/2047604)