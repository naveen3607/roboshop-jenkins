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
        sh '/home/centos/maven/bin/mvn test'
    }
    if (env.codeType == "nodejs") {
        sh 'npm test'
    }
    if (env.codeType == "python") {
        sh 'python -m unittest'
    }
    if (env.codeType == "goLang") {
        sh 'go test'
    }
}

def codequality() {
    stage('Code Quality') {
        sonarqube.username = sh (script: 'aws ssm get-parameter --name "sonarqube.username" --query="Parameter.Value"', returnStatus: true)
        sonarqube.password = sh (script: 'aws ssm get-parameter --name "sonarqube.password" --with-decryption --query="Parameter.Value"', returnStatus: true)
        sh "sonar-scanner -X -D sonar.host.url=http://172.31.86.97:9000 -D sonar.login=${sonarqube.username} -D sonar.password=${sonarqube.password} -D sonar.projectKey=${component} -D sonar.qualitygate.wait=true"
    }
}

def codesecurity() {
    stage('Code Security') {
        print 'Code Security'
    }
}

def release() {
    stage('Release') {
        print 'Release'
    }
}