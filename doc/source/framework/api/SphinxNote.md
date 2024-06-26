# Sphinx Note

## Overview
Sphinx is written in [Python](https://docs.python-guide.org/) and supports Python 3.5+.

## Installing Sphinx
### Linux
RHEL, CentOS
```sh
yum install python-sphinx
```

### Installation from PyPI
```sh
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

## Sphinx Comments
1. `pip install sphinx-comments`
2. `conf.py`
	```
	extensions = [
		'sphinx_comments',
	]
	comments_config = {
	   "utterances": {
	      "repo": "github-org/github-repo",
	      "optional": "config",
	   }
	}
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

## Sphinx Book Theme
1. `pip install sphinx-book-theme`
2. `conf.py`
	```
	html_theme = "sphinx_book_theme"
	html_theme_options = {
	    "path_to_docs": "doc/source",
	    "repository_url": "https://github.com/T5750/framework-repositories",
	    "repository_branch": "master",
	    "use_source_button": True,
	    "use_edit_page_button": True,
	    "use_issues_button": True,
	}
	```

## Tips
### Read the Docs
- `requirements.txt`
- Fix `latexmk -r latexmkrc -pdf`: `*.gif`
- [https://readthedocs.org/](https://readthedocs.org/) -> 项目 -> 管理 -> 设置 -> 名称: `frameworks`
- Fix `ImportError: cannot import name 'PackageFinder' from 'pip._internal.index'`:
	* Go to **Versions**
	* Click on the **Edit** button of the version you want to wipe on the right side of the page
	* Go to the bottom of the page and click the **wipe** link, next to the “Save” button
- Fix `Could not import extension sphinx.builders.linkcheck`: use a config file ([https://docs.readthedocs.io/en/stable/config-file/v2.html](https://docs.readthedocs.io/en/stable/config-file/v2.html))

## References
- [Installing Sphinx](http://www.sphinx-doc.org/en/master/usage/installation.html)
- [Sphinx Markdown](http://www.sphinx-doc.org/en/master/usage/markdown.html)
- [Getting Started with Sphinx](https://docs.readthedocs.io/en/stable/intro/getting-started-with-sphinx.html)
- [Latest pip release fails](https://github.com/readthedocs/readthedocs.org/issues/6554)
- [Wiping a Build Environment](https://docs.readthedocs.io/en/stable/guides/wipe-environment.html)
- [TypeError: 'generator' object is not reversible](https://github.com/sphinx-doc/sphinx/issues/9727)
- [Sphinx Comments](https://daobook.github.io/sphinx-comments/)
- [Could not import extension sphinx.builders.linkcheck (exception: urllib3 v2.0 only supports OpenSSL 1.1.1+)](https://github.com/readthedocs/readthedocs.org/issues/10290)
- [Sphinx Book Theme Get started](https://sphinx-book-theme.readthedocs.io/en/stable/tutorials/get-started.html)
- [Sphinx Book Theme Reference of theme options](https://sphinx-book-theme.readthedocs.io/en/stable/reference.html)
- [PyTorch Sphinx Theme](https://github.com/pytorch/pytorch_sphinx_theme)