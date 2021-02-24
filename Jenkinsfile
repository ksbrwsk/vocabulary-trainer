node {
    stage 'Clone the project'
    git 'https://github.com/ksbrwsk/vocabulary-trainer.git'

    stage("Compilation and Analysis") {
        parallel 'Compilation': {
            sh "./mvnw clean install -DskipTests"
        }
    }
}
