package com.example

/*
Git Functions
*/

def getCommitHash() {

  sh "git rev-parse --short HEAD > commit-hash.txt"
  readFile('commit-hash.txt').trim()
}

// You do not need this for Multibranch Pipeline Projects
def getCurrentBranch() {

  sh "git rev-parse --abbrev-ref HEAD > current-branch.txt"
  readFile('current-branch.txt').trim()
}

/*
Docker Functions
*/

def dockerTest() {

  sh "docker --version";
}

def dockerBuild(Map args) {

  def path

  if (args.path == null) {
      path = "./"
  } else {
      path = args.path
  }

  sh "docker build -t ${args.imageName}:${args.imageTag} ${path}"
}

def dockerTag(Map args) {

  sh "docker tag ${args.imageName}:${args.imageTag} ${args.targetImageName}:${args.targetImageTag}"
}

def dockerLogin(Map args) {

  sh "docker login -u ${args.username} -p ${args.password}"
}

def dockerPush(Map args) {

  sh "docker push ${args.imageName}:${args.imageTag}"
}

/*
Helm Functions
*/

def helmTest() {

  sh "helm list"
}
