# Troubleshooting

* Error: Helm returns connection refused error. Helm is falling back to localhost or 127.0.0.1 port 8080.
   ```
   $ helm ls
   Error: Get http://localhost:8080/api/v1/namespaces/kube-system/configmaps?labelSelector=OWNER%!D(MISSING)TILLER: dial tcp 127.0.0.1:8080: connect: connection refused
   ```

  > Fix by setting `automountServiceAccountToken` to true:
  ```
  kubectl -n kube-system patch deployment tiller-deploy -p '{"spec": {"template": {"spec": {"automountServiceAccountToken": true}}}}'
  ```

