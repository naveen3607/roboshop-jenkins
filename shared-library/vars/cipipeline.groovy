def call() {
    node('workstation') {
            stage('Compile Code') {
                print "Hello"
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