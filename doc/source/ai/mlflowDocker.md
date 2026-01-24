# MLflow Docker

一个管理机器学习生命周期的工具

## Docker
```sh
docker run -d --name mlflow -p 5000:5000 -v $(pwd)/mlruns:/mlflow/mlruns ghcr.io/mlflow/mlflow mlflow server --host 0.0.0.0 --backend-store-uri sqlite:////mlflow/mlruns/mlflow.db --default-artifact-root /mlflow/mlruns --serve-artifacts
```
[http://localhost:5000](http://localhost:5000)

## pip
```sh
pip install mlflow
mlflow server --port 5000
```

## Quickstart
```
import mlflow
# Connect to remote MLflow server
mlflow.set_tracking_uri("http://localhost:5000/")
mlflow.set_experiment("MLflow Quickstart")
# Enable autologging for scikit-learn
mlflow.sklearn.autolog()
```

## Runtime Environment
- [Python 3.10.x](https://www.python.org/downloads/)

## Architecture
![](https://mlflow.org.cn/docs/latest/assets/images/tracking-setup-overview-3d8cfd511355d9379328d69573763331.png)

## Screenshots
![](https://mlflow.org.cn/docs/latest/assets/images/tracking-metrics-ui-temp-ffc0da57b388076730e20207dbd7f9c4.png)

## References
- [MLflow](https://mlflow.org/)
- [MLflow 中文](https://mlflow.org.cn/docs/latest/ml/)
- [MLflow GitHub](https://github.com/mlflow/mlflow)
- [MLflow Docker](https://mlflow.org.cn/latest/ml/docker/)
- [MLflow Tracking 快速入门](https://mlflow.org.cn/docs/latest/ml/getting-started/quickstart/)
- [MLflow 命令行接口](https://mlflow.org.cn/docs/latest/api_reference/cli.html)
- [MLflow 架构概览](https://mlflow.org.cn/docs/latest/self-hosting/architecture/overview/)
- [MLflow Scikit-learn 集成](https://mlflow.org.cn/docs/latest/ml/traditional-ml/sklearn/)
- [MLflow PyTorch 集成](https://mlflow.org.cn/docs/latest/ml/deep-learning/pytorch/)