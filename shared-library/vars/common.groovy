def compile() {
    if (env.codeType == "maven") {
        print "maven"
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