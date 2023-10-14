# Cherry Markdown

Cherry Markdown Editor 是一款 Javascript Markdown 编辑器，具有开箱即用、轻量简洁、易于扩展等特点. 它可以运行在浏览器或服务端(NodeJs).

## Demo
- [全功能](https://tencent.github.io/cherry-markdown/examples/index.html)
- [基础版](https://tencent.github.io/cherry-markdown/examples/basic.html)
- [H5](https://tencent.github.io/cherry-markdown/examples/h5.html)
- [多实例](https://tencent.github.io/cherry-markdown/examples/multiple.html)
- [无 toolbar](https://tencent.github.io/cherry-markdown/examples/notoolbar.html)
- [纯预览模式](https://tencent.github.io/cherry-markdown/examples/preview_only.html)
- [注入](https://tencent.github.io/cherry-markdown/examples/xss.html)（默认防注入，需要配置才允许注入）
- [API](https://tencent.github.io/cherry-markdown/examples/api.html)
- [图片所见即所得编辑尺寸](https://tencent.github.io/cherry-markdown/examples/img.html)
- [表格编辑](https://tencent.github.io/cherry-markdown/examples/table.html)
- [标题自动序号](https://tencent.github.io/cherry-markdown/examples/head_num.html)

## Install
Via yarn
```bash
yarn add cherry-markdown
```

Via npm
```bash
npm install cherry-markdown --save
```

If you need to enable the functions of `mermaid` drawing and table-to-chart, you need to add `mermaid` and `echarts` packages at the same time.

Currently, the plug-in version **Cherry** recommend is `echarts@4.6.0` `mermaid@9.4.3`.

```bash
# Install mermaid, enable mermaid and drawing function
yarn add mermaid@9.4.3
# Install echarts, turn on the table-to-chart function
yarn add echarts@4.6.0
```

## Example
[examples](https://github.com/Tencent/cherry-markdown/tree/main/examples)

## References
- [Cherry Markdown GitHub](https://github.com/Tencent/cherry-markdown)