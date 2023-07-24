pipeline  {
    agent any

    tools {
        jdk 'OpenJDK11'
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '5'))
        skipDefaultCheckout()
    }

    stages {
    	stage('Clean Workspace') {
            steps {
                // Cleanup before starting the stage
                cleanWs()
            }
        }
    	stage('Checkout') {
            steps {
                // Checkout the repository
                checkout scm 
            }
        }
        stage('Main branch release') {
            when { 
                branch 'main' 
            }
            steps {
                echo "I am building on ${env.BRANCH_NAME}"
                sh "./gradlew build release -Drelease.dir=$JENKINS_HOME/repo.dim/release/de.jena.upd-model --info --stacktrace -Dmaven.repo.local=${WORKSPACE}/.m2"
            }
        }
        stage('Snapshot branch release') {
            when { 
                branch 'snapshot'
            }
            steps  {
                echo "I am building on ${env.JOB_NAME}"
                sh "./gradlew build release --info --stacktrace -Dmaven.repo.local=${WORKSPACE}/.m2"
                sh "mkdir -p $JENKINS_HOME/repo.dim/snapshot/de.jena.upd-model"
                sh "rm -rf $JENKINS_HOME/repo.dim/snapshot/de.jena.upd-model/*"
                sh "cp -r cnf/release/* $JENKINS_HOME/repo.dim/snapshot/de.jena.upd-model"
            }
        }
        stage('Other branch') {
            when {
            	allOf {
            		not {
	                	branch 'snapshot'
	            	}
            		not {
	                	branch 'main'
	            	}
            	}
            }
            steps  {
                echo "I am building on ${env.JOB_NAME}"
                sh "./gradlew build --info --stacktrace -Dmaven.repo.local=${WORKSPACE}/.m2"
            }
        }
    }

}
