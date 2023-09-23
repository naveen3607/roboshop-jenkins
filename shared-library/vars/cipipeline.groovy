def call() {
    node('workstation') {

        sh "find . | sed -e '1d' | xargs rm -rf"
        sh "env"
        if (env.TAG_NAME ==~ ".*") {
            env.BRANCH_NAME == "refs/tags/${env.TAG_NAME}"
        }
        else {
            env.BRANCH_NAME == "${env.BRANCH_NAME}"
        }
        checkout scmGit(
                branches: [[name: branch_name]],
                userRemoteConfigs: [[url: "https://github.com/naveen3607/${component}"]]
        )

        stage('Compile Code') {
           common.compile()
        }
        stage('Test') {
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