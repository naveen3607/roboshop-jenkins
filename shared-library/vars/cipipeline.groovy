def call() {
    node('workstation') {

        sh "find . | sed -e '1d' | xargs rm -rf"
        sh "env"
        git branch: "${BRANCH_NAME}", url: "https://github.com/naveen3607/${component}"

        stage('Compile Code') {
           common.compile()
        }
        stage('Test') {
           when {
               anyOf {
                   expression { env.BRANCH_NAME == ".*" }
                   expression { env.TAG_NAME == ".*" }
               }
           }
           print "Hello"
        }
        stage('Code Quality') {
           print "Hello"
        }
        stage('Code Security') {
           print "Hello"
        }
        stage('Release') {
           print "Hello"
        }
    }
}