node('master') {

    checkout scm

    def javaHome = tool('jdk8')
    def mavenHome = tool('maven')

    withEnv('JAVA_HOME': "${javaHome}", 'MAVEN_HOME': "${mavenHome}", 'PATH+MAVEN': "${mavenHome}/bin") {

        stage('Packaging') {
            sh 'mvn clean install -DskipTests'
        }

        stage('Testing') {
            parallel(

                    enforcingCodeStandards: stage('Enforcing Code Standards') {

                        def checkstylePlugin = 'org.apache.maven.plugins:maven-checkstyle-plugin'
                        sh "mvn ${checkstylePlugin}:check"
                    },

                    runningUnitTests: stage('Running Unit Tests') {

                        def jacocoPlugin = 'org.jacoco:jacoco-maven-plugin'
                        sh "mvn ${jacocoPlugin}:prepare-agent surefire:test ${jacocoPlugin}:report"
                    },

                    runningMutationTests: stage('Running Mutation Tests') {

                        def pitestPlugin = 'org.pitest:pitest-maven'
                        sh "mvn ${pitestPlugin}:mutationCoverage"
                    },

                    failFast: true
            )

            junit('target/**/TEST*.xml')
        }

        stage('Analyzing') {

            parallel(
                    runningSonarScan: stage('Running Sonar Scan') {
                        withSonarQubeEnv('sonar-server') {
                            sh 'mvn sonar:sonar'
                        }

                        timeout(time: 5, unit: 'MINUTES') {
                            waitForQualityGate(true)
                        }
                    },

                    runningCheckmarxScan: stage('Running Checkmarx Scan') {
                        // step - checkmarx
                        echo('Checkmarxing and Checkengelsing')
                    },

                    failFast: true
            )
        }

        stage('Publishing') {

            def deployPlugin = 'org.apache.maven.plugins:maven-deploy-plugin'
            sh "mvn ${deployPlugin}:deploy dockerfile:build"

            def pom = readMavenPom
            def serviceImage = docker.image("${pom.getGroupId()}/${pom.getArtifactId()}:${pom.getVersion()}")

            echo(serviceImage.id)
//        serviceImage.push()
//        serviceImage.push("${env.BUILD_TAG}")
//        serviceImage.push('latest')

            image
        }

        stage('Integrating') {
            // KUBERNETES...
            echo('Kubernetes!!!')
        }
    }
}