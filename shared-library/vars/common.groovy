def compile() {
    if (env.codeType == "maven") {
        sh '/root/maven/bin/mvn package'
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