# /bin/bash

mvn clean deploy -Dmaven.test.skip  -DaltDeploymentRepository=self-mvn-repo::default::file:/Users/yihui/GitHub/maven-repository/repository
