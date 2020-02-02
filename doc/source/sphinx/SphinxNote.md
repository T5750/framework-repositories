# Sphinx Note

## Overview
Sphinx is written in [Python](https://docs.python-guide.org/) and supports Python 3.5+.

## Installing Sphinx
### Linux
RHEL, CentOS
```
yum install python-sphinx
```

### Installation from PyPI
```
pip install -U sphinx
```

### Windows
1. [Download Python 3.7.x](https://www.python.org/downloads/)
2. `pip install -U sphinx`

## Markdown
### Configuration
1. `pip install --upgrade recommonmark`
2. `extensions = ['recommonmark']`
3. `source_suffix`
	```
	source_suffix = {
		'.rst': 'restructuredtext',
		'.txt': 'markdown',
		'.md': 'markdown',
	}
	```

### sphinx-markdown-tables
1. `pip install sphinx-markdown-tables`
2. `conf.py`
	```
	extensions = [
		'sphinx_markdown_tables',
	]
	```

## Read the Docs
1. `pip install sphinx_rtd_theme`
2. `sphinx-quickstart`
    - Separate source and build directories (y/n) [n]: `y`
    - Project language [en]: `zh_CN`
3. `make html`

### Windows
```
make.bat html
```

### Build
1. [https://readthedocs.org/](https://readthedocs.org/)
2. Sign in with GitHub
3. Import a Project

## Tips
### Read the Docs
- `requirements.txt`
- Fix `latexmk -r latexmkrc -pdf`: `*.gif`
- [https://readthedocs.org/](https://readthedocs.org/) -> 项目 -> 管理 -> 设置 -> 名称: `frameworks`
- Fix `ImportError: cannot import name 'PackageFinder' from 'pip._internal.index'`:
	* Go to **Versions**
	* Click on the **Edit** button of the version you want to wipe on the right side of the page
	* Go to the bottom of the page and click the **wipe** link, next to the “Save” button

## References
- [Installing Sphinx](http://www.sphinx-doc.org/en/master/usage/installation.html)
- [Sphinx Markdown](http://www.sphinx-doc.org/en/master/usage/markdown.html)
- [Getting Started with Sphinx](https://docs.readthedocs.io/en/stable/intro/getting-started-with-sphinx.html)
- [Latest pip release fails](https://github.com/readthedocs/readthedocs.org/issues/6554)
- [Wiping a Build Environment](https://docs.readthedocs.io/en/stable/guides/wipe-environment.html)