def call() {
    node('workstation') {

        sh "find . | sed -e '1d' | xargs rm -rf"
        sh "env"
        if (env.TAG_NAME ==~ ".*") {
            env.BRANCH_NAME == "refs/tags/${env.TAG_NAME}"
        } else {
            env.BRANCH_NAME == "${env.BRANCH_NAME}"
        }
        checkout scmGit(
                branches: [[name: branch_name]],
                userRemoteConfigs: [[url: "https://github.com/naveen3607/${component}"]]
        )
        if (env.TAG_NAME ==~ ".*") {
            common.compile()
            common.release()
        } else {
            if (env.BRANCH_NAME == "main") {
                common.compile()
                common.test()
                common.codequality()
                common.codesecurity()
            } else {
                common.compile()
                common.test()
                common.codequality()
            }
        }
    }
}