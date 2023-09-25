def compile() {
    stage('Compile Code') {
        if (env.codeType == "maven") {
            sh '/home/centos/maven/bin/mvn package'
        }
        if (env.codeType == "nodejs") {
            print "nodejs"
        }
        if (env.codeType == "python") {
            print "python"
        }
        if (env.codeType == "goLang") {
            print "goLang"
        }
        if (env.codeType == "static") {
            print "static"
        }
    }
}

def test() {
    stage('Test Case') {
        print 'Test'
    }
}

def codequality() {
    stage('Code Quality') {
        print 'Code Quality'
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