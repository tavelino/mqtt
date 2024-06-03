# mqtt-producer

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at http://localhost:8080/q/dev/.

## Packaging and running the application

The application can be packaged using:
```shell script
./mvnw package
```
It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:
```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using: 
```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: 
```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/mqtt-producer-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/maven-tooling.

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)



apiVersion: kafka.strimzi.io/v1beta2
kind: Kafka
metadata:
  annotations:
    kubectl.kubernetes.io/last-applied-configuration: >
      {"apiVersion":"kafka.strimzi.io/v1beta2","kind":"Kafka","metadata":{"annotations":{},"labels":{"infra.argoproj.io/instance":"k8shmlbb103-iib-kafka-sgn"},"name":"kafka-sgn","namespace":"iib-kafka-sgn"},"spec":{"clientsCa":{"renewalDays":60,"validityDays":1825},"clusterCa":{"generateCertificateAuthority":false,"generateSecretOwnerReference":false,"renewalDays":60,"validityDays":1825},"cruiseControl":{"logging":{"loggers":{"rootLogger.level":"ERROR"},"type":"inline"},"template":{"pod":{"affinity":{"nodeAffinity":{"requiredDuringSchedulingIgnoredDuringExecution":{"nodeSelectorTerms":[{"matchExpressions":[{"key":"node.k8s.bb/servico","operator":"In","values":["iib-kafka-util-sgn-worker"]}]},{"matchExpressions":[{"key":"release.k8s.bb/servico","operator":"In","values":["sgn"]}]}]}}},"tolerations":[{"effect":"NoSchedule","key":"node.k8s.bb/site","value":"bb1"},{"effect":"NoSchedule","key":"node.k8s.bb/servico","value":"iib-kafka-util-sgn-worker"},{"effect":"NoSchedule","key":"paas.cluster.k8s.bb/iib-kafka","value":"true"},{"effect":"NoSchedule","key":"release.k8s.bb/servico","value":"sgn"}]}}},"entityOperator":{"template":{"pod":{"affinity":{"nodeAffinity":{"requiredDuringSchedulingIgnoredDuringExecution":{"nodeSelectorTerms":[{"matchExpressions":[{"key":"node.k8s.bb/servico","operator":"In","values":["iib-kafka-util-sgn-worker"]}]},{"matchExpressions":[{"key":"release.k8s.bb/servico","operator":"In","values":["sgn"]}]}]}}},"tolerations":[{"effect":"NoSchedule","key":"node.k8s.bb/site","value":"bb1"},{"effect":"NoSchedule","key":"node.k8s.bb/servico","value":"iib-kafka-util-sgn-worker"},{"effect":"NoSchedule","key":"paas.cluster.k8s.bb/iib-kafka","value":"true"},{"effect":"NoSchedule","key":"release.k8s.bb/servico","value":"sgn"}]}},"topicOperator":{"logging":{"loggers":{"rootLogger.level":"ERROR"},"type":"inline"},"resources":{"requests":{"cpu":"100m","memory":"512Mi"}}},"userOperator":{"logging":{"loggers":{"rootLogger.level":"ERROR"},"type":"inline"},"resources":{"requests":{"cpu":"100m","memory":"512Mi"}}}},"kafka":{"authorization":{"type":"simple"},"config":{"auto.create.topics.enable":true,"default.replication.factor":3,"inter.broker.protocol.version":"3.6","log.retention.check.interval.ms":60000,"log.retention.ms":300000,"min.insync.replicas":2,"num.partitions":10,"offsets.topic.replication.factor":3,"transaction.state.log.min.isr":2,"transaction.state.log.replication.factor":3},"jvmOptions":{"-XX":{"InitiatingHeapOccupancyPercent":45,"MaxGCPauseMillis":200,"UseG1GC":true,"UseStringDeduplication":true},"-Xms":"6g","-Xmx":"6g","gcLoggingEnabled":true},"listeners":[{"authentication":{"type":"scram-sha-512"},"name":"plain","port":9092,"tls":false,"type":"internal"},{"name":"tls","port":9093,"tls":true,"type":"internal"},{"authentication":{"type":"scram-sha-512"},"configuration":{"bootstrap":{"host":"seguranca-bootstrap.kafka.iib.hm.bb.com.br","labels":{"ingress":"kafka"}},"brokers":[{"broker":0,"host":"seguranca-0.kafka.iib.hm.bb.com.br","labels":{"ingress":"kafka"}},{"broker":1,"host":"seguranca-1.kafka.iib.hm.bb.com.br","labels":{"ingress":"kafka"}},{"broker":2,"host":"seguranca-2.kafka.iib.hm.bb.com.br","labels":{"ingress":"kafka"}}]},"name":"ocroute","port":9094,"tls":true,"type":"route"}],"metricsConfig":{"type":"jmxPrometheusExporter","valueFrom":{"configMapKeyRef":{"key":"kafka-metrics-config.yml","name":"kafka-metrics"}}},"replicas":3,"storage":{"class":"vsphere-csi-kafka","size":"100Gi","type":"persistent-claim"},"template":{"persistentVolumeClaim":{"metadata":{"labels":{"infra.argoproj.io/instance":"none"}}},"pod":{"affinity":{"nodeAffinity":{"requiredDuringSchedulingIgnoredDuringExecution":{"nodeSelectorTerms":[{"matchExpressions":[{"key":"node.k8s.bb/servico","operator":"In","values":["iib-kafka-broker-sgn-worker"]},{"key":"release.k8s.bb/servico","operator":"In","values":["sgn"]}]}]}},"podAntiAffinity":{"requiredDuringSchedulingIgnoredDuringExecution":[{"labelSelector":{"matchExpressions":[{"key":"strimzi.io/name","operator":"In","values":["kafka-sgn-kafka"]}]},"topologyKey":"kubernetes.io/hostname"}]}},"tolerations":[{"effect":"NoSchedule","key":"node.k8s.bb/site","value":"bb1"},{"effect":"NoSchedule","key":"node.k8s.bb/servico","value":"iib-kafka-broker-sgn-worker"},{"effect":"NoSchedule","key":"paas.cluster.k8s.bb/iib-kafka","value":"true"},{"effect":"NoSchedule","key":"release.k8s.bb/servico","value":"sgn"}]}},"version":"3.6.0"},"kafkaExporter":{"resources":{"limits":{"cpu":"300m","memory":"200Mi"},"requests":{"cpu":"50m","memory":"100Mi"}},"template":{"pod":{"affinity":{"nodeAffinity":{"requiredDuringSchedulingIgnoredDuringExecution":{"nodeSelectorTerms":[{"matchExpressions":[{"key":"node.k8s.bb/servico","operator":"In","values":["iib-kafka-util-sgn-worker"]}]},{"matchExpressions":[{"key":"release.k8s.bb/servico","operator":"In","values":["sgn"]}]}]}}},"tolerations":[{"effect":"NoSchedule","key":"node.k8s.bb/site","value":"bb1"},{"effect":"NoSchedule","key":"node.k8s.bb/servico","value":"iib-kafka-util-sgn-worker"},{"effect":"NoSchedule","key":"paas.cluster.k8s.bb/iib-kafka","value":"true"},{"effect":"NoSchedule","key":"release.k8s.bb/servico","value":"sgn"}]}}},"zookeeper":{"metricsConfig":{"type":"jmxPrometheusExporter","valueFrom":{"configMapKeyRef":{"key":"zookeeper-metrics-config.yml","name":"kafka-metrics"}}},"replicas":3,"storage":{"class":"vsphere-csi-kafka","size":"100Gi","type":"persistent-claim"},"template":{"persistentVolumeClaim":{"metadata":{"labels":{"infra.argoproj.io/instance":"none"}}},"pod":{"affinity":{"nodeAffinity":{"requiredDuringSchedulingIgnoredDuringExecution":{"nodeSelectorTerms":[{"matchExpressions":[{"key":"node.k8s.bb/servico","operator":"In","values":["iib-kafka-zoo-sgn-infra"]},{"key":"release.k8s.bb/servico","operator":"In","values":["sgn"]}]}]}},"podAntiAffinity":{"requiredDuringSchedulingIgnoredDuringExecution":[{"labelSelector":{"matchExpressions":[{"key":"strimzi.io/name","operator":"In","values":["kafka-sgn-zookeeper"]}]},"topologyKey":"kubernetes.io/hostname"}]}},"tolerations":[{"effect":"NoSchedule","key":"node-role.kubernetes.io/infra"},{"effect":"NoSchedule","key":"node.k8s.bb/site","value":"bb1"},{"effect":"NoSchedule","key":"node.k8s.bb/servico","value":"iib-kafka-zoo-sgn-infra"},{"effect":"NoSchedule","key":"paas.cluster.k8s.bb/iib-kafka","value":"true"},{"effect":"NoSchedule","key":"release.k8s.bb/servico","value":"sgn"}]}}}}}
  resourceVersion: '2243352782'
  name: kafka-sgn
  uid: f3982aa3-41ab-45c2-9538-cd7b69c1b958
  creationTimestamp: '2024-05-15T19:29:08Z'
  generation: 1
  managedFields:
    - apiVersion: kafka.strimzi.io/v1beta2
      fieldsType: FieldsV1
      fieldsV1:
        'f:metadata':
          'f:annotations':
            .: {}
            'f:kubectl.kubernetes.io/last-applied-configuration': {}
          'f:labels':
            .: {}
            'f:infra.argoproj.io/instance': {}
        'f:spec':
          .: {}
          'f:clientsCa':
            .: {}
            'f:renewalDays': {}
            'f:validityDays': {}
          'f:clusterCa':
            .: {}
            'f:generateCertificateAuthority': {}
            'f:generateSecretOwnerReference': {}
            'f:renewalDays': {}
            'f:validityDays': {}
          'f:cruiseControl':
            .: {}
            'f:logging':
              .: {}
              'f:loggers':
                .: {}
                'f:rootLogger.level': {}
              'f:type': {}
            'f:template':
              .: {}
              'f:pod':
                .: {}
                'f:affinity':
                  .: {}
                  'f:nodeAffinity':
                    .: {}
                    'f:requiredDuringSchedulingIgnoredDuringExecution':
                      .: {}
                      'f:nodeSelectorTerms': {}
                'f:tolerations': {}
          'f:entityOperator':
            .: {}
            'f:template':
              .: {}
              'f:pod':
                .: {}
                'f:affinity':
                  .: {}
                  'f:nodeAffinity':
                    .: {}
                    'f:requiredDuringSchedulingIgnoredDuringExecution':
                      .: {}
                      'f:nodeSelectorTerms': {}
                'f:tolerations': {}
            'f:topicOperator':
              .: {}
              'f:logging':
                .: {}
                'f:loggers':
                  .: {}
                  'f:rootLogger.level': {}
                'f:type': {}
              'f:resources':
                .: {}
                'f:requests':
                  .: {}
                  'f:cpu': {}
                  'f:memory': {}
            'f:userOperator':
              .: {}
              'f:logging':
                .: {}
                'f:loggers':
                  .: {}
                  'f:rootLogger.level': {}
                'f:type': {}
              'f:resources':
                .: {}
                'f:requests':
                  .: {}
                  'f:cpu': {}
                  'f:memory': {}
          'f:kafka':
            'f:version': {}
            'f:authorization':
              .: {}
              'f:type': {}
            'f:storage':
              .: {}
              'f:class': {}
              'f:size': {}
              'f:type': {}
            'f:listeners': {}
            .: {}
            'f:template':
              .: {}
              'f:persistentVolumeClaim':
                .: {}
                'f:metadata':
                  .: {}
                  'f:labels':
                    .: {}
                    'f:infra.argoproj.io/instance': {}
              'f:pod':
                .: {}
                'f:affinity':
                  .: {}
                  'f:nodeAffinity':
                    .: {}
                    'f:requiredDuringSchedulingIgnoredDuringExecution':
                      .: {}
                      'f:nodeSelectorTerms': {}
                  'f:podAntiAffinity':
                    .: {}
                    'f:requiredDuringSchedulingIgnoredDuringExecution': {}
                'f:tolerations': {}
            'f:replicas': {}
            'f:metricsConfig':
              .: {}
              'f:type': {}
              'f:valueFrom':
                .: {}
                'f:configMapKeyRef':
                  .: {}
                  'f:key': {}
                  'f:name': {}
            'f:jvmOptions':
              .: {}
              'f:-XX':
                .: {}
                'f:InitiatingHeapOccupancyPercent': {}
                'f:MaxGCPauseMillis': {}
                'f:UseG1GC': {}
                'f:UseStringDeduplication': {}
              'f:-Xms': {}
              'f:-Xmx': {}
              'f:gcLoggingEnabled': {}
            'f:config':
              'f:transaction.state.log.min.isr': {}
              'f:num.partitions': {}
              'f:auto.create.topics.enable': {}
              .: {}
              'f:inter.broker.protocol.version': {}
              'f:offsets.topic.replication.factor': {}
              'f:default.replication.factor': {}
              'f:log.retention.ms': {}
              'f:min.insync.replicas': {}
              'f:log.retention.check.interval.ms': {}
              'f:transaction.state.log.replication.factor': {}
          'f:kafkaExporter':
            .: {}
            'f:resources':
              .: {}
              'f:limits':
                .: {}
                'f:cpu': {}
                'f:memory': {}
              'f:requests':
                .: {}
                'f:cpu': {}
                'f:memory': {}
            'f:template':
              .: {}
              'f:pod':
                .: {}
                'f:affinity':
                  .: {}
                  'f:nodeAffinity':
                    .: {}
                    'f:requiredDuringSchedulingIgnoredDuringExecution':
                      .: {}
                      'f:nodeSelectorTerms': {}
                'f:tolerations': {}
          'f:zookeeper':
            .: {}
            'f:metricsConfig':
              .: {}
              'f:type': {}
              'f:valueFrom':
                .: {}
                'f:configMapKeyRef':
                  .: {}
                  'f:key': {}
                  'f:name': {}
            'f:replicas': {}
            'f:storage':
              .: {}
              'f:class': {}
              'f:size': {}
              'f:type': {}
            'f:template':
              .: {}
              'f:persistentVolumeClaim':
                .: {}
                'f:metadata':
                  .: {}
                  'f:labels':
                    .: {}
                    'f:infra.argoproj.io/instance': {}
              'f:pod':
                .: {}
                'f:affinity':
                  .: {}
                  'f:nodeAffinity':
                    .: {}
                    'f:requiredDuringSchedulingIgnoredDuringExecution':
                      .: {}
                      'f:nodeSelectorTerms': {}
                  'f:podAntiAffinity':
                    .: {}
                    'f:requiredDuringSchedulingIgnoredDuringExecution': {}
                'f:tolerations': {}
      manager: argocd-controller
      operation: Update
      time: '2024-05-15T19:29:08Z'
    - apiVersion: kafka.strimzi.io/v1beta2
      fieldsType: FieldsV1
      fieldsV1:
        'f:status':
          .: {}
          'f:clusterId': {}
          'f:conditions': {}
          'f:kafkaVersion': {}
          'f:listeners': {}
          'f:observedGeneration': {}
          'f:operatorLastSuccessfulVersion': {}
      manager: strimzi-cluster-operator
      operation: Update
      subresource: status
      time: '2024-05-15T19:37:43Z'
  namespace: iib-kafka-sgn
  labels:
    infra.argoproj.io/instance: k8shmlbb103-iib-kafka-sgn
spec:
  clientsCa:
    renewalDays: 60
    validityDays: 1825
  clusterCa:
    generateCertificateAuthority: false
    generateSecretOwnerReference: false
    renewalDays: 60
    validityDays: 1825
  cruiseControl:
    logging:
      loggers:
        rootLogger.level: ERROR
      type: inline
    template:
      pod:
        affinity:
          nodeAffinity:
            requiredDuringSchedulingIgnoredDuringExecution:
              nodeSelectorTerms:
                - matchExpressions:
                    - key: node.k8s.bb/servico
                      operator: In
                      values:
                        - iib-kafka-util-sgn-worker
                - matchExpressions:
                    - key: release.k8s.bb/servico
                      operator: In
                      values:
                        - sgn
        tolerations:
          - effect: NoSchedule
            key: node.k8s.bb/site
            value: bb1
          - effect: NoSchedule
            key: node.k8s.bb/servico
            value: iib-kafka-util-sgn-worker
          - effect: NoSchedule
            key: paas.cluster.k8s.bb/iib-kafka
            value: 'true'
          - effect: NoSchedule
            key: release.k8s.bb/servico
            value: sgn
  entityOperator:
    template:
      pod:
        affinity:
          nodeAffinity:
            requiredDuringSchedulingIgnoredDuringExecution:
              nodeSelectorTerms:
                - matchExpressions:
                    - key: node.k8s.bb/servico
                      operator: In
                      values:
                        - iib-kafka-util-sgn-worker
                - matchExpressions:
                    - key: release.k8s.bb/servico
                      operator: In
                      values:
                        - sgn
        tolerations:
          - effect: NoSchedule
            key: node.k8s.bb/site
            value: bb1
          - effect: NoSchedule
            key: node.k8s.bb/servico
            value: iib-kafka-util-sgn-worker
          - effect: NoSchedule
            key: paas.cluster.k8s.bb/iib-kafka
            value: 'true'
          - effect: NoSchedule
            key: release.k8s.bb/servico
            value: sgn
    topicOperator:
      logging:
        loggers:
          rootLogger.level: ERROR
        type: inline
      resources:
        requests:
          cpu: 100m
          memory: 512Mi
    userOperator:
      logging:
        loggers:
          rootLogger.level: ERROR
        type: inline
      resources:
        requests:
          cpu: 100m
          memory: 512Mi
  kafka:
    config:
      log.retention.ms: 300000
      inter.broker.protocol.version: '3.6'
      transaction.state.log.replication.factor: 3
      min.insync.replicas: 2
      log.retention.check.interval.ms: 60000
      num.partitions: 10
      transaction.state.log.min.isr: 2
      auto.create.topics.enable: true
      offsets.topic.replication.factor: 3
      default.replication.factor: 3
    metricsConfig:
      type: jmxPrometheusExporter
      valueFrom:
        configMapKeyRef:
          key: kafka-metrics-config.yml
          name: kafka-metrics
    version: 3.6.0
    authorization:
      type: simple
    template:
      persistentVolumeClaim:
        metadata:
          labels:
            infra.argoproj.io/instance: none
      pod:
        affinity:
          nodeAffinity:
            requiredDuringSchedulingIgnoredDuringExecution:
              nodeSelectorTerms:
                - matchExpressions:
                    - key: node.k8s.bb/servico
                      operator: In
                      values:
                        - iib-kafka-broker-sgn-worker
                    - key: release.k8s.bb/servico
                      operator: In
                      values:
                        - sgn
          podAntiAffinity:
            requiredDuringSchedulingIgnoredDuringExecution:
              - labelSelector:
                  matchExpressions:
                    - key: strimzi.io/name
                      operator: In
                      values:
                        - kafka-sgn-kafka
                topologyKey: kubernetes.io/hostname
        tolerations:
          - effect: NoSchedule
            key: node.k8s.bb/site
            value: bb1
          - effect: NoSchedule
            key: node.k8s.bb/servico
            value: iib-kafka-broker-sgn-worker
          - effect: NoSchedule
            key: paas.cluster.k8s.bb/iib-kafka
            value: 'true'
          - effect: NoSchedule
            key: release.k8s.bb/servico
            value: sgn
    storage:
      class: vsphere-csi-kafka
      size: 100Gi
      type: persistent-claim
    replicas: 3
    jvmOptions:
      '-XX':
        InitiatingHeapOccupancyPercent: 45
        MaxGCPauseMillis: 200
        UseG1GC: true
        UseStringDeduplication: true
      '-Xms': 6g
      '-Xmx': 6g
      gcLoggingEnabled: true
    listeners:
      - authentication:
          type: scram-sha-512
        name: plain
        port: 9092
        tls: false
        type: internal
      - name: tls
        port: 9093
        tls: true
        type: internal
      - authentication:
          type: scram-sha-512
        configuration:
          bootstrap:
            host: seguranca-bootstrap.kafka.iib.hm.bb.com.br
            labels:
              ingress: kafka
          brokers:
            - broker: 0
              host: seguranca-0.kafka.iib.hm.bb.com.br
              labels:
                ingress: kafka
            - broker: 1
              host: seguranca-1.kafka.iib.hm.bb.com.br
              labels:
                ingress: kafka
            - broker: 2
              host: seguranca-2.kafka.iib.hm.bb.com.br
              labels:
                ingress: kafka
        name: ocroute
        port: 9094
        tls: true
        type: route
  kafkaExporter:
    resources:
      limits:
        cpu: 300m
        memory: 200Mi
      requests:
        cpu: 50m
        memory: 100Mi
    template:
      pod:
        affinity:
          nodeAffinity:
            requiredDuringSchedulingIgnoredDuringExecution:
              nodeSelectorTerms:
                - matchExpressions:
                    - key: node.k8s.bb/servico
                      operator: In
                      values:
                        - iib-kafka-util-sgn-worker
                - matchExpressions:
                    - key: release.k8s.bb/servico
                      operator: In
                      values:
                        - sgn
        tolerations:
          - effect: NoSchedule
            key: node.k8s.bb/site
            value: bb1
          - effect: NoSchedule
            key: node.k8s.bb/servico
            value: iib-kafka-util-sgn-worker
          - effect: NoSchedule
            key: paas.cluster.k8s.bb/iib-kafka
            value: 'true'
          - effect: NoSchedule
            key: release.k8s.bb/servico
            value: sgn
  zookeeper:
    metricsConfig:
      type: jmxPrometheusExporter
      valueFrom:
        configMapKeyRef:
          key: zookeeper-metrics-config.yml
          name: kafka-metrics
    replicas: 3
    storage:
      class: vsphere-csi-kafka
      size: 100Gi
      type: persistent-claim
    template:
      persistentVolumeClaim:
        metadata:
          labels:
            infra.argoproj.io/instance: none
      pod:
        affinity:
          nodeAffinity:
            requiredDuringSchedulingIgnoredDuringExecution:
              nodeSelectorTerms:
                - matchExpressions:
                    - key: node.k8s.bb/servico
                      operator: In
                      values:
                        - iib-kafka-zoo-sgn-infra
                    - key: release.k8s.bb/servico
                      operator: In
                      values:
                        - sgn
          podAntiAffinity:
            requiredDuringSchedulingIgnoredDuringExecution:
              - labelSelector:
                  matchExpressions:
                    - key: strimzi.io/name
                      operator: In
                      values:
                        - kafka-sgn-zookeeper
                topologyKey: kubernetes.io/hostname
        tolerations:
          - effect: NoSchedule
            key: node-role.kubernetes.io/infra
          - effect: NoSchedule
            key: node.k8s.bb/site
            value: bb1
          - effect: NoSchedule
            key: node.k8s.bb/servico
            value: iib-kafka-zoo-sgn-infra
          - effect: NoSchedule
            key: paas.cluster.k8s.bb/iib-kafka
            value: 'true'
          - effect: NoSchedule
            key: release.k8s.bb/servico
            value: sgn
status:
  clusterId: FeC105sVQxqe5Waa19z3kw
  conditions:
    - lastTransitionTime: '2024-05-15T19:37:43.806291996Z'
      status: 'True'
      type: Ready
  kafkaVersion: 3.6.0
  listeners:
    - addresses:
        - host: kafka-sgn-kafka-bootstrap.iib-kafka-sgn.svc
          port: 9092
      bootstrapServers: 'kafka-sgn-kafka-bootstrap.iib-kafka-sgn.svc:9092'
      name: plain
    - addresses:
        - host: kafka-sgn-kafka-bootstrap.iib-kafka-sgn.svc
          port: 9093
      bootstrapServers: 'kafka-sgn-kafka-bootstrap.iib-kafka-sgn.svc:9093'
      certificates:
        - |
          -----BEGIN CERTIFICATE-----
          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
          -----END CERTIFICATE-----
      name: tls
    - addresses:
        - host: seguranca-bootstrap.kafka.iib.hm.bb.com.br
          port: 443
      bootstrapServers: 'seguranca-bootstrap.kafka.iib.hm.bb.com.br:443'
      certificates:
        - |
          -----BEGIN CERTIFICATE-----
          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
          -----END CERTIFICATE-----
      name: ocroute
  observedGeneration: 1
  operatorLastSuccessfulVersion: 0.38.0.redhat-00005