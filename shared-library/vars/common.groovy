def compile() {
    if (env.codeType == "python" || env.codeType == "static") {
        return "Return, Do not need any compilations"
    }
    stage('Compile Code') {
        if (env.codeType == "maven") {
            sh '/home/centos/maven/bin/mvn package'
        }
        if (env.codeType == "nodejs") {
            sh 'npm install'
        }
        if (env.codeType == "goLang") {
            sh 'go mod init dispatch ; go get ; go build'
        }
    }
}

def test() {
    if (env.codeType == "maven") {
//        sh '/home/centos/maven/bin/mvn test'
    }
    if (env.codeType == "nodejs") {
//        sh 'npm test'
    }
    if (env.codeType == "python") {
//        sh 'python -m unittest'
    }
    if (env.codeType == "goLang") {
//        sh 'go test'
    }
}

def codequality() {
    stage('Code Quality') {
        env.sonarqubeuser = sh (script: 'aws ssm get-parameter --name "sonarqube.username" --query="Parameter.Value" |xargs', returnStdout: true).trim()
        env.sonarqubepass = sh (script: 'aws ssm get-parameter --name "sonarqube.password" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
        wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: sonarqubepass]]]) {
            if(env.codeType == "maven") {
                print "OK"
                //sh 'sonar-scanner -X -D sonar.host.url=http://172.31.86.97:9000 -D sonar.login=${sonarqubeuser} -D sonar.password=${sonarqubepass} -D sonar.projectKey=${component} -D sonar.qualitygate.wait=true -D sonar.java.binaries=./target'
            } else {
                print "OK"
                //sh 'sonar-scanner -X -D sonar.host.url=http://172.31.86.97:9000 -D sonar.login=${sonarqubeuser} -D sonar.password=${sonarqubepass} -D sonar.projectKey=${component} -D sonar.qualitygate.wait=true'
            }
        }
    }
}

def codesecurity() {
    stage('Code Security') {
        print 'Code Security'

        // IN code security we will generally used SAST (Static Application Security Testing) & SCA (Software Composition Analysis) checks
        // You can say that your company is using checkmarx sast and checkmarx sca for this
    }
}

def release() {
    stage('Release') {
        env.nexususer = sh (script: 'aws ssm get-parameter --name "nexus.username" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
        env.nexuspass = sh (script: 'aws ssm get-parameter --name "nexus.password" --with-decryption --query="Parameter.Value" |xargs', returnStdout: true).trim()
        wrap([$class: "MaskPasswordsBuildWrapper", varPasswordPairs: [[password: nexuspass]]]) {
            sh 'echo ${TAG_NAME} >VERSION'
            if(env.codeType == "nodejs") {
                sh 'zip -r ${component}-${TAG_NAME}.zip server.js node_modules VERSION ${schemadir}'
            } else if(env.codeType == "maven") {
                sh 'cp target/${component}-1.0.jar ${component}.jar; zip -r ${component}-${TAG_NAME}.zip ${component}.jar VERSION ${schemadir}'
            } else {
                sh 'zip -r ${component}-${TAG_NAME}.zip *'
            }

            sh 'curl -v -u ${nexususer}:${nexuspass} --upload-file ${component}-${TAG_NAME}.zip http://172.31.24.24:8081/repository/${component}/${component}-${TAG_NAME}.zip'
        }
    }
}